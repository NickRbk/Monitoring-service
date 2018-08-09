package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.repository.TweetRepository;
import com.petproject.monitoring.exception.InvalidParameterException;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.sort.SortConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TweetService implements ITweetService, SortConstants {

    private Twitter twitter;
    private IEntityAdapterService entityAdapterService;
    private TweetRepository tweetRepository;

    @Override
    public Page<Tweet> getTweets(List<TargetUser> targetUsers, int page, int size) {
        List<Tweet> tweets = new ArrayList<>();
        targetUsers.forEach(u -> {
            try {
                ResponseList<Status> userTimeline = twitter.timelines().getUserTimeline(
                        u.getSocialMedia().getTwitterProfile().getTwitterUser().getScreenName()
                );
                userTimeline.forEach(status -> tweets.add(getTweet(status)));
            } catch (TwitterException e) {
                log.error(e.getErrorMessage());
            }
        });
        tweetRepository.saveAll(tweets);
        return tweetRepository.findAll(PageRequest.of(page, size));
    }

    @Override
    public Page<Tweet> getTweetsOrderByDate(String criteria, int page, int size) {
        if(isValidCriteria(criteria)) {
            Sort sort = new Sort(Sort.Direction.valueOf(criteria.toUpperCase()), DATE_FIELD);
            return tweetRepository.findAllOrdered(PageRequest.of(page, size, sort));
        } else throw new InvalidParameterException();
    }

    private Tweet getTweet(Status status) {
        return entityAdapterService.getTweetFromAPI(status);
    }

    private boolean isValidCriteria(String criteria) {
        return SORT_CRITERIA.stream()
                .anyMatch(c -> c.equals(criteria));
    }
}
