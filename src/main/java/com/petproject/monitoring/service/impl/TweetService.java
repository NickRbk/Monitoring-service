package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.repository.TweetRepository;
import com.petproject.monitoring.exception.InvalidParameterException;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.sort.EnumGetter;
import com.petproject.monitoring.sort.SortDirection;
import com.petproject.monitoring.sort.TwitterSortKeys;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.EnumSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class TweetService implements ITweetService {
    private TweetRepository tweetRepository;
    private ITargetUserService targetUserService;

    @Override
    public Page<Tweet> getTweets(Long customerId, String key, String direction, int page, int size) {
        List<Long> targetIdList = getTargetIdList(customerId);
        if(key != null) {
            String d = getParamIfValid(SortDirection.class, direction);
            String k = getParamIfValid(TwitterSortKeys.class, key);
            Sort sort = new Sort(Sort.Direction.valueOf(d.toUpperCase()), k);
            return tweetRepository.findAllByTargetUserIdIn(targetIdList, PageRequest.of(page, size, sort));
        } else return tweetRepository.findAllByTargetUserIdIn(targetIdList, PageRequest.of(page, size));
    }

    private List<Long> getTargetIdList(Long customerId) {
        List<TargetUser> targetUsers = targetUserService.getUsersByCustomerId(customerId);
        return targetUsers.stream()
                .map(u -> u.getSocialMedia().getTwitterProfile().getTwitterUser().getId())
                .collect(Collectors.toList());
    }

    private <E extends Enum<E> & EnumGetter> String getParamIfValid(Class<E> enumToCheck, String criteria) {
        for (E value : EnumSet.allOf(enumToCheck)) {
            if(value.name().equalsIgnoreCase(criteria)) return value.getValue();
        }
        throw new InvalidParameterException();
    }
}
