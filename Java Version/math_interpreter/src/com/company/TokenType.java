package com.company;

public enum TokenType {
    NUMBER(""),
    PLUS("+"),
    MINUS("-"),
    DIVIDE("/"),
    MULTIPLY("*"),
    POWER("^"),
    LPAREN("("),
    RPAREN(")"),
    CONSTANT(""),
    MATH_FUNCTION(""),
    VAR("x");


    private final String symbol;

    public String getSymbol() {
        return symbol;
    }

    TokenType(String s) {
        this.symbol = s;
    }
}