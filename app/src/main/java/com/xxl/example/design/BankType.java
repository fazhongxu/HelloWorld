package com.xxl.example.design;

import androidx.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author xxl.
 * @date 2019/08/21.
 */
@IntDef({BankType.CMB,
        BankType.CCB,
        BankType.ABC})
@Retention(RetentionPolicy.SOURCE)
public @interface BankType {
    /**
     * 中国招商银行
     */
    int CMB = 1;

    /**
     * 中国建设银行
     */
    int CCB = 2;

    /**
     * 中国农业银行
     */
    int ABC = 3;
}
