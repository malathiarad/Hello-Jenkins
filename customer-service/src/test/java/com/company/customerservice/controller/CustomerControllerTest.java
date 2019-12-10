package com.company.customerservice.controller;

import com.company.customerservice.model.Customer;
import com.company.customerservice.repository.CustomerRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(CustomerController.class)
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class CustomerControllerTest {

    @MockBean
    CustomerRepository repo;

    private ObjectMapper mapper = new ObjectMapper();

    @Autowired
    MockMvc mockMvc;

    @Before
    public void setUp() throws Exception {
        setUpCustomerRepoMock();
    }

    @Test
    public void shouldSaveCustomer() throws Exception {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setStreet("Main Street");
        customer.setCity("Chicago");
        customer.setZip("60004");
        customer.setEmail("jsmith@yahoo.com");
        customer.setPhone("1234567891");

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("John");
        customer1.setLastName("Smith");
        customer1.setStreet("Main Street");
        customer1.setCity("Chicago");
        customer1.setZip("60004");
        customer1.setEmail("jsmith@yahoo.com");
        customer1.setPhone("1234567891");
        String input = mapper.writeValueAsString(customer);
        String output = mapper.writeValueAsString(customer1);
        mockMvc.perform(
                post("/customer")
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().json(output));
    }

    @Test
    public void shouldGetCustomerById() throws Exception {
        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("John");
        customer1.setLastName("Smith");
        customer1.setStreet("Main Street");
        customer1.setCity("Chicago");
        customer1.setZip("60004");
        customer1.setEmail("jsmith@yahoo.com");
        customer1.setPhone("1234567891");
        String output = mapper.writeValueAsString(customer1);
        mockMvc.perform(
                get("/customer/{id}", 1)
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output));
    }

    @Test
    public void shouldGetAllCustomers() throws Exception {
        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("John");
        customer1.setLastName("Smith");
        customer1.setStreet("Main Street");
        customer1.setCity("Chicago");
        customer1.setZip("60004");
        customer1.setEmail("jsmith@yahoo.com");
        customer1.setPhone("1234567891");

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        String output = mapper.writeValueAsString(customers);
        mockMvc.perform(
                get("/customer")
        ).andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().json(output));
    }

    @Test
    public void shouldUpdateCustomer() throws Exception {
        Customer customerUpdated = new Customer();
        customerUpdated.setCustomerId(1);
        customerUpdated.setFirstName("Sam");
        customerUpdated.setLastName("Rock");
        customerUpdated.setStreet("Chicago Street");
        customerUpdated.setCity("Newyork");
        customerUpdated.setZip("45627");
        customerUpdated.setEmail("srock@yahoo.com");
        customerUpdated.setPhone("9876543210");
        String input = mapper.writeValueAsString(customerUpdated);
        mockMvc.perform(
                put("/customer/{id}", 1)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void shouldDeleteCustomer() throws Exception {
        mockMvc.perform(
                delete("/customer/{id}", 1)
        ).andDo(print())
                .andExpect(status().isNoContent());
    }

    @Test
    public void shouldGetExceptionWhenInvalidId() throws Exception {
        Customer customerUpdated = new Customer();
        customerUpdated.setCustomerId(1);
        customerUpdated.setFirstName("Sam");
        customerUpdated.setLastName("Rock");
        customerUpdated.setStreet("Chicago Street");
        customerUpdated.setCity("Newyork");
        customerUpdated.setZip("45627");
        customerUpdated.setEmail("srock@yahoo.com");
        customerUpdated.setPhone("9876543210");
        String input = mapper.writeValueAsString(customerUpdated);
        mockMvc.perform(
                put("/customer/{id}", 5)
                        .content(input)
                        .contentType(MediaType.APPLICATION_JSON)
        ).andDo(print())
                .andExpect(status().isBadRequest());
    }

    private void setUpCustomerRepoMock() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setStreet("Main Street");
        customer.setCity("Chicago");
        customer.setZip("60004");
        customer.setEmail("jsmith@yahoo.com");
        customer.setPhone("1234567891");

        Customer customer1 = new Customer();
        customer1.setCustomerId(1);
        customer1.setFirstName("John");
        customer1.setLastName("Smith");
        customer1.setStreet("Main Street");
        customer1.setCity("Chicago");
        customer1.setZip("60004");
        customer1.setEmail("jsmith@yahoo.com");
        customer1.setPhone("1234567891");

        Customer customerUpdated = new Customer();
        customerUpdated.setCustomerId(1);
        customerUpdated.setFirstName("Sam");
        customerUpdated.setLastName("Rock");
        customerUpdated.setStreet("Chicago Street");
        customerUpdated.setCity("Newyork");
        customerUpdated.setZip("45627");
        customerUpdated.setEmail("srock@yahoo.com");
        customerUpdated.setPhone("9876543210");

        Optional<Customer> optionalCustomer = Optional.of(customer1);

        List<Customer> customers = new ArrayList<>();
        customers.add(customer1);

        doReturn(customer1).when(repo).save(customer);
        doReturn(customerUpdated).when(repo).save(customerUpdated);
        doReturn(optionalCustomer).when(repo).findById(1);
        doReturn(customers).when(repo).findAll();
    }

}