package com.jhl.service;

import com.jhl.dto.ModelInfoDto;

/**
 * 模板消息类型
 * Created by Administrator on 2016/3/22.
 */
public class WxModelFactory {
    public enum ModelType {

        BALANCE(0),         //余额
        RECHARGE_NOTICE(1),       //充值通知
        RECHARGE_SUCCESS(2),      //充值成功通知
        CASH_ADVANCE_APPLY(3),    //提现申请通知
        CASH_ADVANCE_SUCCESS(4),  //提现成功通知
        CASH_ADVANCE_FAIL(5),     //提现失败通知
        INVEST_APPLY(6),        //投资申请通知
        INVEST_STATUS(7);         //投资状态更新

        private int value;

        ModelType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    public static ModelInfoDto buildMsg(ModelType modelType, Object... params) {
        ModelInfoDto modelInfoDto = null;
        switch (modelType) {
            case BALANCE:
                modelInfoDto = new ModelInfoDto();
                modelInfoDto.setKeyword1(params[0].toString());
                modelInfoDto.setKeyword2(params[1].toString());
                modelInfoDto.setKeyword3(params[2].toString());
                break;
            case RECHARGE_NOTICE:
                modelInfoDto = new ModelInfoDto();
                modelInfoDto.setKeyword1(params[0].toString());
                modelInfoDto.setKeyword2(params[1].toString());
                modelInfoDto.setKeyword3(params[2].toString());
                modelInfoDto.setKeyword4(params[3].toString());
                break;
            case RECHARGE_SUCCESS:
                modelInfoDto = new ModelInfoDto();
                modelInfoDto.setKeyword1(params[0].toString());
                modelInfoDto.setKeyword2(params[1].toString());
                break;
            case CASH_ADVANCE_APPLY:
                modelInfoDto = new ModelInfoDto();
                modelInfoDto.setKeyword1(params[0].toString());
                modelInfoDto.setKeyword2(params[1].toString());
                modelInfoDto.setKeyword3(params[2].toString());
                break;
            case CASH_ADVANCE_SUCCESS:
                modelInfoDto = new ModelInfoDto();
                modelInfoDto.setKeyword1(params[0].toString());
                modelInfoDto.setKeyword2(params[1].toString());
                break;
            case CASH_ADVANCE_FAIL:
                modelInfoDto = new ModelInfoDto();
                modelInfoDto.setKeyword1(params[0].toString());
                modelInfoDto.setKeyword2(params[1].toString());
                break;
            case INVEST_APPLY:
                modelInfoDto = new ModelInfoDto();
                modelInfoDto.setKeyword1(params[0].toString());
                modelInfoDto.setKeyword2(params[1].toString());
                modelInfoDto.setKeyword3(params[2].toString());
                modelInfoDto.setKeyword4(params[3].toString());
                break;
            case INVEST_STATUS:
                modelInfoDto = new ModelInfoDto();
                modelInfoDto.setKeyword1(params[0].toString());
                modelInfoDto.setKeyword2(params[1].toString());
                modelInfoDto.setKeyword3(params[2].toString());
                modelInfoDto.setKeyword4(params[3].toString());
                modelInfoDto.setKeyword5(params[4].toString());
                break;
            default:
                break;
        }

        return modelInfoDto;
    }

}
