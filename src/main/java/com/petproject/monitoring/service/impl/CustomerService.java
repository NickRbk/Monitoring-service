package com.petproject.monitoring.service.impl;

import com.petproject.monitoring.domain.model.Customer;
import com.petproject.monitoring.domain.model.TargetUser;
import com.petproject.monitoring.domain.repository.CustomerRepository;
import com.petproject.monitoring.exception.NotFoundException;
import com.petproject.monitoring.service.ICustomerService;
import com.petproject.monitoring.service.IHelperService;
import com.petproject.monitoring.service.ITargetUserService;
import com.petproject.monitoring.web.dto.CustomerDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CustomerService implements ICustomerService {
    private IHelperService helperService;
    private CustomerRepository customerRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    private ITargetUserService targetUserService;

    @Override
    public void signUp(CustomerDTO customerDTO) {
        customerRepository.save(helperService.getCustomerFromDTO(null, customerDTO, bCryptPasswordEncoder));
    }

    @Override
    public void update(Long customerId, CustomerDTO customerDTO) {
        customerRepository.save(helperService.getCustomerFromDTO(customerId, customerDTO, bCryptPasswordEncoder));
    }

    @Override
    public Customer getById(Long customerId) {
        return customerRepository.findById(customerId).orElseThrow(NotFoundException::new);
    }

    @Override
    public void delete(Long customerId) {
        List<TargetUser> targetUsers = targetUserService.getUsersByCustomerId(customerId);
        targetUsers.forEach(targetUser -> targetUserService.delete(targetUser));
        customerRepository.deleteById(customerId);
    }
}
