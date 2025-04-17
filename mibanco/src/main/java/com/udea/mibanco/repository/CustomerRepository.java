package com.udea.mibanco.repository;

import com.udea.mibanco.entity.Customer;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
  Optional<Customer> findByAccountNumber(String accountNumber);

  List<Customer> findByFirstNameContaining(String firstName);

  List<Customer> findByLastNameContaining(String lastName);

  List<Customer> findByBalanceBetween(Double balanceMin, Double balanceMax);

  List<Customer> findByFirstNameContainingAndBalanceBetween(
    String firstName,
    Double balanceMin,
    Double balanceMax
  );
}
