package com.company;


import java.nio.file.attribute.UserPrincipalLookupService;
import java.util.*;

public class Lexer {
    private Character currentChar;
    private String text;
    private Set<String> constantExpressions;
    private int index;
    private ArrayList<Token> tokens;


    private static final String WHITESPACE = " \n\t";
    private static final String DIGITS = "0123456789";
    private static final Set<String> MATH_OPERATIONS = Set.of("cos", "sin", "tan", "exp", "log", "sin_inv", "cos_inv", "tan_inv");
    private static final String ASCII_LOWERCASE = "abcdefghijklmnopqrstuvwxyz_";


    Lexer(String text)
    {
        this.text = text;
        this.currentChar = null;
        this.constantExpressions = new HashSet<>();
        this.index = -1;
        this.tokens = new ArrayList<>();
        this.advance();
    }

    Lexer(String text, HashSet<String> constantExpressions)
    {
        this(text);
        this.constantExpressions = constantExpressions;
    }

    private void advance()
    {
        if(++this.index >= this.text.length())
            this.currentChar = null;
        else
            this.currentChar = text.charAt(this.index);
    }

    private Token generateNumber()
    {
        int dec_count = 0;
        StringBuilder number = new StringBuilder();
        while(this.currentChar != null && (this.currentChar == '.' || DIGITS.indexOf(this.currentChar) != -1))
        {
            if(this.currentChar == '.')
                ++dec_count;
            if(dec_count > 1)
                break;
            number.append(this.currentChar);
            this.advance();
        }
        return new Token(TokenType.NUMBER, number.toString());
    }

    private Token generateMathFunctionOrConstant()
    {
        StringBuilder functionString = new StringBuilder();
        while(this.currentChar != null && (ASCII_LOWERCASE.indexOf(this.currentChar) != -1))
        {
            functionString.append(this.currentChar);
            this.advance();
        }
        String function = functionString.toString();
        if(MATH_OPERATIONS.contains(function))
        {
            return new Token(TokenType.MATH_FUNCTION, function);
        }
        if(this.constantExpressions != null && this.constantExpressions.contains(function))
        {
            return new Token(TokenType.CONSTANT, function);
        }
        return null;
    }


    public ArrayList<Token> generateTokens() throws Exception {
        while(this.currentChar != null)
        {
            if(WHITESPACE.indexOf(this.currentChar) != -1)
            {
                this.advance();
            }
            else if(this.currentChar == '+')
            {
                this.advance();
                tokens.add(new Token(TokenType.PLUS));
            }
            else if(this.currentChar == '-')
            {
                this.advance();
                tokens.add(new Token(TokenType.MINUS));
            }
            else if(this.currentChar == '/')
            {
                this.advance();
                tokens.add(new Token(TokenType.DIVIDE));
            }
            else if(this.currentChar == '*')
            {
                this.advance();
                tokens.add(new Token(TokenType.MULTIPLY));
            }
            else if(this.currentChar == '^')
            {
                this.advance();
                tokens.add(new Token(TokenType.POWER));
            }
            else if(this.currentChar == '(')
            {
                this.advance();
                tokens.add(new Token(TokenType.LPAREN));
            }
            else if(this.currentChar == ')')
            {
                this.advance();
                tokens.add(new Token(TokenType.RPAREN));
            }
            else if(this.currentChar == 'x')
            {
                this.advance();
                tokens.add(new Token(TokenType.VAR));
            }
            else if(this.currentChar == '.' || DIGITS.indexOf(this.currentChar) != -1)
            {
                tokens.add(this.generateNumber());
            }
            else if(ASCII_LOWERCASE.indexOf(this.currentChar) != -1)
            {
                tokens.add(this.generateMathFunctionOrConstant());
            }
            else
            {
                throw new Exception(String.format("Illegal Character: %s", this.currentChar));
            }
        }
        return this.tokens;
    }

}
