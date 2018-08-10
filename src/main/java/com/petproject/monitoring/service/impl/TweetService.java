package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.repository.TweetRepository;
import com.petproject.monitoring.exception.InvalidParameterException;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.sort.SortConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@AllArgsConstructor
public class TweetService implements ITweetService, SortConstants {
    private TweetRepository tweetRepository;
    private ITargetUserService targetUserService;

    @Override
    public Page<Tweet> getTweets(Long customerId, int page, int size) {
        List<Long> targetIdList = getTargetIdList(customerId);
        return tweetRepository.findAllByTargetUserIdIn(targetIdList, PageRequest.of(page, size));
    }

    @Override
    public Page<Tweet> getTweetsOrderByDate(Long customerId, String criteria, int page, int size) {
        if(isValidCriteria(criteria)) {
            List<Long> targetIdList = getTargetIdList(customerId);
            Sort sort = new Sort(Sort.Direction.valueOf(criteria.toUpperCase()), DATE_FIELD);
            return tweetRepository.findAllByTargetUserIdIn(targetIdList, PageRequest.of(page, size, sort));
        } else throw new InvalidParameterException();
    }

    private List<Long> getTargetIdList(Long customerId) {
        List<TargetUser> targetUsers = targetUserService.getUsersByCustomerId(customerId);
        return targetUsers.stream()
                .map(u -> u.getSocialMedia().getTwitterProfile().getTwitterUser().getId())
                .collect(Collectors.toList());
    }

    private boolean isValidCriteria(String criteria) {
        return SORT_CRITERIA.stream()
                .anyMatch(c -> c.equals(criteria));
    }
}
