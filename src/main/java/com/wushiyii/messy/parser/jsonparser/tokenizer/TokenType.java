package com.wushiyii.messy.parser.jsonparser.tokenizer;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author wgq
 * @date 2020/2/25 7:36 下午
 */
@Getter
@AllArgsConstructor
public enum TokenType {

    BEGIN_OBJECT(1, "{"),
    END_OBJECT(2,  "}"),
    BEGIN_ARRAY(3, "["),
    END_ARRAY(4, "]"),
    NULL(5, "null"),
    NUMBER(6, "number"),
    STRING(7, "string"),
    BOOLEAN(8, "boolean"),
    SEPARATE_COLON(9, ":"),
    SEPARATE_COMMA(9, ",");

    private Integer code;
    private String desc;

}
