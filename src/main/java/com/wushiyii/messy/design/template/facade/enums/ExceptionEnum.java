package com.wushiyii.messy.design.template.facade.enums;


import lombok.Getter;

@Getter
public enum ExceptionEnum {


    S00000("成功"),
    P00000("处理中"),

    UNKNOWN("未知"),

    E10000("系统繁忙，请稍后重试"),
    ;


    private String msg;

    ExceptionEnum(String msg) {
        this.msg = msg;
    }

}
