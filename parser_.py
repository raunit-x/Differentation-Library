"""
GRAMMAR RULES:
        EXPR --> TERM | TERM + EXPR | TERM - EXPR
        TERM --> FACTOR | FACTOR * TERM | FACTOR / TERM
      FACTOR --> FACTOR_PRIME | FACTOR ^ FACTOR_PRIME
FACTOR_PRIME --> <ID, X> | CONSTANT | NUMBER | MATH(EXPR)
"""

from tokens import TokenType
from nodes import *


class Parser:
    def __init__(self, tokens):
        self.current_token = None
        self.tokens = iter(tokens)
        self.advance()

    def advance(self):
        try:
            self.current_token = next(self.tokens)
        except StopIteration:
            self.current_token = None

    def raise_error(self):
        raise Exception('Invalid Syntax!')

    def parse(self):
        if not self.current_token:
            return None
        result = self.expr()
        if self.current_token:
            self.raise_error()
        return result

    def expr(self):
        result = self.term()

        while self.current_token and self.current_token.type in (TokenType.PLUS, TokenType.MINUS):
            if self.current_token.type == TokenType.PLUS:
                self.advance()
                result = AddNode(result, self.term())
            elif self.current_token.type == TokenType.MINUS:
                self.advance()
                result = SubtractNode(result, self.term())
        return result

    def term(self):
        result = self.factor()
        while self.current_token and self.current_token.type in (TokenType.MULTIPLY, TokenType.DIVIDE):
            if self.current_token.type == TokenType.MULTIPLY:
                self.advance()
                result = MultiplyNode(result, self.factor())
            elif self.current_token.type == TokenType.DIVIDE:
                self.advance()
                result = DivideNode(result, self.factor())
        return result

    def factor(self):
        result = self.factor_prime()
        while self.current_token and self.current_token.type == TokenType.POWER:
            self.advance()
            result = PowerNode(result, self.factor_prime())
        return result

    def factor_prime(self):
        token = self.current_token
        if token.type == TokenType.NUMBER:
            self.advance()
            return NumberNode(token.value)
        if token.type == TokenType.VAR:
            self.advance()
            return VarNode()
        if token.type == TokenType.CONSTANT:
            self.advance()
            return ConstantNode(token.value)
        if token.type == TokenType.MATH_FUNCTION:
            math_token = token
            self.advance()
            self.advance()  # skip LPAREN
            result = self.expr()
            self.advance()  # skip RPAREN
            return MathNode(math_token.value, result)
        if token.type == TokenType.PLUS:
            self.advance()
            return PlusNode(self.factor_prime())
        if token.type == TokenType.MINUS:
            self.advance()
            return PlusNode(self.factor_prime())
        self.raise_error()
