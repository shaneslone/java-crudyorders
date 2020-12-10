package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
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
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrderServices orderServices;
    // http://localhost:2019/orders/order/{orderid}
    @GetMapping(value = "/order/{orderid}", produces = "application/json")
    public ResponseEntity<?> findOrderById(@PathVariable long orderid) {
        Order order = orderServices.findById(orderid);
        return new ResponseEntity<>(order, HttpStatus.OK);
    }
    // http://localhost:2019/orders/advanceamount
    @GetMapping(value = "advanceamount", produces = "application/json")
    public ResponseEntity<?> findOrderByAdvanceammount(){
        List<Order> myList = orderServices.findAllByAdvanceAmmount();
        return new ResponseEntity<>(myList, HttpStatus.OK);
    }

    // http://localhost:2019/orders/order
    @PostMapping(value = "/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addOrder(@Valid @RequestBody Order newOrder){
        newOrder.setOrdnum(0);
        newOrder = orderServices.save(newOrder);

        HttpHeaders responseHeaders = new HttpHeaders();
        URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{ordnum}")
                    .buildAndExpand((newOrder.getOrdnum())).toUri();
        responseHeaders.setLocation(newOrderURI);

        return new ResponseEntity<>(newOrder, responseHeaders, HttpStatus.CREATED);
    }

    // http://localhost:2019/orders/order/{orderid}
    @PutMapping(value = "/order/{orderid}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> replaceCustomerById(@PathVariable long orderid, @Valid @RequestBody Order replaceOrder){
        replaceOrder.setOrdnum(orderid);
        Order o = orderServices.save(replaceOrder);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // http://localhost:2019/orders/order/{orderid}
    @DeleteMapping(value = "/order/{orderid}")
    public ResponseEntity<?> deleteOrderById(@PathVariable long orderid){
        orderServices.delete(orderid);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
