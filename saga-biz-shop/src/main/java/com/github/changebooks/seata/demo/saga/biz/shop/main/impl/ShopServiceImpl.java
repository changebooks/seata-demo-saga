package com.github.changebooks.seata.demo.saga.biz.shop.main.impl;

import com.github.changebooks.seata.demo.saga.biz.shop.main.ShopService;
import io.seata.saga.engine.StateMachineEngine;
import io.seata.saga.statelang.domain.ExecutionStatus;
import io.seata.saga.statelang.domain.StateMachineInstance;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author 宋欢
 */
@Service
public class ShopServiceImpl implements ShopService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShopServiceImpl.class);

    @Autowired
    private StateMachineEngine stateMachineEngine;

    @Override
    public boolean createOrder(Integer orderId, Integer userId, Integer productId, Integer productNum, Integer payNum) {
        LOGGER.info("createOrder trace, orderId: {}, userId: {}, productId: {}, productNum: {}, payNum: {}", orderId, userId, productId, productNum, payNum);

        Map<String, Object> parameter = new HashMap<>(16);
        parameter.put("orderId", orderId);
        parameter.put("userId", userId);
        parameter.put("productId", productId);
        parameter.put("productNum", productNum);
        parameter.put("payNum", payNum);

        String businessKey = UUID.randomUUID().toString();
        StateMachineInstance stateMachineInstance = stateMachineEngine.startWithBusinessKey("CreateOrder", null, businessKey, parameter);

        ExecutionStatus executionStatus = stateMachineInstance.getStatus();
        String xid = stateMachineInstance.getId();
        LOGGER.info("createOrder start transaction, orderId: {}, userId: {}, productId: {}, productNum: {}, payNum: {}, businessKey: {}, executionStatus: {}, xid: {}",
                orderId, userId, productId, productNum, payNum, businessKey, executionStatus, xid);

        return true;
    }

}
