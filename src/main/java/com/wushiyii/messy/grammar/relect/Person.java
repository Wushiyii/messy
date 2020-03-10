package com.wushiyii.messy.grammar.relect;


import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Person {
    public String name;
    protected Integer age;
    private String hobby;
}