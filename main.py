from lexer import Lexer
from parser_ import Parser
from interpreter import Interpreter


while True:
    text = input("Expr > ")
    lexer = Lexer(text, {'z', 'y'})
    tokens = list(lexer.generate_tokens())
    print(f"Tokens: {tokens}")
    parser = Parser(tokens)
    tree = parser.parse()
    # print(f"Tree: {tree}")
    interpreter = Interpreter()
    value = interpreter.visit(tree)
    print(f"Value: {value}")
