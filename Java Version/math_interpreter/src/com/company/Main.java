package com.company;

import com.company.nodes.Node;
import com.company.nodes.NumberNode;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        while(true)
        {
            System.out.print("> ");
            String expr = scanner.nextLine();
            if(expr.equals("quit") || expr.equals("q"))
                break;
            Lexer lexer = new Lexer(expr);
            ArrayList<Token> tokens = lexer.generateTokens();
            System.out.printf("Tokens: %s\n", tokens);
            Parser parser = new Parser(tokens);
            Node result = parser.parse();
            System.out.printf("Parser: %s\n", result);
            Interpreter interpreter = new Interpreter();
            String res = interpreter.visit(result).toString();
            System.out.printf("Derivative: %s\n", res);
        }
    }
}
