package com.digitalacademy.customer.service;

import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    CustomerService customerService;

    @BeforeEach
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        customerService = new CustomerService(customerRepository);
    }

    @DisplayName("Test get all customer should return customer list")
    @Test
    void testGetAllCustomer(){
        List<Customer> customerList = new ArrayList<>();
        Customer customer = new Customer();
        customer.setId(0);
        customer.setFirstName("Tiw");
        customer.setLastName("Tha");
        customer.setEmail("tiw@mail.com");
        customer.setPhoneNo("020992904");
        customer.setAge(22);

        customerList.add(customer);

        customer = new Customer();
        customer.setId(1);
        customer.setFirstName("Bew");
        customer.setLastName("Tha");
        customer.setEmail("bew@mail.com");
        customer.setPhoneNo("020992904");
        customer.setAge(22);

        customerList.add(customer);

        when(customerRepository.findAll()).thenReturn(customerList);

        List<Customer> resp = customerService.getCustomerList();

        assertEquals(0,customerList.get(0).getId());
        assertEquals("Tiw",customerList.get(0).getFirstName());
        assertEquals("Tha",customerList.get(0).getLastName());
        assertEquals("tiw@mail.com",customerList.get(0).getEmail());
        assertEquals("020992904",customerList.get(0).getPhoneNo());
        assertEquals(22,customerList.get(0).getAge().intValue());



        assertEquals(1,customerList.get(1).getId());
        assertEquals("Bew",customerList.get(1).getFirstName());
        assertEquals("Tha",customerList.get(1).getLastName());
        assertEquals("bew@mail.com",customerList.get(1).getEmail());
        assertEquals("020992904",customerList.get(1).getPhoneNo());
        assertEquals(22,customerList.get(1).getAge().intValue());
    }

    @DisplayName("Test update customer should return new customer")
    @Test
    void testUpdateCustomer(){

    }
}
