package com.udea.mibanco.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "transactions")
public class Transaction {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  @Column(nullable = false)
  private String senderAccountNumber;

  @Column(nullable = false)
  private String receiverAccountNumber;

  @Column(nullable = false)
  private Double amount;

  @Column(nullable = false)
  private LocalDateTime timestamp;

  @Column
  private String description;

  public Transaction() {}

  public Transaction(
    long id,
    String senderAccountNumber,
    String receiverAccountNumber,
    Double amount,
    LocalDateTime timeStamp,
    String description
  ) {
    this.id = id;
    this.senderAccountNumber = senderAccountNumber;
    this.receiverAccountNumber = receiverAccountNumber;
    this.amount = amount;
    this.timestamp = timeStamp;
    this.description = description;
  }

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getSenderAccountNumber() {
    return senderAccountNumber;
  }

  public void setSenderAccountNumber(String senderAccountNumber) {
    this.senderAccountNumber = senderAccountNumber;
  }

  public String getReceiverAccountNumber() {
    return receiverAccountNumber;
  }

  public void setReceiverAccountNumber(String receiverAccountNumber) {
    this.receiverAccountNumber = receiverAccountNumber;
  }

  public Double getAmount() {
    return amount;
  }

  public void setAmount(double amount) {
    this.amount = amount;
  }

  public LocalDateTime getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(LocalDateTime timeStamp) {
    this.timestamp = timeStamp;
  }

  public String getDescription() {
    return this.description;
  }

  public void setDescription(String description) {
    this.description = description;
  }
}
