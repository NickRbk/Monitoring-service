package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.Tweet;
import org.springframework.data.domain.Page;

public interface ITweetService {
    Page<Tweet> getTweets(Long customerId, String key, String direction, int page, int size);
}
