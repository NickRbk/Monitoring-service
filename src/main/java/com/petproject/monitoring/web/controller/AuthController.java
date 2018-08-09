package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.service.ICustomerService;
import com.petproject.monitoring.web.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@Validated
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private ICustomerService customerService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @NotNull @Valid CustomerDTO customerDTO) {
        customerService.signUp(customerDTO);
    }
}
