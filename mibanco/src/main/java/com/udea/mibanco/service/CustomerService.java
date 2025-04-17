package com.udea.mibanco.service;

import com.udea.mibanco.DTO.CustomerDTO;
import com.udea.mibanco.entity.Customer;
import com.udea.mibanco.mapper.CustomerMapper;
import com.udea.mibanco.repository.CustomerRepository;
import com.udea.mibanco.repository.TransactionRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;
  private final TransactionRepository transactionRepository;

  public CustomerService(
    CustomerRepository customerRepository,
    TransactionRepository transactionRepository,
    CustomerMapper customerMapper
  ) {
    this.customerRepository = customerRepository;
    this.transactionRepository = transactionRepository;
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
    Double balanceMax
  ) {
    return customerRepository
      .findAll()
      .stream()
      .filter(c ->
        firstName == null ||
        c.getFirstName().toLowerCase().contains(firstName.toLowerCase())
      )
      .filter(c ->
        lastName == null ||
        c.getLastName().toLowerCase().contains(lastName.toLowerCase())
      )
      .filter(c -> balanceMin == null || c.getBalance() >= balanceMin)
      .filter(c -> balanceMax == null || c.getBalance() <= balanceMax)
      .map(customerMapper::toDTO)
      .toList();
  }

  public boolean customerHasTransactions(Long customerId) {
    Optional<Customer> customer = customerRepository.findById(customerId);

    if (customer.isEmpty()) {
      return false;
    }

    String accountNumber = customer.get().getAccountNumber();

    return (
      transactionRepository.existsBySenderAccountNumber(accountNumber) ||
      transactionRepository.existsByReceiverAccountNumber(accountNumber)
    );
  }

  public boolean deleteCustomer(Long customerId) {
    if (customerHasTransactions(customerId)) {
      return false;
    }
    customerRepository.deleteById(customerId);
    return true;
  }
}
