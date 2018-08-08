package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.Tweet;
import com.petproject.monitoring.service.ISocialMediaService;
import com.petproject.monitoring.service.ITweetService;
import com.petproject.monitoring.web.dto.SocialMediaDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/media")
@AllArgsConstructor
public class SocialMediaController {
    private ITweetService tweetService;

    @GetMapping
    public List<Tweet> getTweets() {
        return tweetService.getTweets();
    }
}
