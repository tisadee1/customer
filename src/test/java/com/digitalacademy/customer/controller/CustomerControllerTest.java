package com.digitalacademy.customer.controller;


import com.digitalacademy.customer.model.Customer;
import com.digitalacademy.customer.service.CustomerService;
import com.digitalacademy.customer.support.CustomerSupportTest;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class CustomerControllerTest {
    @Mock
    private CustomerService customerService;
    @InjectMocks
    CustomerController customerController;
    private MockMvc mvc;
    public static final String urlCustomer = "/customer/";
    public static final String urlCustomerList = "/customer/list";
    @BeforeEach
    public void setup(){
        MockitoAnnotations.initMocks(this);
        customerController = new CustomerController(customerService);
        mvc = MockMvcBuilders.standaloneSetup(customerController).build();
    }
    @DisplayName("Test get customer should return customer list")
    @Test
    void testGetCustomerList() throws Exception {
        when(customerService.getCustomerList())
                .thenReturn(CustomerSupportTest.getCustomerList());
        MvcResult mvcResult = mvc.perform(get(urlCustomerList))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();
        JSONArray jasonArray = new JSONArray(mvcResult.getResponse().getContentAsString());
        assertEquals( "1", jasonArray.getJSONObject(0).get("id").toString());
        assertEquals( "Ryan", jasonArray.getJSONObject(0).get("firstName").toString());
        assertEquals( "Giggs", jasonArray.getJSONObject(0).get("lastName").toString());
        assertEquals( "66888888888", jasonArray.getJSONObject(0).get("phoneNo").toString());
        assertEquals( "scb@gmail.com", jasonArray.getJSONObject(0).get("email").toString());
        assertEquals( 23, jasonArray.getJSONObject(0).get("age"));
        assertEquals( "2", jasonArray.getJSONObject(1).get("id").toString());
        assertEquals( "David", jasonArray.getJSONObject(1).get("firstName").toString());
        assertEquals( "Blackham", jasonArray.getJSONObject(1).get("lastName").toString());
        assertEquals( "66888888888", jasonArray.getJSONObject(1).get("phoneNo").toString());
        assertEquals( "dbb@gmail.com", jasonArray.getJSONObject(1).get("email").toString());
        assertEquals( 40, jasonArray.getJSONObject(1).get("age"));
    }

    @DisplayName("Test get customer by id should return customer")
    @Test
    void testGetCustomerById()throws Exception{
        Long reqId = 2L;
        when(customerService.getCustomerById(reqId)).thenReturn(CustomerSupportTest.getOldNoonCustomer());

        MvcResult mvcResult =  mvc.perform(get(urlCustomer+""+reqId))
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
                .andReturn();

        JSONObject jsonObject = new JSONObject(mvcResult.getResponse().getContentAsString());

        assertEquals("2",jsonObject.get("id").toString());
        assertEquals("OldNoon",jsonObject.get("firstName").toString());
        assertEquals("OldBow",jsonObject.get("lastName").toString());
        assertEquals("66888888888",jsonObject.get("phoneNo").toString());
        assertEquals("old@old.com",jsonObject.get("email").toString());
        assertEquals(20,jsonObject.get("age"));
    }

    @DisplayName("Test get customer by id should return not found")
    @Test
    void testGetCustomerByIdNotFound() throws Exception{
        Long reqId =5L;
        MvcResult mvcResult = mvc.perform(get(urlCustomer+""+reqId))
                .andExpect(status().isNotFound())
                .andReturn();
    }

    @DisplayName("Test create customer should return success")
    @Test
    void testCreateCustomer() throws Exception{
        Customer reqCustomer = CustomerSupportTest.getReqNewCustomer();
    }

    @DisplayName("Test update customer should return id not found")
    @Test
    void testUpdateCustomerIdNotFound()throws Exception{
        Customer reqCustomer = CustomerSupportTest.getOldNoonCustomer();
        Long reqId=2L;

        ObjectMapper mapper= new ObjectMapper();
        mapper.configure(SerializationFeature.WRAP_ROOT_VALUE,false);
        ObjectWriter ow = mapper.writer().withDefaultPrettyPrinter();
        String requestJson = ow.writeValueAsString(reqCustomer);

        when(customerService.updateCustomer(reqId,reqCustomer))
                .thenReturn(null);

        MvcResult mvcResult = mvc.perform(put(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(requestJson))
                .andExpect(status().isNotFound())
                .andReturn();
        verify(customerService,times(1)).updateCustomer(reqId,reqCustomer);

    }

    @DisplayName("Test delete customer should not found")
    @Test
    void testDeleteCustomerShouldReturnNotFound()throws Exception{
        Long reqId = 4L;
        when(customerService.deleteCustomer(reqId)).thenReturn(false);


        MvcResult mvcResult = mvc.perform(delete(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();
        verify(customerService,times(1)).deleteCustomer(reqId);

    }

    @DisplayName("Test delete customer should success")
    @Test
    void testDeleteCustomer()throws Exception{
        Long reqId = 4L;
        when(customerService.deleteCustomer(reqId)).thenReturn(true);


        MvcResult mvcResult = mvc.perform(delete(urlCustomer+""+reqId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        verify(customerService,times(1)).deleteCustomer(reqId);

    }
}

