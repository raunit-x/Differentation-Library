package com.company;

/*
      EXPR         --> TERM | TERM + EXPR | TERM - EXPR
      TERM         --> FACTOR | FACTOR * TERM | FACTOR / TERM
      FACTOR       --> FACTOR_PRIME | FACTOR ^ FACTOR_PRIME
      FACTOR_PRIME --> VAR: X | CONSTANT | NUMBER | MATH(EXPR) | + FACTOR_PRIME | - FACTOR_PRIME | [LPAREN] EXPR [RPAREN]
 */

import com.company.nodes.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Parser {
    private final ArrayList<Token> tokens;
    private int index;
    private Token currentToken;

    Parser(ArrayList<Token> tokens)
    {
        this.tokens = tokens;
        this.index = -1;
        this.currentToken = null;
        this.advance();
    }

    public void raiseError() throws Exception {
        throw new Exception(String.format("Invalid syntax: %s", this.currentToken));
    }

    private void advance()
    {
        if(++this.index >= tokens.size())
            this.currentToken = null;
        else this.currentToken = tokens.get(this.index);
    }

    private Node expr() throws Exception {
        Node result = this.term();
        Set<TokenType> acceptedTokens = Set.of(TokenType.PLUS, TokenType.MINUS);
        while (this.currentToken != null && acceptedTokens.contains(this.currentToken.tokenType)) {
            if(this.currentToken.tokenType == TokenType.PLUS)
            {
                this.advance();
                result = new AddNode(result, this.term());
            }
            else if(this.currentToken.tokenType == TokenType.MINUS)
            {
                this.advance();
                result = new SubtractNode(result, this.term());
            }
        }
        return result;
    }

    private Node factor() throws Exception {
        Node result = this.factorPrime();
        while(this.currentToken != null && this.currentToken.tokenType == TokenType.POWER)
        {
            this.advance();
            result = new PowerNode(result, this.factor());
        }
        return result;
    }

    private Node factorPrime() throws Exception {
        Token token = this.currentToken;
        Node result = null;
        if(token.tokenType == TokenType.LPAREN)
        {
            this.advance();
            result = this.expr();
            if(token.tokenType != TokenType.RPAREN)
                raiseError();
            this.advance();
        }
        else if(token.tokenType == TokenType.VAR)
        {
            this.advance();
            result = new VarNode();
        }
        else if(token.tokenType == TokenType.NUMBER)
        {
            this.advance();
            result = new NumberNode(Double.parseDouble(token.value));
        }
        else if(token.tokenType == TokenType.CONSTANT)
        {
            this.advance();
            result = new ConstantNode(token.value);
        }
        else if(token.tokenType == TokenType.MATH_FUNCTION)
        {
            Token mathToken = this.currentToken;
            this.advance();
            this.advance(); // LPAREN
            result = this.expr();
            this.advance(); // RPAREN
            result = new MathNode(mathToken, result);
        }
        else if(token.tokenType == TokenType.PLUS)
        {
            this.advance();
            result = new PlusNode(this.factorPrime());
        }
        else if(token.tokenType == TokenType.MINUS)
        {
            this.advance();
            result = new MinusNode(this.factorPrime());
        }
        return result;
    }


    private Node term() throws Exception {
        Node result = this.factor();
        Set<TokenType> acceptedTokens = Set.of(TokenType.MULTIPLY, TokenType.DIVIDE);
        while(this.currentToken != null && acceptedTokens.contains(this.currentToken.tokenType))
        {
            if(this.currentToken.tokenType == TokenType.MULTIPLY)
            {
                this.advance();
                result = new MultiplyNode(result, this.term());
            }
            else if(this.currentToken.tokenType == TokenType.DIVIDE)
            {
                this.advance();
                result = new DivideNode(result, this.term());
            }
        }
        return result;
    }

    public Node parse() throws Exception
    {
        if(this.currentToken == null)
            return null;
        Node result = this.expr();
        System.out.println(this.currentToken);
        if(this.currentToken != null)
            raiseError();
        return result;
    }

}
