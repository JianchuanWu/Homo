package org.homo.orderdemo.service;

import org.homo.core.annotation.Message;
import org.homo.core.annotation.Service;
import org.homo.pocket.annotation.Transaction;
import org.homo.core.service.AbstractService;
import org.homo.core.executor.HomoRequest;
import org.homo.orderdemo.model.Order;
import org.homo.orderdemo.repository.OrderRepositoryImpl;
import org.homo.pocket.session.Session;
import org.springframework.context.ApplicationContext;

import java.math.BigDecimal;
import java.util.function.BiFunction;

/**
 * @author wujianchuan 2018/12/29
 */
@Service(session = "order")
public class OrderServiceImpl extends AbstractService {

    @Message(type = Order.class)
    public BiFunction<HomoRequest, Session, Object> getCode = (request, session) -> "A-001";

    @Transaction
    @Message(type = Order.class)
    public BiFunction<HomoRequest, Session, Object> discount = (request, session) -> {
        Order order;
        try {
            ApplicationContext context = request.getApplicationContext();
            OrderRepositoryImpl orderRepository = context.getBean(OrderRepositoryImpl.class);
            orderRepository.setSession(session);
            long uuid = Long.parseLong(request.getParameter("uuid"));
            order = orderRepository.findOne(uuid);
            order.setPrice(order.getPrice().add(new BigDecimal("1")));
            orderRepository.getProxy().update(order, request.getUser());
            return order.getPrice().toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };

    @Transaction
    @Message(type = Order.class)
    public BiFunction<HomoRequest, Session, Object> saveOrder = (request, session) -> {
        ApplicationContext context = request.getApplicationContext();
        OrderRepositoryImpl orderRepository = context.getBean(OrderRepositoryImpl.class);
        orderRepository.setSession(session);
        Order order = Order.newInstance("A-002", new BigDecimal("12.6"));
        try {
            return orderRepository.save(order, request.getUser());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    };
}
