package com.company.customerservice.controller;

import com.company.customerservice.model.Customer;
import com.company.customerservice.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@CacheConfig(cacheNames = {"customer"})
public class CustomerController {

    @Autowired
    CustomerRepository repository;

    @CachePut(key = "#result.getCustomerId()")
    @RequestMapping(value = "/customer", method = RequestMethod.POST)
    @ResponseStatus(HttpStatus.CREATED)
    public Customer addCustomer(@RequestBody @Valid Customer customer) {
        System.out.println("CREATING CUSTOMER");
        return repository.save(customer);
    }

    @Cacheable
    @RequestMapping(value = "/customer", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public List<Customer> getAllCustomers() {
        return repository.findAll();
    }

    @RequestMapping(value = "/customer/{id}", method = RequestMethod.GET)
    @ResponseStatus(HttpStatus.OK)
    public Customer getCustomer(@PathVariable int id) {
        System.out.println("GETTING CUSTOMER ID :"+id);
        return repository.findById(id).get();
    }

    @CacheEvict(key = "#customer.getCustomerId()")
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.PUT)
    @ResponseStatus(HttpStatus.OK)
    public void updateCustomer(@RequestBody @Valid Customer customer, @PathVariable int id) {
        if (id != customer.getCustomerId()) {
            throw new IllegalArgumentException("Id does not match with the id provided Customer Information");
        }
        System.out.println("UPDATING CUSTOMER ID = " + id);
        repository.save(customer);

    }

    @CacheEvict
    @RequestMapping(value = "/customer/{id}", method = RequestMethod.DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCustomer(@PathVariable int id) {
        System.out.println("DELETING CUSTOMER ID = " + id   );
        repository.deleteById(id);
    }
}
