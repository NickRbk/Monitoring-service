package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.service.IAuthService;
import com.petproject.monitoring.service.ITweetService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import static com.petproject.monitoring.security.constants.SecurityConstants.HEADER_STRING;

@Validated
@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
public class SocialMediaController {
    private ITweetService tweetService;
    private IAuthService authService;

    @GetMapping()
    public Page<Tweet> getTweets(@RequestParam("page") int page,
                                 @RequestParam("size") int size,
                                 @RequestParam(value = "order", required = false) String key,
                                 @RequestHeader(HEADER_STRING) String token) {
        return tweetService.getTweets(authService.getIdFromToken(token), key, page, size);
    }
}
