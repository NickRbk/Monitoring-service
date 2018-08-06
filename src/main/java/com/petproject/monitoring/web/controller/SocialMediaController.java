package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.service.ISocialMediaService;
import com.petproject.monitoring.web.dto.SocialMediaDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/api/users/{userId}/media")
@AllArgsConstructor
public class SocialMediaController {
    private ISocialMediaService smService;

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping()
    public void addSocialMediaResources(@PathVariable Long userId,
                                        @RequestBody @NotNull @Valid SocialMediaDTO smDTO) {
        smService.save(userId, smDTO);
    }
}
