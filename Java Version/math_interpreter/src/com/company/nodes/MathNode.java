package com.company.nodes;

import com.company.Token;

public class MathNode implements Node {
    public Token operation;
    public Node value;

    public MathNode(Token operation, Node value)
    {
        this.operation = operation;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s(%s)", this.operation, this.value);
    }
}
