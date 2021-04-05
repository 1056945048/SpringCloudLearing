package com.itcloud.quartz.module;

import com.itcloud.quartz.enums.ErrorEnum;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;


import java.io.Serializable;

/**
 * @author yangkun
 * @date 2021-03-24
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Accessors(chain = true)//set方法返回当前对象
public class Result<T> implements Serializable {
    /**
     * 是否成功
     */
    private boolean success;
    /**
     * 说明
     */
    private String msg;
    /**
     * 返回数据
     */
    private T data;

    public Result(boolean success, String msg) {
        this.success = success;
        this.msg = msg;
    }

    public static <T> Result ok() {
        return new Result(true, "请求成功");
    }

    public static <T> Result ok(T data) {
        return new Result(true, "请求成功").setData(data);
    }

    public static <T> Result ok(T data, String msg) {
        return new Result(true, msg).setData(data);
    }

    public static <T> Result error() {
        return new Result(false, ErrorEnum.UNKNOWN.getMsg());
    }

    public static <T> Result error(String msg) {
        return new Result(false, msg);
    }

    public static <T> Result error(String msg, T data) {
        return new Result(false, msg).setData(data);
    }

    public static <T> Result error(ErrorEnum errorEnum) {
        return new Result(false, errorEnum.getMsg());
    }
}
