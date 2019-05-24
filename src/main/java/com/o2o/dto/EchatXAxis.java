package com.o2o.dto;

import java.util.HashSet;

/**
 * 横轴
 */
public class EchatXAxis {
    private String type = "category";
    private HashSet<String> data;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public HashSet<String> getData() {
        return data;
    }

    public void setData(HashSet<String> data) {
        this.data = data;
    }
}
