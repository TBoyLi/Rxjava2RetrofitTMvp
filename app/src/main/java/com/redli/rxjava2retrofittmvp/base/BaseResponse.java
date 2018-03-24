package com.redli.rxjava2retrofittmvp.base;

/**
 * @author RedLi
 * @date 2018/3/20
 * 根据Json数据格式构建返回数据类
 */

public class BaseResponse<T> {

    /**
     * 这里是根据豆辫放回的数据封装的
     * 数据一般为
     * boolean success;
     * String message;
     * int total;
     * T data; {} or [{}] 数据
     * 可根据自己的需求来封装
     */

    /**
     * count : 10
     * start : 0
     * total : 250
     * subjects: {} or [{}] 数据
     */

    private int count;
    private int start;
    private int total;
    private T subjects;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public T getSubjects() {
        return subjects;
    }

    public void setSubjects(T subjects) {
        this.subjects = subjects;
    }
}
