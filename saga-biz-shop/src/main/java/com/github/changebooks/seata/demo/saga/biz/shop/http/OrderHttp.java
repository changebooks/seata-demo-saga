package com.github.changebooks.seata.demo.saga.biz.shop.http;

import com.github.changebooks.seata.demo.saga.repository.spi.OrderSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author 宋欢
 */
@Repository
public class OrderHttp implements OrderSpi {

    private static final Logger LOGGER = LoggerFactory.getLogger(OrderHttp.class);

    @Override
    public boolean createOrder(Integer orderId, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        LOGGER.info("OrderHttp[createOrder] trace, orderId: {}, userId: {}, productId: {}, productNum: {}, payNum: {}",
                orderId, userId, productId, productNum, payNum);

        return true;
    }

    @Override
    public boolean deleteOrder(Integer orderId) {
        LOGGER.info("OrderHttp[deleteOrder] trace, orderId: {}", orderId);

        return true;
    }

}
