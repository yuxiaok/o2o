package com.o2o.enums;

public enum ShopAuthMapStateEnum {
    SUCCESS(1, "操作成功"), INNER_ERROR(-1001, "操作失败"), NULL_SHOPAUTH_ID(-1002, "ShopAuthId为空"),
    NULL_SHOPAUTH_INFO(-1003, "传入了空的信息");
    private int code;
    private String msg;

    ShopAuthMapStateEnum(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public static ShopAuthMapStateEnum getEnum(int code) {
        for (ShopAuthMapStateEnum e : ShopAuthMapStateEnum.values()) {
            if (e.getCode() == code) {
                return e;
            }
        }
        return null;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
