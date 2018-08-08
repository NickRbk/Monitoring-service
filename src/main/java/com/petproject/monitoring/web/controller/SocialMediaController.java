package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.domain.model.User;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.service.IUserService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
public class SocialMediaController {
    private ITweetService tweetService;
    private IUserService userService;

    @GetMapping
    public List<Tweet> getTweets() {
        List<User> users = userService.getUsers();
        return tweetService.getTweets(users);
    }
}
