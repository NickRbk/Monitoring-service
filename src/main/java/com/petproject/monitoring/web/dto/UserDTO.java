package com.petproject.monitoring.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class UserDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
