package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.*;
import com.petproject.monitoring.domain.repository.TwitterUserRepository;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.web.dto.CustomerDTO;
import com.petproject.monitoring.web.dto.TargetUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import twitter4j.Status;
import twitter4j.User;

import java.util.Optional;

@Service
@AllArgsConstructor
public class EntityAdapterService implements IEntityAdapterService {
    private TwitterUserRepository twitterUserRepository;

    @Override
    public Customer getCustomerFromDTO(CustomerDTO customerDTO, BCryptPasswordEncoder bCryptPasswordEncoder) {
        return Customer.builder()
                .email(customerDTO.getEmail())
                .firstName(customerDTO.getFirstName())
                .lastName(customerDTO.getLastName())
                .password(bCryptPasswordEncoder.encode(customerDTO.getPassword()))
                .phoneNumber(customerDTO.getPhoneNumber()).build();
    }

    @Override
    public TargetUser getUserFromDTO(Long customerId, Long targetUserId, SocialMedia sm, TargetUserDTO targetUserDTO) {
        return TargetUser.builder()
                .id(targetUserId)
                .customerId(customerId)
                .firstName(targetUserDTO.getFirstName())
                .lastName(targetUserDTO.getLastName())
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
