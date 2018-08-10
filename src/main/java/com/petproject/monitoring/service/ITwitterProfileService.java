package com.petproject.monitoring.service;

import com.petproject.monitoring.web.dto.SocialAliasDTO;

public interface ITwitterProfileService {
    void add(Long customerId, Long targetUserId, SocialAliasDTO saDTO);
}
