package com.wushiyii.messy.parser.jsonparser.tokenizer;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @author wgq
 * @date 2020/2/25 7:43 下午
 */
@Data
@AllArgsConstructor
public class Token {

    private TokenType tokenType;
    private String value;

}
