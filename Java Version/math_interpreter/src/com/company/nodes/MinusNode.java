package com.company.nodes;

public class MinusNode implements Node {
    public Node node;

    public MinusNode(Node node)
    {
        this.node = node;
    }
    @Override
    public String toString() {
        return String.format("-(%s)", this.node);
    }
}
