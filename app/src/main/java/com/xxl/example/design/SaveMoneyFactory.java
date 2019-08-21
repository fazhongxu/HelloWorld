package com.xxl.example.design;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xxl.
 * @date 2019/08/21.
 */
public class SaveMoneyFactory {

    private static Map<Integer,SaveMoney> sSaveMoneyMap = new HashMap<>();

    private SaveMoneyFactory() {

    }

    private static class Holder {
        private static final  SaveMoneyFactory INSTANCE = new SaveMoneyFactory();
    }

    public static SaveMoneyFactory getInstance() {
        return Holder.INSTANCE;
    }

    static {
        sSaveMoneyMap.put(BankType.ABC,new ABCSaveMoney());
        sSaveMoneyMap.put(BankType.CCB,new CCBSaveMoney());
        sSaveMoneyMap.put(BankType.CMB,new CMBSaveMoney());
    }

    public SaveMoney saveMoney(@BankType int bankType) {
        return sSaveMoneyMap.get(bankType);
    }
}
