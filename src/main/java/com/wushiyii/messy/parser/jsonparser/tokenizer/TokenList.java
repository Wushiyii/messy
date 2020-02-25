package com.wushiyii.messy.parser.jsonparser.tokenizer;

import lombok.Data;
import lombok.ToString;

import java.util.ArrayList;
import java.util.List;

/**
 * @author wgq
 * @date 2020/2/25 7:56 下午
 */
@Data
@ToString
public class TokenList {

    private int pos;

    private List<Token> tokens = new ArrayList<>();

    public void add(Token token) {
        tokens.add(token);
    }

    public Token peek() {
        return pos < tokens.size() ? tokens.get(pos) : null;
    }

    public Token peekPrevious() {
        return pos - 1 < 0 ? null : tokens.get(pos - 2);
    }

    public Token next() {
        return tokens.get(pos++);
    }

    public boolean hasMore() {
        return pos < tokens.size();
    }


}
