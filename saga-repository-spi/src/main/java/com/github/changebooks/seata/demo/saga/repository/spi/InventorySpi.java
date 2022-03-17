package com.github.changebooks.seata.demo.saga.repository.spi;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 库存
 *
 * @author 宋欢
 */
@RequestMapping("inventory")
public interface InventorySpi {
    /**
     * 入库
     *
     * @param productId 商品id
     * @param num       增加的库存
     * @param orderId   订单号
     * @return 成功？
     */
    @PostMapping(value = "/in-stock")
    boolean inStock(@RequestParam("productId") Integer productId,
                    @RequestParam("num") Integer num,
                    @RequestParam("orderId") Integer orderId);

    /**
     * 出库
     *
     * @param productId 商品id
     * @param num       扣减的库存
     * @param orderId   订单号
     * @return 成功？
     */
    @PostMapping(value = "/out-stock")
    boolean outStock(@RequestParam("productId") Integer productId,
                     @RequestParam("num") Integer num,
                     @RequestParam("orderId") Integer orderId);

}
