package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.service.IUserService;
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

    @Override
    public List<Tweet> getTweets(List<com.petproject.monitoring.domain.model.User> users) {
        List<Tweet> tweets = new ArrayList<>();
        users.forEach(u -> {
            try {
                ResponseList<Status> userTimeline = twitter.timelines().getUserTimeline(u.getSocialMedia().getTwitterUrl());
                userTimeline.forEach(status -> tweets.add(getTweet(u.getId(), status)));
            } catch (TwitterException e) {
                log.error(e.getErrorMessage());
            }
        });
        return tweets;
    }

    private Tweet getTweet(Long userId, Status status) {
        boolean isRetweeted = status.getRetweetedStatus() != null;

        return Tweet.builder()
                .id(status.getId())
                .userId(userId)
                .createdAt(status.getCreatedAt())
                .text(isRetweeted
                        ? status.getRetweetedStatus().getText()
                        : status.getText())
                .textUrl(status.getURLEntities().length > 0
                        ? status.getURLEntities()[0].getURL()
                        : (isRetweeted && status.getRetweetedStatus().getURLEntities().length > 0)
                            ? status.getRetweetedStatus().getURLEntities()[0].getURL()
                            : null)
                .favouriteCount(isRetweeted ? status.getRetweetedStatus().getFavoriteCount() : status.getFavoriteCount())
                .retweetCount(isRetweeted
                        ? status.getRetweetedStatus().getRetweetCount()
                        : status.getRetweetCount())
                .originalAuthor(isRetweeted
                        ? getTwitterUser(status.getRetweetedStatus().getUser())
                        : null)
                .user(getTwitterUser(status.getUser())).build();
    }

    private TwitterUser getTwitterUser(User u) {
        return TwitterUser.builder()
                .userName(u.getName())
                .screenName(u.getScreenName())
                .location(u.getLocation())
                .description(u.getDescription())
                .followersCount(u.getFollowersCount())
                .friendsCount(u.getFriendsCount())
                .favouritiesCount(u.getFavouritesCount())
                .statusesCount(u.getStatusesCount())
                .originalProfileImageURL(u.getOriginalProfileImageURL())
                .build();
    }
}
