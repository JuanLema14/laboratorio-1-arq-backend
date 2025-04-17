package com.udea.mibanco.repository;

import com.udea.mibanco.entity.Transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<Transaction,Long> {
    List<Transaction> findBySenderAccountNumberOrReceiverAccountNumber(String senderAccountNumber, String ReceiverAccountNumber);

    boolean existsBySenderAccountNumber(String customerAccountNumber);

    boolean existsByReceiverAccountNumber(String customerAccountNumber);
}
