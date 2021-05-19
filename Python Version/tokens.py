from enum import Enum
from dataclasses import dataclass


class TokenType(Enum):
    VAR = 0
    NUMBER = 1
    PLUS = 2
    MINUS = 3
    MULTIPLY = 4
    DIVIDE = 5
    POWER = 6
    LPAREN = 7
    RPAREN = 8
    MATH_FUNCTION = 9
    CONSTANT = 10


@dataclass
class Token:
    type: TokenType
    value: any = None

    def __repr__(self):
        return self.type.name + (f': {self.value}' if self.value else '')
