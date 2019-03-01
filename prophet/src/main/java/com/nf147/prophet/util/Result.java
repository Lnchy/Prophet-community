package com.nf147.prophet.util;

import org.springframework.data.domain.Page;

/**
 * 返回类
 *
 * @param <T>
 */
public class Result<T> {

    private boolean resultStatus;
    private int statusCode;
    private String statusMsg;
    private T data;
    private PageHelp pageHelp;

    public PageHelp getPageHelp() {
        return pageHelp;
    }

    public void setPageHelp(PageHelp pageHelp) {
        this.pageHelp = pageHelp;
    }

    public boolean isResultStatus() {
        return resultStatus;
    }

    public void setResultStatus(boolean resultStatus) {
        this.resultStatus = resultStatus;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusMsg() {
        return statusMsg;
    }

    public void setStatusMsg(String statusMsg) {
        this.statusMsg = statusMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static <T> Result<T> status(boolean status) {
        Result<T> result = new Result<T>();
        result.setResultStatus(status);
        result.setStatusCode(status ? 200 : 500);
        return result;
    }

    public Result<T> msg(String msg) {
        this.statusMsg = msg;
        return this;
    }

    public Result<T> code(int code) {
        this.statusCode = code;
        return this;
    }

    public Result<T> body(T data) {
        this.data = data;
        return this;
    }

    public Result<T> page(PageHelp pageHelp) {
        this.pageHelp = pageHelp;
        return this;
    }

}
