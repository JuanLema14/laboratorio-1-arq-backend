package com.udea.mibanco.controller;

import com.udea.mibanco.DTO.CustomerDTO;
import com.udea.mibanco.service.CustomerService;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/customers")
public class CustomerController {

  private final CustomerService customerFacade;

  public CustomerController(CustomerService customerFacade) {
    this.customerFacade = customerFacade;
  }

  @GetMapping("/listar")
  public ResponseEntity<List<CustomerDTO>> getAllCustomers(
    @RequestParam(required = false) String firstName,
    @RequestParam(required = false) String lastName,
    @RequestParam(required = false) Double balanceMin,
    @RequestParam(required = false) Double balanceMax
  ) {
    List<CustomerDTO> customers = customerFacade.getAllCustomersWithFilters(
      firstName,
      lastName,
      balanceMin,
      balanceMax
    );
    return ResponseEntity.ok(customers);
  }

  @GetMapping("/{id}")
  public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id) {
    return ResponseEntity.ok(customerFacade.getCustomerById(id));
  }

  @PostMapping("/crear")
  public ResponseEntity<CustomerDTO> createCustomer(
    @RequestBody CustomerDTO customerDTO
  ) {
    if (customerDTO.getBalance() == null) {
      throw new IllegalArgumentException("Balance cannot be null");
    }

    return ResponseEntity.ok(customerFacade.createCustomer(customerDTO));
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerDTO> updateCustomer(
    @PathVariable Long id,
    @RequestBody CustomerDTO customerDTO
  ) {
    CustomerDTO updatedCustomer = customerFacade.updateCustomer(
      id,
      customerDTO
    );
    return ResponseEntity.ok(updatedCustomer);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
    boolean deleted = customerFacade.deleteCustomer(id);
    if (deleted) {
      return ResponseEntity.noContent().build(); 
    } else {
      return ResponseEntity.status(400).body(null);
    }
  }
}
