package com.udea.mibanco.service;

import com.udea.mibanco.DTO.CustomerDTO;
import com.udea.mibanco.entity.Customer;
import com.udea.mibanco.mapper.CustomerMapper;
import com.udea.mibanco.repository.CustomerRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  @Autowired
  public CustomerService(
    CustomerRepository customerRepository,
    CustomerMapper customerMapper
  ) {
    this.customerRepository = customerRepository;
    this.customerMapper = customerMapper;
  }

  public List<CustomerDTO> getAllCustomers() {
    return customerRepository
      .findAll()
      .stream()
      .map(customerMapper::toDTO)
      .toList();
  }

  public CustomerDTO getCustomerById(Long id) {
    return customerRepository
      .findById(id)
      .map(customerMapper::toDTO)
      .orElseThrow(() -> new RuntimeException("Customer not found"));
  }

  public CustomerDTO createCustomer(CustomerDTO customerDTO) {
    Customer customer = customerMapper.toEntity(customerDTO);
    return customerMapper.toDTO(customerRepository.save(customer));
  }

  public List<CustomerDTO> getAllCustomersWithFilters(
          String firstName,
          String lastName,
          Double balanceMin,
          Double balanceMax
  ) {
    return customerRepository.findAll().stream()
            .filter(c -> firstName == null || c.getFirstName().toLowerCase().contains(firstName.toLowerCase()))
            .filter(c -> lastName == null || c.getLastName().toLowerCase().contains(lastName.toLowerCase()))
            .filter(c -> balanceMin == null || c.getBalance() >= balanceMin)
            .filter(c -> balanceMax == null || c.getBalance() <= balanceMax)
            .map(customerMapper::toDTO)
            .toList();
  }

}
