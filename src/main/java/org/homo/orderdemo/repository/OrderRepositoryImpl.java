package org.homo.orderdemo.repository;

import org.homo.authority.model.User;
import org.homo.orderdemo.model.Order;
import org.homo.core.repository.AbstractRepository;
import org.springframework.stereotype.Repository;

/**
 * @author wujianchuan 2018/12/26
 */
@Repository
public class OrderRepositoryImpl extends AbstractRepository<Order> implements OrderRepository {

    @Override
    public Order save(Order entity, User operator) throws Exception {
        return (Order) this.session.save(entity);
    }

    @Override
    public Order update(Order entity, User operator) throws Exception {
        return (Order) this.session.update(entity);
    }

    @Override
    public int delete(Order entity, User operator) throws Exception {
        return this.session.delete(entity);
    }

    @Override
    public Order findOne(long uuid) throws Exception {
        return (Order) session.findOne(Order.class, uuid);
    }
}
