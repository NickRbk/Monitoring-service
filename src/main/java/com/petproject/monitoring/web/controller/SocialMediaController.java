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
@RequestMapping("/api/users/{userId}/media")
@AllArgsConstructor
public class SocialMediaController {
    private ISocialMediaService smService;
    private ITweetService tweetService;

    @GetMapping
    public List<Tweet> getTweets(@PathVariable Long userId) {
        return tweetService.getTweets();
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping()
    public void addSocialMediaResources(@PathVariable Long userId,
                                        @RequestBody @NotNull @Valid SocialMediaDTO smDTO) {
        smService.save(userId, smDTO);
    }
}
