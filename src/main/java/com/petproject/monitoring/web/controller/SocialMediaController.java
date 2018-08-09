package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.service.IAuthService;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.service.ITargetUserService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.petproject.monitoring.security.SecurityConstants.HEADER_STRING;

@Validated
@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
public class SocialMediaController {
    private ITweetService tweetService;
    private ITargetUserService targetUserService;
    private IAuthService authService;

    @GetMapping()
    public Page<Tweet> getTweets(@RequestParam("page") int page,
                                 @RequestParam("size") int size,
                                 @RequestHeader(HEADER_STRING) String token) {
        List<TargetUser> targetUsers = targetUserService.getUsersByCustomerId(authService.getIdFromToken(token));
        return tweetService.getTweets(targetUsers, page, size);
    }

    @GetMapping("/sort")
    public Page<Tweet> getTweetsOrderByDate(@RequestParam("page") int page,
                                            @RequestParam("size") int size,
                                            @RequestParam("orderBy") String key) {
        return tweetService.getTweetsOrderByDate(key, page, size);
    }
}
