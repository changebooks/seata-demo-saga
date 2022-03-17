package com.github.changebooks.seata.demo.saga.biz.shop.http;

import com.github.changebooks.seata.demo.saga.repository.spi.InventorySpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author 宋欢
 */
@Repository
public class InventoryHttp implements InventorySpi {

    private static final Logger LOGGER = LoggerFactory.getLogger(InventoryHttp.class);

    @Override
    public boolean inStock(Integer productId, Integer num, Integer orderId) {
        LOGGER.info("InventoryHttp[inStock] trace, productId: {}, num: {}, orderId: {}", productId, num, orderId);

        return true;
    }

    @Override
    public boolean outStock(Integer productId, Integer num, Integer orderId) {
        LOGGER.info("InventoryHttp[outStock] trace, productId: {}, num: {}, orderId: {}", productId, num, orderId);

        // 回滚
        throw new RuntimeException("outStock failed");
    }

}
