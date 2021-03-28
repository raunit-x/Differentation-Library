from dataclasses import dataclass
from tokens import Token


@dataclass
class NumberNode:
    value: float

    def __repr__(self):
        return f"{self.value}"


@dataclass
class AddNode:
    node_a: any
    node_b: any

    def __repr__(self):
        return f"({self.node_a} + {self.node_b})"


@dataclass
class SubtractNode:
    node_a: any
    node_b: any

    def __repr__(self):
        return f"({self.node_a} - {self.node_b})"


@dataclass
class MultiplyNode:
    node_a: any
    node_b: any

    def __repr__(self):
        return f"({self.node_a} * {self.node_b})"


@dataclass
class DivideNode:
    node_a: any
    node_b: any

    def __repr__(self):
        return f"({self.node_a} / {self.node_b})"


@dataclass
class PowerNode:
    node_a: any
    node_b: any

    def __repr__(self):
        return f"({self.node_a} ^ {self.node_b})"


@dataclass
class MathNode:
    operation: Token.value
    value: any

    def __repr__(self):
        return f"[MATH: {self.operation}({self.value})]"


@dataclass
class VarNode:

    def __repr__(self):
        return "<VAR, x>"


@dataclass
class ConstantNode:
    value: Token.value

    def __repr__(self):
        return f"<CONST: {self.value}>"


@dataclass
class PlusNode:
    node: any

    def __repr__(self):
        return f"(+{self.node})"


@dataclass
class MinusNode:
    node: any

    def __repr__(self):
        return f"(-{self.node})"
