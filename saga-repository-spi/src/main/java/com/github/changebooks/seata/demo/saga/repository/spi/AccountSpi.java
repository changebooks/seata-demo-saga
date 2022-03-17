package com.github.changebooks.seata.demo.saga.repository.spi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 预存款
 *
 * @author 宋欢
 */
@RequestMapping("account")
public interface AccountSpi {
    /**
     * 加余额
     *
     * @param userId  用户id
     * @param num     增加的金额，单位：分
     * @param orderId 订单号
     * @return 成功？
     */
    @PostMapping(value = "/increase-balance")
    boolean increaseBalance(@RequestParam("userId") Integer userId,
                            @RequestParam("num") Integer num,
                            @RequestParam("orderId") Integer orderId);

    /**
     * 扣余额
     *
     * @param userId  用户id
     * @param num     扣减的金额，单位：分
     * @param orderId 订单号
     * @return 成功？
     */
    @PostMapping(value = "/decrease-balance")
    boolean decreaseBalance(@RequestParam("userId") Integer userId,
                            @RequestParam("num") Integer num,
                            @RequestParam("orderId") Integer orderId);

}
