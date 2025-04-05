package com.udea.mibanco.repository;

import com.udea.mibanco.entity.Customer;
import com.udea.mibanco.mapper.CustomerMapper;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findByAccountNumber(String accountNumber);

}
