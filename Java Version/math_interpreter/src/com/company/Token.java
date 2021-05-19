package com.company;


public class Token {
    TokenType tokenType;
    String value;

    Token(TokenType tokenType)
    {
        this.tokenType = tokenType;
        this.value = null;
    }

    Token(TokenType tokenType, String value)
    {
        this.tokenType = tokenType;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s", this.value == null ? this.tokenType.getSymbol() : this.value);
    }
}

