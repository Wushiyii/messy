package com.wushiyii.messy.design.pattern.responsibilityChain;

import lombok.Data;

import java.util.Objects;

/**
 * @author wgq
 * @date 2020/3/1 7:21 下午
 */
@Data
public abstract class Handler {

    private Handler nextHandler;

    private String type;

    public Handler(String type) {
        this.type = type;
    }

    public void setNextHandler(Handler nextHandler) {
        this.nextHandler = nextHandler;
    }

    public final void handlePack(String type) {
        if (type.equals(this.type)) {
            doHandle(type);
        } else {
            if (Objects.nonNull(nextHandler)) {
                nextHandler.doHandle(type);
            }
        }
    }

    protected abstract void doHandle(String type);
}
