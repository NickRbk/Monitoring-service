package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITweetService {
    Page<Tweet> getTweets(List<TargetUser> targetUsers, int page, int size);
    Page<Tweet> getTweetsOrderByDate(String criteria, int page, int size);
}
