package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.OrdersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service(value = "orderservices")
public class OrderServicesImpl implements OrderServices {
    @Autowired
    private OrdersRepository ordersRepository;

    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    @Override
    public Order save(Order order){
        Order newOrder = new Order();

        if(order.getOrdnum() != 0){
            ordersRepository.findById(order.getOrdnum())
                    .orElseThrow(() -> new EntityNotFoundException("Order " + order.getOrdnum() + " not found!"));
            newOrder.setOrdnum(order.getOrdnum());
        }

        newOrder.setOrdamount(order.getOrdamount());
        newOrder.setAdvanceamount(order.getAdvanceamount());
        newOrder.setOrderdescription(order.getOrderdescription());

        Customer newCustomer = customersRepository.findById(order.getCustomer().getCustcode())
                .orElseThrow(() -> new EntityNotFoundException("Customer " + order.getCustomer().getCustcode() + " not found!"));
        newOrder.setCustomer(newCustomer);

        Set<Payment> payments = new HashSet<>();
        newOrder.setPayments(payments);
        for(Payment p: order.getPayments()){
            Payment newPayment = paymentRepository.findById(p.getPaymentid())
                    .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + " not found!"));
        newOrder.getPayments().add(newPayment);
        }

        return ordersRepository.save(newOrder);
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

    @Transactional
    @Override
    public void delete(long orderid) {
        ordersRepository.deleteById(orderid);
    }
}
