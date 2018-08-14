package com.petproject.monitoring.web.controller;

import com.petproject.monitoring.domain.model.Customer;
import com.petproject.monitoring.service.IAuthService;
import com.petproject.monitoring.service.ICustomerService;
import com.petproject.monitoring.service.IHelperService;
import com.petproject.monitoring.web.dto.CustomerDTO;
import com.petproject.monitoring.web.dto.CustomerResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import static com.petproject.monitoring.security.constants.SecurityConstants.HEADER_STRING;

@Validated
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private ICustomerService customerService;
    private IHelperService helperService;
    private IAuthService authService;

    @GetMapping()
    public CustomerResponse getCustomer(@RequestHeader(HEADER_STRING) String token) {
        Customer customer = customerService.getById(authService.getIdFromToken(token));
        return helperService.getCustomerResponseFromEntity(customer);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @PatchMapping()
    public void updateCustomer(@RequestBody @NotNull @Valid CustomerDTO customerDTO,
                               @RequestHeader(HEADER_STRING) String token) {
        customerService.update(authService.getIdFromToken(token), customerDTO);
    }

    @ResponseStatus(HttpStatus.ACCEPTED)
    @DeleteMapping()
    public void deleteCustomer(@RequestHeader(HEADER_STRING) String token) {
        customerService.delete(authService.getIdFromToken(token));
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/sign-up")
    public void signUp(@RequestBody @NotNull @Valid CustomerDTO customerDTO) {
        customerService.signUp(customerDTO);
    }
}
