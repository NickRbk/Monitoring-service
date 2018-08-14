package com.petproject.monitoring.web.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponse {
    private String email;
    private String firstName;
    private String lastName;
    private String phoneNumber;
}
