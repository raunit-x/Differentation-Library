package com.company.nodes;

public class NumberNode implements Node {
    private double value;

    public NumberNode(double value)
    {
        this.value = value;
    }

    @Override
    public String toString()
    {
        return String.format("%s", this.value);
    }
}
