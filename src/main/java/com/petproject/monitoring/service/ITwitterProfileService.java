package com.petproject.monitoring.service;

import com.petproject.monitoring.web.dto.SocialAliasDTO;

public interface ITwitterProfileService {
    void add(Long userId, SocialAliasDTO saDTO);
}
