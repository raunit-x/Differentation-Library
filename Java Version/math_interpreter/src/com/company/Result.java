package com.company;

public class Result {
    private String value;
    public Result(String value)
    {
        this.value = value;
    }

    @Override
    public String toString() {
        return this.value;
    }
}
