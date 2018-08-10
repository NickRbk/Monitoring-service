package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.service.IAuthService;
import com.petproject.monitoring.service.ITwitterProfileService;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.web.dto.SocialAliasDTO;
import com.petproject.monitoring.web.dto.TargetUserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

import static com.petproject.monitoring.security.SecurityConstants.HEADER_STRING;

@Validated
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class TargetUserController {
    private ITargetUserService targetUserService;
    private ITwitterProfileService twitterProfileService;
    private IAuthService authService;

    @GetMapping
    public List<TargetUser> getUsers(@RequestHeader(HEADER_STRING) String token) {
        return targetUserService.getUsersByCustomerId(authService.getIdFromToken(token));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public void addUser(@RequestBody @NotNull @Valid TargetUserDTO targetUserDTO,
                        @RequestHeader(HEADER_STRING) String token) {
        targetUserService.add(authService.getIdFromToken(token), targetUserDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{targetUserId}")
    public void updateUser(@PathVariable Long targetUserId,
                           @RequestBody @NotNull @Valid TargetUserDTO targetUserDTO,
                           @RequestHeader(HEADER_STRING) String token) {
        targetUserService.update(authService.getIdFromToken(token), targetUserId, targetUserDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{targetUserId}")
    public void deleteUser(@PathVariable Long targetUserId,
                           @RequestHeader(HEADER_STRING) String token) {
        targetUserService.delete(authService.getIdFromToken(token), targetUserId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("{targetUserId}/media")
    public void addSocialMediaResources(@PathVariable Long targetUserId,
                                        @RequestBody @NotNull @Valid SocialAliasDTO smDTO,
                                        @RequestHeader(HEADER_STRING) String token) {
        twitterProfileService.add(authService.getIdFromToken(token), targetUserId, smDTO);
    }
}
