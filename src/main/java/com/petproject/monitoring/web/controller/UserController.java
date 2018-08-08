package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.service.ISocialMediaService;
import com.petproject.monitoring.service.IUserService;
import com.petproject.monitoring.web.dto.SocialMediaDTO;
import com.petproject.monitoring.web.dto.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/users")
@AllArgsConstructor
public class UserController {
    private IUserService userService;
    private ISocialMediaService smService;

    @GetMapping
    public List<TargetUser> getUsers() {
        return userService.getUsers();
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping()
    public void addUser(@RequestBody @NotNull @Valid UserDTO userDTO) {
        userService.add(userDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping("/{userId}")
    public void updateUser(@PathVariable Long userId,
                           @RequestBody @NotNull @Valid UserDTO userDTO) {
        userService.update(userId, userDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping("/{userId}")
    public void deleteUser(@PathVariable Long userId) {
        userService.delete(userId);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PostMapping("{userId}/media")
    public void addSocialMediaResources(@PathVariable Long userId,
                                        @RequestBody @NotNull @Valid SocialMediaDTO smDTO) {
        smService.save(userId, smDTO);
    }
}
