package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.service.ITargetUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Validated
@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
public class SocialMediaController {
    private ITweetService tweetService;
    private ITargetUserService targetUserService;

    @GetMapping()
    public Page<Tweet> getTweets(@RequestParam("page") int page,
                                 @RequestParam("size") int size) {
        List<TargetUser> targetUsers = targetUserService.getUsers();
        return tweetService.getTweets(targetUsers, page, size);
    }

    @GetMapping("/sort")
    public Page<Tweet> getTweetsOrderByDate(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("orderBy") String key) {
        return tweetService.getTweetsOrderByDate(key, page, size);
    }
}
