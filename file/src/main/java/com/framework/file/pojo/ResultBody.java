package com.framework.file.pojo;

import javax.xml.transform.Result;
import java.io.Serializable;

/***
 **@project: base
 **@description: common result
 **@Author: twj
 **@Date: 2019/03/28
 **/
public class ResultBody implements Serializable {
    private static final String SUCCESS_CODE = "200";

    private static final String FAIL_CODE = "500";

    private static final String NOT_FOUND = "400";

    private String message;

    private String retCode;

    private Object data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getRetCode() {
        return retCode;
    }

    public void setRetCode(String retCode) {
        this.retCode = retCode;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public ResultBody() {
    }

    private ResultBody(String message, String retCode, Object data) {
        this.message = message;
        this.retCode = retCode;
        this.data = data;
    }

    public static final ResultBody success(String message, Object data){
        return new ResultBody(message, ResultBody.SUCCESS_CODE, data);
    }

    public static final ResultBody fail(String message, Object data){
        return new ResultBody(message, ResultBody.FAIL_CODE, data);
    }

    public static final ResultBody notFound(String message, Object data){
        return new ResultBody(message, ResultBody.NOT_FOUND, data);
    }
}
