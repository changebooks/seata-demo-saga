package com.github.changebooks.seata.demo.saga.biz.shop.http;

import com.github.changebooks.seata.demo.saga.repository.spi.AccountSpi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

/**
 * @author 宋欢
 */
@Repository
public class AccountHttp implements AccountSpi {

    private static final Logger LOGGER = LoggerFactory.getLogger(AccountHttp.class);

    @Override
    public boolean increaseBalance(Integer userId, Integer num, Integer orderId) {
        LOGGER.info("AccountHttp[increaseBalance] trace, userId: {}, num: {}, orderId: {}", userId, num, orderId);

        return true;
    }

    @Override
    public boolean decreaseBalance(Integer userId, Integer num, Integer orderId) {
        LOGGER.info("AccountHttp[decreaseBalance] trace, userId: {}, num: {}, orderId: {}", userId, num, orderId);

        return true;
    }

}
