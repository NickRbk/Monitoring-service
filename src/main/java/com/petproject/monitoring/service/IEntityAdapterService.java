package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.SocialMedia;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.model.TwitterUser;
import com.petproject.monitoring.web.dto.UserDTO;
import twitter4j.Status;
import twitter4j.User;

public interface IEntityAdapterService {
    TargetUser getUserFromDTO(Long userId, SocialMedia sm, UserDTO userDTO);
    TwitterUser getTwitterUserFromAPI(User u, boolean isTarget);
    Tweet getTweetFromAPI(Status status);
}