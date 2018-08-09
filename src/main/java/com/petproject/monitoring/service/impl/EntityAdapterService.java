package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.User;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EntityAdapterService implements IEntityAdapterService {
    private TwitterUserRepository twitterUserRepository;


    @Override
    public TargetUser getUserFromDTO(Long userId, SocialMedia sm, UserDTO userDTO) {
        return TargetUser.builder()
                .id(userId)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .socialMedia(sm).build();
    }

    @Override
    public TwitterUser getTwitterUserFromAPI(User u, boolean isTarget) {
        return TwitterUser.builder()
                .userName(u.getName())
                .screenName(u.getScreenName())
                .location(u.getLocation())
                .description(u.getDescription())
                .followersCount(u.getFollowersCount())
                .friendsCount(u.getFriendsCount())
                .favouritesCount(u.getFavouritesCount())
                .statusesCount(u.getStatusesCount())
                .isTarget(isTarget)
                .profileImageURL(u.getOriginalProfileImageURL()).build();
    }

    @Override
    public Tweet getTweetFromAPI(Status status) {
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
                .favouritesCount(isRetweeted
                        ? status.getRetweetedStatus().getFavoriteCount()
                        : status.getFavoriteCount())
                .retweetCount(isRetweeted
                        ? status.getRetweetedStatus().getRetweetCount()
                        : status.getRetweetCount())
                .originalAuthor(isRetweeted
                        ? getTwitterUser(status.getRetweetedStatus().getUser())
                        : null)
                .targetUser(getTwitterUser(status.getUser()))
                .build();
    }

    private TwitterUser getTwitterUser(User u) {
        Optional<TwitterUser> twitterUser = twitterUserRepository.findByScreenName(u.getScreenName());
        return twitterUser
                .orElseGet(() -> twitterUserRepository.save(getTwitterUserFromAPI(u, false)));
    }
}
