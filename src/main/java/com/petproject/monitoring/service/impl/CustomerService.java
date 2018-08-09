package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.repository.CustomerRepository;
import com.petproject.monitoring.service.ICustomerService;
import com.petproject.monitoring.service.IEntityAdapterService;
import com.petproject.monitoring.web.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {
    private IEntityAdapterService entityAdapterService;
    private CustomerRepository customerRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void signUp(CustomerDTO customerDTO) {
        customerRepository.save(entityAdapterService.getCustomerFromDTO(customerDTO, bCryptPasswordEncoder));
    }
}
