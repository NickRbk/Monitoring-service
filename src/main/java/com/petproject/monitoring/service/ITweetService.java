package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.Tweet;
import org.springframework.data.domain.Page;

public interface ITweetService {
    Page<Tweet> getTweets(Long customerId, int page, int size);
    Page<Tweet> getTweetsOrderByDate(Long customerId, String criteria, int page, int size);
}
