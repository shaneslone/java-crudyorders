package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.views.OrderCounts;

import java.sql.ClientInfoStatus;
import java.util.List;

public interface CustomerServices {
    Customer save(Customer customer); // POST
    void delete(long id); // DELETE
    Customer update(Customer customer, long id); // PATCH
    void deleteAll();
    List<Customer> findAllOrders();
    Customer findById(long customerid);
    List<Customer> findByNameLike(String substring);
    List<OrderCounts> findOrderCounts();
}
