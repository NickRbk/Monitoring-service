package com.petproject.monitoring.service;

import com.petproject.monitoring.web.dto.SocialMediaDTO;

public interface ISocialMediaService {
    void save(Long userId, SocialMediaDTO smDTO);
}
