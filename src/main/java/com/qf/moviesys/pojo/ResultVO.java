package com.qf.moviesys.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 用来封装给前端的返回数据的
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultVO<T> {
    private int code;
    private String msg;
    private T data;

    public ResultVO(int code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
