package com.base.commom.http;

public class BaseResponse<T> {
    private int status;
    private String error;
    private T data ;

    public int getStatue() {
        return status;
    }

    public void setStatue(int statue) {
        this.status = statue;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
