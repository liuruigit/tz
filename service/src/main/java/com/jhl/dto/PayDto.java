package com.jhl.dto;

/**
 * Created by Administrator on 2016/2/24.
 */
public class PayDto extends BaseDto {

    String amount;
    String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "PayDto{" +
                "amount='" + amount + '\'' +
                ", type='" + type + '\'' +
                "} " + super.toString();
    }
}
