package com.udea.mibanco.controller;

import com.udea.mibanco.DTO.TransactionDTO;
import com.udea.mibanco.service.TransactionService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
public class TransactionController {

    private final TransactionService transactionFacade;

    public TransactionController(TransactionService transactionService){
        this.transactionFacade = transactionService;
    }

    //Crear una nueva transacción
    @PostMapping
    public ResponseEntity<TransactionDTO> createTransaction(@RequestBody TransactionDTO transactionDTO){
        if(transactionDTO.getSenderAccountNumber() == null || transactionDTO.getReceiverAccountNumber() == null){
            throw new IllegalArgumentException("Sender or Receiver Account Number cannot be null");
        }
        if(transactionDTO.getAmount() == null || transactionDTO.getAmount() <= 0){
            throw new IllegalArgumentException("Amount must be greater than zero");
        }
        return ResponseEntity.ok(transactionFacade.transferMoney(transactionDTO));
    }

    //Consultar el histórico de transacciones por número de cuenta
    @GetMapping("/account/{accountNumber}")
    public ResponseEntity<List<TransactionDTO>> getTransactionsByAccount(@PathVariable String accountNumber){
        return ResponseEntity.ok(transactionFacade.getTransactionsForAccount(accountNumber));
    }
}
