package com.company.nodes;

public class MultiplyNode implements Node {
    public Node nodeA;
    public Node nodeB;

    public MultiplyNode(Node nodeA, Node nodeB)
    {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    @Override
    public String toString() {
        return String.format("(%s * %s)", nodeA, nodeB);
    }
}
