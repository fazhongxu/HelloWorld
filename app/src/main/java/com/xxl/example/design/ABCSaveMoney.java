package com.xxl.example.design;

/**
 * 中国农业银行存款
 * @author xxl.
 * @date 2019/08/21.
 */
public class ABCSaveMoney implements SaveMoney {
    /**
     * 存钱
     *
     * @param amount 金额
     */
    @Override
    public double saveMoney(float amount) {
        return amount * 0.001;
    }
}
