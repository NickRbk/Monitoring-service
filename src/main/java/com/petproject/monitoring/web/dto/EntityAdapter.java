package com.petproject.monitoring.web.dto;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;

public class EntityAdapter {
    public static TargetUser getUserFromDTO(Long userId, SocialMedia sm, UserDTO userDTO) {
        return TargetUser.builder()
                .id(userId)
                .firstName(userDTO.getFirstName())
                .lastName(userDTO.getLastName())
                .socialMedia(sm)
                .build();
    }
}
