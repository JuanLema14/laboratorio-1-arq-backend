package com.udea.mibanco.mapper;


import com.udea.mibanco.DTO.TransactionDTO;
import com.udea.mibanco.entity.Customer;
import com.udea.mibanco.entity.Transaction;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TransactionMapper {
    TransactionMapper INSTANCE = Mappers.getMapper(TransactionMapper.class);
    TransactionDTO toDTO(Transaction transaction);
    Transaction toEntity(TransactionDTO transactionDTO);
}
