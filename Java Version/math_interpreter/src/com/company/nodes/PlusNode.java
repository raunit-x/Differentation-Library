package com.company.nodes;

public class PlusNode implements Node {
    public Node node;

    public PlusNode(Node node)
    {
        this.node = node;
    }
    @Override
    public String toString() {
        return String.format("+(%s)", this.node);
    }
}
