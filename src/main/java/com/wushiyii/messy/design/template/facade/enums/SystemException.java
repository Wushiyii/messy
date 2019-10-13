package com.wushiyii.messy.design.template.facade.enums;


import lombok.Data;

import java.text.MessageFormat;

@Data
public class SystemException extends RuntimeException {


    private SystemStatusEnum exceptionEnum;

    private String code;

    private String msg;

    public SystemException() {
        super();
    }

    public SystemException(ExceptionEnum bizExceptionEnum) {
        this(bizExceptionEnum.name(), bizExceptionEnum.getMsg(),null);
    }

    public SystemException(ExceptionEnum bizExceptionEnum, SystemStatusEnum exceptionEnum) {
        this(bizExceptionEnum.name(), bizExceptionEnum.getMsg(), exceptionEnum);
    }

    public SystemException(ExceptionEnum bizExceptionEnum,String msg, SystemStatusEnum exceptionEnum) {
        this(bizExceptionEnum.name(), msg, exceptionEnum);
    }

    public SystemException(String errorCode, String errorMsg, SystemStatusEnum exceptionEnum) {
        super(MessageFormat.format("code:{0}, msg:{1}", errorCode, errorMsg));
        this.code = errorCode;
        this.msg = errorMsg;
        this.exceptionEnum = exceptionEnum;
    }


}
