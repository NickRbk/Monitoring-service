package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.domain.repository.TweetRepository;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.service.ITweetService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import twitter4j.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@AllArgsConstructor
public class TweetService implements ITweetService {

    private Twitter twitter;
    private TweetRepository tweetRepository;
    private TwitterUserRepository twitterUserRepository;

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
//        return tweets;
    }

    private Tweet getTweet(Status status) {
        boolean isRetweeted = status.getRetweetedStatus() != null;

        return Tweet.builder()
                .id(status.getId())
                .createdAtTwitter(status.getCreatedAt())
                .text(isRetweeted
                        ? status.getRetweetedStatus().getText()
                        : status.getText())
                .textUrl(status.getURLEntities().length > 0
                        ? status.getURLEntities()[0].getURL()
                        : (isRetweeted && status.getRetweetedStatus().getURLEntities().length > 0)
                            ? status.getRetweetedStatus().getURLEntities()[0].getURL()
                            : null)
                .favouritesCount(isRetweeted ? status.getRetweetedStatus().getFavoriteCount() : status.getFavoriteCount())
                .retweetCount(isRetweeted
                        ? status.getRetweetedStatus().getRetweetCount()
                        : status.getRetweetCount())
                .originalAuthor(isRetweeted
                        ? getTwitterUser(status.getRetweetedStatus().getUser())
                        : null)
                .targetUser(getTwitterUser(status.getUser())).build();
    }

    private TwitterUser getTwitterUser(User u) {
        Optional<TwitterUser> twitterUser = twitterUserRepository.findByScreenName(u.getScreenName());
        if(twitterUser.isPresent()) return twitterUser.get();
        TwitterUser newTwitterUser = TwitterUser.builder()
                .userName(u.getName())
                .screenName(u.getScreenName())
                .location(u.getLocation())
                .description(u.getDescription())
                .followersCount(u.getFollowersCount())
                .friendsCount(u.getFriendsCount())
                .favouritesCount(u.getFavouritesCount())
                .statusesCount(u.getStatusesCount())
                .profileImageURL(u.getOriginalProfileImageURL()).build();
        return twitterUserRepository.save(newTwitterUser);
    }
}
