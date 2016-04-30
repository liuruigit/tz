package com.jhl.dto;

/**
 * 模板消息封装
 * Created by Administrator on 2016/3/25.
 */
public class ModelInfoDto {
    private String first;//抬头
    private String keyword1;//第1个显示字段
    private String keyword2;//第2个显示字段
    private String keyword3;//第3个显示字段
    private String keyword4;//第4个显示字段
    private String keyword5;//第5个显示字段
    private String remark;//结尾备注

    public String getFirst() {
        return first;
    }

    public void setFirst(String first) {
        this.first = first;
    }

    public String getKeyword1() {
        return keyword1;
    }

    public void setKeyword1(String keyword1) {
        this.keyword1 = keyword1;
    }

    public String getKeyword2() {
        return keyword2;
    }

    public void setKeyword2(String keyword2) {
        this.keyword2 = keyword2;
    }

    public String getKeyword3() {
        return keyword3;
    }

    public void setKeyword3(String keyword3) {
        this.keyword3 = keyword3;
    }

    public String getKeyword4() {
        return keyword4;
    }

    public void setKeyword4(String keyword4) {
        this.keyword4 = keyword4;
    }

    public String getKeyword5() {
        return keyword5;
    }

    public void setKeyword5(String keyword5) {
        this.keyword5 = keyword5;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
