package com.lambdaschool.orders.controllers;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

}
