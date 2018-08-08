package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.Tweet;

import java.util.List;

public interface ITweetService {
    List<Tweet> getTweets();
}
