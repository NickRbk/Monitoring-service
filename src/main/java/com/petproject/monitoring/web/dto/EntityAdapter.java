package com.petproject.monitoring.web.dto;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.User;

public class EntityAdapter {
    public static User getUserFromDTO(Long userId, SocialMedia sm, UserDTO userDTO) {
        return User.builder()
                .id(userId)
                .email(userDTO.getEmail())
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .socialMedia(sm)
                .build();
    }

    public static SocialMedia getSocialMediaFromDTO(Long id, Long userId, SocialMediaDTO smDTO) {
        return SocialMedia.builder()
                .id(id)
                .userId(userId)
                .facebookUrl(smDTO.getFacebookUrl())
                .twitterUrl(smDTO.getTwitterUrl())
                .build();
    }
}
