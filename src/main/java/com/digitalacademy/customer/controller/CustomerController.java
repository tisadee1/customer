package com.digitalacademy.customer.controller;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping(path = "/customer")
public class CustomerController {

    private CustomerService customerService;

    @Autowired
    public CustomerController(CustomerService customerService){
        this.customerService = customerService;
    }

    @GetMapping("/list")
    public List<Customer> customerList(){
        return  customerService.getCustomerList();
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getCustomerById(@PathVariable Long id){
        Customer customer = customerService.getCustomerById(id);
        if(customer != null){
            return  ResponseEntity.ok(customer);
        }else{
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("name/{firstName}")
    public ResponseEntity<?> getCustomerById(@PathVariable String firstName){
        List<Customer> customer = customerService.getCustomerByFirstname(firstName);
        if(customer != null){
            return  ResponseEntity.ok(customer);
        }else{
            return ResponseEntity.notFound().build();
        }
    }


    @RequestMapping(method = RequestMethod.PUT,path = "{id}")
    public ResponseEntity<?> updateCustomer(@PathVariable Long id,@Valid @RequestBody Customer body){
        body.setId(id);
        Customer customer = customerService.updateCustomer(id,body);
        return customer!=null? ResponseEntity.ok(customer) : ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long id){
        return customerService.deleteCustomer(id)? ResponseEntity.ok().build():ResponseEntity.notFound().build();

    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> createCustomer(@Valid @RequestBody Customer body){
        Customer customer = customerService.createCustomer(body);
        return ResponseEntity.status(HttpStatus.CREATED).body((customer));
    }
}
