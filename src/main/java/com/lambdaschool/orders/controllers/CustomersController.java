package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.services.CustomerServices;
import com.lambdaschool.orders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomersController {
    @Autowired
    private CustomerServices customerServices;
    // http://localhost:2019/customers/orders
    @GetMapping(value = "/orders", produces = "application/json")
    public ResponseEntity<?> listAllOrders() {
        List<Customer> myList = customerServices.findAllOrders();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
    // http://localhost:2019/customers/customer/{customerid}
    @GetMapping(value = "/customer/{customerid}", produces = "application/json")
    public ResponseEntity<?> findByCustomerId(@PathVariable long customerid) {
        Customer customer = customerServices.findById(customerid);
        return new ResponseEntity<>(customer, HttpStatus.OK);
    }
    // http://localhost:2019/customers/namelike/{substring}
    @GetMapping(value = "/namelike/{substring}", produces = "application/json")
    public ResponseEntity<?> findCustomersByNameLike(@PathVariable String substring){
        List<Customer> myList = customerServices.findByNameLike(substring);
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }
    // http://localhost:2019/customers/orders/count
    @GetMapping(value = "/orders/count", produces = "application/json")
    public ResponseEntity<?> findCustomerOrderCount(){
        List<OrderCounts> rtnList = customerServices.findOrderCounts();
        return new ResponseEntity<>(rtnList, HttpStatus.OK);
    }

    // http://localhost:2019/customers/customer
    @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addCustomer(@Valid @RequestBody Customer newCustomer){
        newCustomer.setCustcode(0);
        newCustomer = customerServices.save(newCustomer);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{custcode}")
                .buildAndExpand(newCustomer.getCustcode()).toUri();
        responseHeaders.setLocation(newCustomerURI);

        return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.CREATED);
    }
    // http://localhost:2019/customers/customer/{customerid}
    @PutMapping(value = "/customer/{customerid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> replaceCustomerById(@PathVariable long customerid, @Valid @RequestBody Customer replaceCustomer){
        replaceCustomer.setCustcode(customerid);
        Customer c = customerServices.save(replaceCustomer);
        return new ResponseEntity<>(c, HttpStatus.OK);
    }
    @PatchMapping(value = "/customer/{customerid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> updateCustomerById(@PathVariable long customerid, @RequestBody Customer updateCustomer){
        customerServices.update(updateCustomer, customerid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/customer/{customerid}")
    public ResponseEntity<?> deleteCustomerById(@PathVariable long customerid){
        customerServices.delete(customerid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
