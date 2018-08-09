package com.petproject.monitoring.web.dto;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class TargetUserDTO {
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
}
