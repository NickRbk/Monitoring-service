package com.petproject.monitoring.web.dto;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.TwitterProfile;
import com.petproject.monitoring.domain.model.TwitterUser;

public class EntityAdapter {
    public static TargetUser getUserFromDTO(Long userId, SocialMedia sm, UserDTO userDTO) {
        return TargetUser.builder()
                .id(userId)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .socialMedia(sm)
                .build();
    }

    public static TwitterProfile getTwitterProfileFromDTO(Long id, Long userId, TwitterUser twitterUser) {
        return TwitterProfile.builder()
                .id(id)
                .targetUserId(userId)
                .twitterUser(twitterUser)
                .build();
    }
}
