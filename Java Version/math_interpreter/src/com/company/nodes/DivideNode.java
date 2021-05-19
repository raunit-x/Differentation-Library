package com.company.nodes;

public class DivideNode implements Node {
    public Node nodeA;
    public Node nodeB;

    public DivideNode(Node nodeA, Node nodeB)
    {
        this.nodeA = nodeA;
        this.nodeB = nodeB;
    }

    @Override
    public String toString() {
        return String.format("(%s / %s)", nodeA, nodeB);
    }
}
