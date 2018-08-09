package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.repository.TweetRepository;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.service.ITweetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class TweetService implements ITweetService {

    private Twitter twitter;
    private IEntityAdapterService entityAdapterService;
    private TweetRepository tweetRepository;

    @Override
    public List<Tweet> getTweets(List<TargetUser> targetUsers) {
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
        return tweetRepository.findAll();
    }

    private Tweet getTweet(Status status) {
        return entityAdapterService.getTweetFromAPI(status);
    }
}
