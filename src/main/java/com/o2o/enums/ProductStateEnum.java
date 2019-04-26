package com.o2o.enums;

/**
 * @Author yukai
 * @Date 2018年9月6日
 */
public enum ProductStateEnum {
    SUCCESS(1, "成功"), EMPTY(-3002, "空"), ERROR(-3001, "插入product失败"), PRODUCT_ID_EMPTY(-3002, "商品ID为空");

    private int code;
    private String msg;

    private ProductStateEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
