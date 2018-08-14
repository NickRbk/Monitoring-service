package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.*;
import com.petproject.monitoring.web.dto.CustomerDTO;
import com.petproject.monitoring.web.dto.CustomerResponse;
import com.petproject.monitoring.web.dto.TargetUserDTO;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import twitter4j.Status;
import twitter4j.User;

public interface IHelperService {
    Customer getCustomerFromDTO(Long customerId, CustomerDTO customerDTO, BCryptPasswordEncoder bCryptPasswordEncoder);
    TargetUser getUserFromDTO(Long customerId, Long targetUserId, SocialMedia sm, TargetUserDTO targetUserDTO);
    CustomerResponse getCustomerResponseFromEntity(Customer customer);
    TwitterUser getTwitterUserFromAPI(User u, boolean isTarget);
    Tweet getTweetFromAPI(Status status);
    void disableTwitterUserAsTargetIfNeeded(TargetUser targetUser);
}
