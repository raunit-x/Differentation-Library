from lexer import Lexer
from parser_ import Parser
from interpreter import Interpreter
from lexer import MATH_FUNCTIONS


if __name__ == '__main__':
    print("SYMBOLIC DIFFERENTIATION CALCULATOR")
    print(f"ALLOWED MATH FUNCTIONS: {MATH_FUNCTIONS}")
    print(f"SYNTAX: cos(expression), OPERATIONS: [+, -, *, /]")
    while True:
        text = input("Expr > ")
        lexer = Lexer(text, {'z', 'y'})
        tokens = list(lexer.generate_tokens())
        print(f"Tokens: {tokens}")
        parser = Parser(tokens)
        tree = parser.parse()
        interpreter = Interpreter()
        value = interpreter.visit(tree)
        print(f"Value: {value}")
