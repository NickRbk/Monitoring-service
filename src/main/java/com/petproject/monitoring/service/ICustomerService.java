package com.petproject.monitoring.service;

import com.petproject.monitoring.domain.model.Customer;
import com.petproject.monitoring.web.dto.CustomerDTO;

public interface ICustomerService {
    Customer getById(Long customerId);
    void signUp(CustomerDTO customerDTO);
    void update(Long customerId, CustomerDTO customerDTO);
    void delete(Long customerId);
}
