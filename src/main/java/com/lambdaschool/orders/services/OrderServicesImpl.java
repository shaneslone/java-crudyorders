package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Service(value = "orderservices")
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrdersRepository ordersRepository;

    @Transactional
    @Override
    public Order save(Order order){
        return ordersRepository.save(order);
    }

    @Override
    public Order findById(long orderid) {
        Order o = ordersRepository.findById(orderid)
                .orElseThrow(() -> new EntityNotFoundException("Order with id " + orderid + " not found!"));
        return o;
    }
    @Override
    public List<Order> findAllByAdvanceAmmount() {
        List<Order> rtnList = new ArrayList<>();
        List<Order> tempList = new ArrayList<>();
        ordersRepository.findAll().iterator().forEachRemaining(tempList::add);
        for(Order o : tempList){
            if(o.getAdvanceamount() > 0.0){
                rtnList.add(o);
            }
        }
        return rtnList;
    }
}
