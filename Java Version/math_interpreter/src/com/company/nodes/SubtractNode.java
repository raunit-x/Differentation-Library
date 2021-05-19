package com.company.nodes;

public class SubtractNode implements Node {
    public Node nodeA;
    public Node nodeB;

    public SubtractNode(Node nodeA, Node nodeB)
    {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    @Override
    public String toString() {
        return String.format("(%s - %s)", nodeA, nodeB);
    }
}
