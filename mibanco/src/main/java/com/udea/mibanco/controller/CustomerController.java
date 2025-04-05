package com.udea.mibanco.controller;

import com.udea.mibanco.DTO.CustomerDTO;
import com.udea.mibanco.service.CustomerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

    private final CustomerService customerFacade;
    public CustomerController(CustomerService customerService){
        this.customerFacade = customerService;
    }

    //Obtener todos los clientes
    @GetMapping
    public ResponseEntity<List<CustomerDTO>> getAllCustomers(){
        return ResponseEntity.ok(customerFacade.getAllCustomers());
    }

    //Obtener un cliente por un id
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id){
        return  ResponseEntity.ok(customerFacade.getCustomerById(id));
    }

    //Crear un nuevo Cliente
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO){
        if(customerDTO.getBalance() == null){
            throw new IllegalArgumentException("Balance cannot be null");
        }

        return ResponseEntity.ok(customerFacade.createCustomer(customerDTO));
    }
}
