package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;

import java.util.List;

public interface OrderServices {
    Order findById(long orderid);
    Order save(Order order);
    void delete(long orderid);
    List<Order> findAllByAdvanceAmmount();
}
