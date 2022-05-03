package com.byb.BaseUtil.Utils;

import lombok.Data;

import java.io.Serializable;

@Data
public class Result<T> implements Serializable {

    private T data;

    private Integer code;

    private String msg;

    public static final int SUCCESS = 200;

    public static final int FAIL = 500;

    public static final int ERROR = 400;

    public static final int NO_PERMISSION = 403;

    public Result(){
        super();
    }

    public Result(T data)
    {
        super();
        this.data = data;
    }

    public Result(T data, int code)
    {
        super();
        this.data = data;
        this.code = code;
    }

    public Result(T data, int code, String msg) {
        super();
        this.data = data;
        this.code = code;
        this.msg = msg;
    }

    public Result(int code, String msg) {
        super();
        this.code = code;
        this.msg = msg;
    }

}
