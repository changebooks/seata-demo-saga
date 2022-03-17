package com.github.changebooks.seata.demo.saga.biz.shop.main;

/**
 * 数据
 *
 * @author 宋欢
 */
public interface ShopRepository {
    /**
     * 加余额
     *
     * @param userId  用户id
     * @param num     增加的金额，单位：分
     * @param orderId 订单号
     * @return 成功？
     */
    boolean increaseBalance(Integer userId, Integer num, Integer orderId);

    /**
     * 扣余额
     *
     * @param userId  用户id
     * @param num     扣减的金额，单位：分
     * @param orderId 订单号
     * @return 成功？
     */
    boolean decreaseBalance(Integer userId, Integer num, Integer orderId);

    /**
     * 入库
     *
     * @param productId 商品id
     * @param num       增加的库存
     * @param orderId   订单号
     * @return 成功？
     */
    boolean inStock(Integer productId, Integer num, Integer orderId);

    /**
     * 出库
     *
     * @param productId 商品id
     * @param num       扣减的库存
     * @param orderId   订单号
     * @return 成功？
     */
    boolean outStock(Integer productId, Integer num, Integer orderId);

    /**
     * 创建订单
     *
     * @param orderId    订单号
     * @param userId     用户id
     * @param productId  商品id
     * @param productNum 商品数
     * @param payNum     支付金额，单位：分
     * @return 成功？
     */
    boolean createOrder(Integer orderId, Integer userId, Integer productId, Integer productNum, Integer payNum);

    /**
     * 删除订单
     *
     * @param orderId 订单号
     * @return 成功？
     */
    boolean deleteOrder(Integer orderId);

}
