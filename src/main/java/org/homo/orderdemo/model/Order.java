package org.homo.orderdemo.model;

import org.homo.core.annotation.HomoEntity;
import org.homo.core.model.BaseEntity;

import java.math.BigDecimal;

/**
 * @author wujianchuan 2018/12/26
 */
@HomoEntity
public class Order extends BaseEntity {
    private static final long serialVersionUID = 2560385391551524826L;

    private String code;
    private BigDecimal price;

    private Order() {
    }

    public static Order newInstance(String code, BigDecimal price) {
        Order order = new Order();
        order.setCode(code);
        order.setPrice(price);
        return order;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    @Override
    public String getDescribe() {
        return super.getDescribe();
    }
}