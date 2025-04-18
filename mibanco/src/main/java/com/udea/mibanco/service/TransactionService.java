package com.udea.mibanco.service;

import com.udea.mibanco.DTO.TransactionDTO;
import com.udea.mibanco.entity.Customer;
import com.udea.mibanco.entity.Transaction;
import com.udea.mibanco.repository.CustomerRepository;
import com.udea.mibanco.repository.TransactionRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransactionService {

  @Autowired
  private TransactionRepository transactionRepository;

  @Autowired
  private CustomerRepository customerRepository;

  public TransactionDTO transferMoney(TransactionDTO transactionDTO) {
    if (
      transactionDTO.getSenderAccountNumber() == null ||
      transactionDTO.getReceiverAccountNumber() == null
    ) {
      throw new IllegalArgumentException(
        "Sender Account Number or Receiver Account Number cannot be null"
      );
    }

    Customer sender = customerRepository
      .findByAccountNumber(transactionDTO.getSenderAccountNumber())
      .orElseThrow(() ->
        new IllegalArgumentException("Sender Account number does not exist")
      );
    Customer receiver = customerRepository
      .findByAccountNumber(transactionDTO.getReceiverAccountNumber())
      .orElseThrow(() ->
        new IllegalArgumentException("Receiver Account number does not exist")
      );

    if (sender.getBalance() < transactionDTO.getAmount()) {
      throw new IllegalArgumentException("Sender balance does not enough");
    }

    sender.setBalance(sender.getBalance() - transactionDTO.getAmount());
    receiver.setBalance(receiver.getBalance() + transactionDTO.getAmount());

    customerRepository.save(sender);
    customerRepository.save(receiver);

    Transaction transaction = new Transaction();
    transaction.setSenderAccountNumber(sender.getAccountNumber());
    transaction.setReceiverAccountNumber(receiver.getAccountNumber());
    transaction.setAmount(transactionDTO.getAmount());
    transaction.setTimestamp(LocalDateTime.now());
    transaction.setDescription(transactionDTO.getDescription());
    transaction = transactionRepository.save(transaction);

    TransactionDTO savedTransaction = new TransactionDTO();
    savedTransaction.setId(transaction.getId());
    savedTransaction.setSenderAccountNumber(sender.getAccountNumber());
    savedTransaction.setReceiverAccountNumber(receiver.getAccountNumber());
    savedTransaction.setAmount(transactionDTO.getAmount());
    savedTransaction.setDescription(transactionDTO.getDescription());
    return savedTransaction;
  }

  public List<TransactionDTO> getTransactionsForAccount(String accountNumber) {
    List<Transaction> transactions = transactionRepository.findBySenderAccountNumberOrReceiverAccountNumber(
      accountNumber,
      accountNumber
    );
    return transactions
      .stream()
      .map(transaction -> {
        TransactionDTO dto = new TransactionDTO();
        dto.setId(transaction.getId());
        dto.setSenderAccountNumber(transaction.getSenderAccountNumber());
        dto.setReceiverAccountNumber(transaction.getReceiverAccountNumber());
        dto.setAmount(transaction.getAmount());
        dto.setTimestamp(transaction.getTimestamp());
        dto.setDescription(transaction.getDescription());
        return dto;
      })
      .collect(Collectors.toList());
  }
}
