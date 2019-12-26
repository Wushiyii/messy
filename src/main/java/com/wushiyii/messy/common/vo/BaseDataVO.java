package com.wushiyii.messy.common.vo;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class BaseDataVO {
    private Integer code;
    private Object data;
    private String message;
}