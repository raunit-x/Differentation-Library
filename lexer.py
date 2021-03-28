from tokens import TokenType, Token
import string


WHITESPACE = ' \t\n'
MATH_FUNCTIONS = {
    'sin', 'cos', 'tan', 'cot', 'log', 'exp', 'sin_inv', 'cos_inv', 'tan_inv', 'cot_inv', 'sec', 'csc'
}
DIGITS = string.digits


class Lexer:
    def __init__(self, text, constants=None):
        """
        :param text: expression to be differentiated
        :param constants: a set of different constants fed in as variables
        """
        self.current_char = None
        self.text = iter(text)
        self.constants = constants
        self.advance()

    def advance(self):
        try:
            self.current_char = next(self.text)
        except StopIteration:
            self.current_char = None

    def generate_number(self):
        decimal_count = 0
        num_str = self.current_char
        self.advance()
        while self.current_char and (self.current_char in DIGITS or self.current_char == '.'):
            decimal_count += (self.current_char == '.')
            if decimal_count > 1:
                break
            num_str += self.current_char
            self.advance()
        return Token(TokenType.NUMBER, float(num_str))

    def generate_math_function_or_constant(self):
        function_str = self.current_char
        self.advance()
        while self.current_char and (self.current_char in string.ascii_lowercase or self.current_char == '_'):
            function_str += self.current_char
            self.advance()
        if function_str in MATH_FUNCTIONS:
            return Token(TokenType.MATH_FUNCTION, function_str)
        if function_str in self.constants:
            return Token(TokenType.CONSTANT, function_str)
        return None

    def generate_tokens(self):
        while self.current_char:
            if self.current_char in WHITESPACE:
                self.advance()
            elif self.current_char == '+':
                self.advance()
                yield Token(TokenType.PLUS)
            elif self.current_char == '-':
                self.advance()
                yield Token(TokenType.MINUS)
            elif self.current_char == '*':
                self.advance()
                yield Token(TokenType.MULTIPLY)
            elif self.current_char == '/':
                self.advance()
                yield Token(TokenType.DIVIDE)
            elif self.current_char == '^':
                self.advance()
                yield Token(TokenType.POWER)
            elif self.current_char == 'x':
                self.advance()
                yield Token(TokenType.VAR)
            elif self.current_char == '(':
                self.advance()
                yield Token(TokenType.LPAREN)
            elif self.current_char == ')':
                self.advance()
                yield Token(TokenType.RPAREN)
            elif self.current_char == '.' or self.current_char in DIGITS:
                yield self.generate_number()
            elif self.current_char in string.ascii_lowercase:
                yield self.generate_math_function_or_constant()
            else:
                raise Exception(f"Illegal Character: {self.current_char}")
