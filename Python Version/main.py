from lexer import Lexer
from parser_ import Parser
from interpreter import Interpreter
from lexer import MATH_FUNCTIONS
import sympy


def format_expr(expr: str):
    expr = expr.replace(' ', '')
    new_expr = ''
    for c in expr:
        if c in '^*+-/' and new_expr:
            new_expr += f' {c} '
        else:
            new_expr += c
    return new_expr


def get_derivative(expr, constants=None):
    x = sympy.symbols('x')
    # expr = expr.replace('exp', 'EXP')
    expr = str(sympy.simplify(expr.replace('^', '**'))).replace('exp', 'EXP').replace('**', '^')
    if not constants:
        constants = {'z', 'y'}
    lexer = Lexer(expr, constants)
    tokens = list(lexer.generate_tokens())
    # print(tokens)
    parser = Parser(tokens)
    tree = parser.parse()
    interpreter = Interpreter()
    expr_prime = str(interpreter.visit(tree)).lower()
    # print(expr_prime)
    try:
        return format_expr(str(sympy.simplify(expr_prime.replace('^', '**'))).replace('**', '^'))
    except Exception as e:
        return expr_prime


if __name__ == '__main__':
    print("SYMBOLIC DIFFERENTIATION CALCULATOR")
    print(f"ALLOWED MATH FUNCTIONS: {MATH_FUNCTIONS}")
    print(f"SYNTAX: cos(expression), OPERATIONS: [+, -, *, /, ^]")
    print("ENTER ['EXIT/exit/Q/q'] TO QUIT")
    while True:
        text = input("Expr > ")
        if text in ['EXIT', 'exit', 'Q', 'q']:
            print("TERMINATING....")
            exit()
        derivative_expr = get_derivative(text)
        print(f"Derivative: {derivative_expr.lower()}")
