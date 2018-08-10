package com.petproject.monitoring.service;

import com.petproject.monitoring.web.dto.CustomerDTO;

public interface ICustomerService {
    void signUp(CustomerDTO customerDTO);
    void update(Long customerId, CustomerDTO customerDTO);
    void delete(Long customerId);
}
