package com.github.changebooks.seata.demo.saga.biz.shop.main.impl;

import com.github.changebooks.seata.demo.saga.biz.shop.main.ShopRepository;
import com.github.changebooks.seata.demo.saga.repository.spi.AccountSpi;
import com.github.changebooks.seata.demo.saga.repository.spi.InventorySpi;
import com.github.changebooks.seata.demo.saga.repository.spi.OrderSpi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

/**
 * @author 宋欢
 */
@Repository("ShopRepositoryImpl")
public class ShopRepositoryImpl implements ShopRepository {

    @Autowired
    private AccountSpi accountSpi;

    @Autowired
    private InventorySpi inventorySpi;

    @Autowired
    private OrderSpi orderSpi;

    @Override
    public boolean increaseBalance(Integer userId, Integer num, Integer orderId) {
        return accountSpi.increaseBalance(userId, num, orderId);
    }

    @Override
    public boolean decreaseBalance(Integer userId, Integer num, Integer orderId) {
        return accountSpi.decreaseBalance(userId, num, orderId);
    }

    @Override
    public boolean inStock(Integer productId, Integer num, Integer orderId) {
        return inventorySpi.inStock(productId, num, orderId);
    }

    @Override
    public boolean outStock(Integer productId, Integer num, Integer orderId) {
        return inventorySpi.outStock(productId, num, orderId);
    }

    @Override
    public boolean createOrder(Integer orderId, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        return orderSpi.createOrder(orderId, userId, productId, productNum, payNum);
    }

    @Override
    public boolean deleteOrder(Integer orderId) {
        return orderSpi.deleteOrder(orderId);
    }

}
