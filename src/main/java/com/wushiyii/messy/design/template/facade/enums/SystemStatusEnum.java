package com.wushiyii.messy.design.template.facade.enums;



public enum SystemStatusEnum {

    SUCCESS,

    FAIL,

    PROCESS,

    UNKNOWN;

    public static SystemStatusEnum getByName(String name){
        for(SystemStatusEnum statusEnum: SystemStatusEnum.values()){
            if(statusEnum.name().equals(name)){
                return statusEnum;
            }
        }
        return null;
    }
}
