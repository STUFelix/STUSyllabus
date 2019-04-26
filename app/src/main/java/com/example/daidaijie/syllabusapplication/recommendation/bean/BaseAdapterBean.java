package com.example.daidaijie.syllabusapplication.recommendation.bean;

import java.util.List;

/**
 * 用于转换不同的item数组
 */

public class BaseAdapterBean<T> {
    List<T> data;
    int type;

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
}
