package com.base.commom.http;

public class ApiException extends Exception {

    private int errcode  = -1;
    private String errmsg;

    public ApiException(String msg) {
        super(msg);
        this.errmsg = msg;
    }

    public ApiException(int code, String msg) {
        super(msg);
        this.errcode = code;
        this.errmsg = msg;
    }

    public int getErrcode() {
        return errcode;
    }

    public void setErrcode(int errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    @Override
    public String toString() {
        return "ApiException{" +
                "errcode=" + errcode +
                ", errmsg='" + errmsg + '\'' +
                '}';
    }
}
