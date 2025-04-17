package com.udea.mibanco.service;

import com.udea.mibanco.DTO.CustomerDTO;
import com.udea.mibanco.entity.Customer;
import com.udea.mibanco.mapper.CustomerMapper;
import com.udea.mibanco.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
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

  public CustomerDTO updateCustomer(Long id, CustomerDTO customerDTO) {
    Customer customer = customerRepository
      .findById(id)
      .orElseThrow(() ->
        new EntityNotFoundException("Cliente no encontrado con id: " + id)
      );

    customer.setFirstName(customerDTO.getFirstName());
    customer.setLastName(customerDTO.getLastName());
    customer.setAccountNumber(customerDTO.getAccountNumber());
    customer.setBalance(customerDTO.getBalance());

    Customer updated = customerRepository.save(customer);

    return customerMapper.toDTO(updated);
  }

  public List<CustomerDTO> getAllCustomersWithFilters(
    String firstName,
    String lastName,
    Double balanceMin,
    Double saldoMax
  ) {
    List<Customer> resultados;

    if (firstName != null && balanceMin != null && saldoMax != null) {
      resultados =
        customerRepository.findByFirstNameContainingAndBalanceBetween(
          firstName,
          balanceMin,
          saldoMax
        );
    } else if (firstName != null && !firstName.isEmpty()) {
      resultados = customerRepository.findByFirstNameContaining(firstName);
    } else if (lastName != null && !lastName.isEmpty()) {
      resultados = customerRepository.findByLastNameContaining(lastName);
    } else if (balanceMin != null && saldoMax != null) {
      resultados =
        customerRepository.findByBalanceBetween(balanceMin, saldoMax);
    } else {
      resultados = customerRepository.findAll();
    }

    return resultados.stream().map(customerMapper::toDTO).toList();
  }
}
