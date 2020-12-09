package com.lambdaschool.orders.services;

import com.lambdaschool.orders.models.Agent;
import com.lambdaschool.orders.models.Customer;
import com.lambdaschool.orders.models.Order;
import com.lambdaschool.orders.models.Payment;
import com.lambdaschool.orders.repositories.AgentsRepository;
import com.lambdaschool.orders.repositories.CustomersRepository;
import com.lambdaschool.orders.repositories.PaymentRepository;
import com.lambdaschool.orders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Transactional
@Service(value = "customerservices")
public class CustomerServicesImpl implements CustomerServices {
    @Autowired
    private CustomersRepository customersRepository;

    @Autowired
    private AgentsRepository agentsRepository;

    @Autowired
    private PaymentRepository paymentRepository;



    @Transactional
    @Override
    public Customer save(Customer customer){
        Customer newCustomer = new Customer();

        if(customer.getCustcode() != 0){
            customersRepository.findById(customer.getCustcode())
                    .orElseThrow(() -> new EntityNotFoundException("Customer " + customer.getCustcode() + " not found!"));
            newCustomer.setCustcode(customer.getCustcode());
        }

        newCustomer.setCustname(customer.getCustname());
        newCustomer.setCustcity(customer.getCustcity());
        newCustomer.setWorkingarea(customer.getWorkingarea());
        newCustomer.setCustcountry(customer.getCustcountry());
        newCustomer.setGrade(customer.getGrade());
        newCustomer.setOpeningamt(customer.getOpeningamt());
        newCustomer.setReceiveamt(customer.getReceiveamt());
        newCustomer.setPaymentamt(customer.getPaymentamt());
        newCustomer.setOutstandingamt(customer.getOutstandingamt());
        newCustomer.setPhone(customer.getPhone());
        Agent newAgent = agentsRepository.findById(customer.getAgent().getAgentcode())
                                    .orElseThrow(() -> new EntityNotFoundException("Agent " + customer.getAgent().getAgentcode() + " not found!"));
        newCustomer.setAgent(newAgent);

        for(Order o : customer.getOrders()){
            Order newOrder = new Order();
            newOrder.setOrdamount(o.getOrdamount());
            newOrder.setAdvanceamount(o.getAdvanceamount());
            newOrder.setCustomer(newCustomer);
            newOrder.setOrderdescription(o.getOrderdescription());
            Set<Payment> payments = new HashSet<>();
            newOrder.setPayments(payments);
            for(Payment p : o.getPayments()){
                Payment newPayment = paymentRepository.findById(p.getPaymentid())
                                .orElseThrow(() -> new EntityNotFoundException("Payment " + p.getPaymentid() + "not found!"));
                newOrder.getPayments().add(newPayment);
            }
            newCustomer.getOrders().add(newOrder);
        }

        return customersRepository.save(newCustomer);
    }

    @Override
    public List<Customer> findAllOrders() {
        List<Customer> rtnList = new ArrayList<>();
        customersRepository.findAll().iterator().forEachRemaining(rtnList::add);
        return rtnList;
    }

    @Override
    public Customer findById(long customerid) {
        Customer c = customersRepository.findById(customerid)
                .orElseThrow(() -> new EntityNotFoundException("Customer with id " + customerid + " not found!"));
        return c;
    }

    @Override
    public List<Customer> findByNameLike(String substring) {
        List<Customer> list = customersRepository.findByCustnameContainingIgnoringCase(substring);
        return list;
    }

    @Override
    public List<OrderCounts> findOrderCounts() {
        List<OrderCounts> list = customersRepository.findOrderCounts();
        return list;
    }

    @Transactional
    @Override
    public void delete(long id) {
        customersRepository.deleteAll();
    }

    @Override
    public Customer update(Customer customer, long id) {
        return null;
    }

    @Override
    public void deleteAll() {

    }
}
