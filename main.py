from lexer import Lexer
from parser_ import Parser
from interpreter import Interpreter
from lexer import MATH_FUNCTIONS
import sympy


def regex(expr: str):
    expr = expr.replace(' ', '')
    new_expr = ''
    for c in expr:
        if c in '^*+-/' and new_expr:
            new_expr += f' {c} '
        else:
            new_expr += c
    return new_expr


if __name__ == '__main__':
    print("SYMBOLIC DIFFERENTIATION CALCULATOR")
    print(f"ALLOWED MATH FUNCTIONS: {MATH_FUNCTIONS}")
    print(f"SYNTAX: cos(expression), OPERATIONS: [+, -, *, /]")
    while True:
        text = input("Expr > ")
        lexer = Lexer(text, {'z', 'y'})
        tokens = list(lexer.generate_tokens())
        # print(f"Tokens: {tokens}")
        parser = Parser(tokens)
        tree = parser.parse()
        # print(f"Tree: {tree}")
        interpreter = Interpreter()
        value = interpreter.visit(tree)
        # Use sympy.simplify() method
        x = sympy.symbols('x')
        smpl = regex(str(sympy.simplify(str(value))).replace('**', '^'))
        print(f"Derivative : {smpl}")
