package com.company.customerservice.repository;

import com.company.customerservice.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.ImportAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@ImportAutoConfiguration(RefreshAutoConfiguration.class)
public class CustomerRepositoryTest {
    @Autowired
    CustomerRepository repository;

    @Before
    public void setUp() throws Exception {
        List<Customer> customerList = repository.findAll();
        customerList.stream()
                .forEach(customer -> repository.delete(customer));
    }

    @Test
    public void shouldAddGetOneCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setStreet("Main Street");
        customer.setCity("Chicago");
        customer.setZip("60004");
        customer.setEmail("jsmith@yahoo.com");
        customer.setPhone("1234567891");
        customer = repository.save(customer);

        Optional<Customer> optionalCustomer = repository.findById(customer.getCustomerId());

        assertEquals(optionalCustomer.get(), customer);
    }

    @Test
    public void shouldGetCustomerById() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setStreet("Main Street");
        customer.setCity("Chicago");
        customer.setZip("60004");
        customer.setEmail("jsmith@yahoo.com");
        customer.setPhone("1234567891");
        customer = repository.save(customer);

        Optional<Customer> optionalCustomer = repository.findById(customer.getCustomerId());

        assertEquals(optionalCustomer.get(), customer);
    }

    @Test
    public void shouldGetAllCustomers() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setStreet("Main Street");
        customer.setCity("Chicago");
        customer.setZip("60004");
        customer.setEmail("jsmith@yahoo.com");
        customer.setPhone("1234567891");
        customer = repository.save(customer);

        Customer customer1 = new Customer();
        customer1.setFirstName("John");
        customer1.setLastName("Smith");
        customer1.setStreet("Main Street");
        customer1.setCity("Chicago");
        customer1.setZip("60004");
        customer1.setEmail("jsmith@yahoo.com");
        customer1.setPhone("1234567891");
        customer1 = repository.save(customer1);

        List<Customer> customerList = repository.findAll();

        assertEquals(2, customerList.size());
        assertEquals(customer, customerList.get(0));
        assertEquals(customer1, customerList.get(1));

    }

    @Test
    public void shouldUpdateCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setStreet("Main Street");
        customer.setCity("Chicago");
        customer.setZip("60004");
        customer.setEmail("jsmith@yahoo.com");
        customer.setPhone("1234567891");
        customer = repository.save(customer);

        customer.setStreet("Chicago Ave");
        customer.setCity("NewYork");
        customer.setZip("34598");
        repository.save(customer);

        Optional<Customer> optionalCustomer = repository.findById(customer.getCustomerId());

        assertEquals(optionalCustomer.get(), customer);
        assertEquals(customer.getCustomerId(), optionalCustomer.get().getCustomerId());

    }

    @Test
    public void shouldDeleteCustomer() {
        Customer customer = new Customer();
        customer.setFirstName("John");
        customer.setLastName("Smith");
        customer.setStreet("Main Street");
        customer.setCity("Chicago");
        customer.setZip("60004");
        customer.setEmail("jsmith@yahoo.com");
        customer.setPhone("1234567891");
        customer = repository.save(customer);

        repository.delete(customer);

        Optional<Customer> optionalCustomer = repository.findById(customer.getCustomerId());

        assertNull(optionalCustomer.orElse(null));

    }

}