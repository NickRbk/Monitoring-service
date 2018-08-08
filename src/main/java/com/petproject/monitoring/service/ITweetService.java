package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.model.User;

import java.util.List;

public interface ITweetService {
    List<Tweet> getTweets(List<User> users);
}
