from lexer import Lexer


while True:
    text = input("Expr > ")
    lexer = Lexer(text, {'z', 'y'})
    tokens = lexer.generate_tokens()
    print(list(tokens))
