package com.company.nodes;

public class ConstantNode implements Node {
    private String value;

    public ConstantNode(String value)
    {
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("%s", this.value);
    }
}
