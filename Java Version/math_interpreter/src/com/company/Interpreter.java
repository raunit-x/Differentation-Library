package com.company;

import com.company.nodes.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.InvalidParameterException;
import java.util.HashMap;

public class Interpreter {
    private final HashMap<String, String> DERIVATIVE_TABLE = new HashMap<>(){{
            put("cos", "-sin(x)");
            put("COS", "-SIN(x)");
            put("sin", "cos(x)");
            put("SIN", "COS(x)");
            put("log", "1 / (x)");
            put("LOG", "1 / (x)");
            put("tan", "sec^2(x)");
            put("TAN", "SEC^2(x)");
            put("cot", "-csc^2(x)");
            put("COT", "-CSC^2(x)");
            put("EXP", "EXP(x)");
            put("sec", "tan(x) * sec(x)");
            put("SEC", "TAN(x) * SEC(x)");
            put("csc", "-cot(x) * csc(x)");
            put("CSC", "-COT(x) * CSC(x)");
            put("cos_inv", "-( 1 / (1 - x ^ 2) ^ 0.5)");
            put("COS_INV", "-( 1 / (1 - x ^ 2) ^ 0.5)");
            put("sin_inv", "(1 / (1 - x ^ 2) ^ 0.5)");
            put("SIN_INV", "(1 / (1 - x ^ 2) ^ 0.5)");
            put("tan_inv", "(1 / (1 + x ^ 2))");
            put("TAN_INV", "(1 / (1 + x ^ 2))");
    }};

    public Result visit(Node node) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        String[] splitNames = node.getClass().toString().split("\\.");
        String className = "visit" + splitNames[splitNames.length - 1];
        Class<?>[] paramTypes = {Node.class};
        Method visitMethod = this.getClass().getDeclaredMethod(className, paramTypes);
        return (Result) visitMethod.invoke(this, node);
    }

    private Result visitNumberNode(Node node) throws InvalidParameterException
    {
        if(!(node instanceof NumberNode))
            throw new InvalidParameterException();
        return new Result("0");
    }

    private Result visitConstantNode(Node node) throws InvalidParameterException
    {
        if(!(node instanceof ConstantNode))
            throw new InvalidParameterException();
        return new Result("0");
    }

    private Result visitVarNode(Node node) throws InvalidParameterException
    {
        if(!(node instanceof VarNode))
            throw new InvalidParameterException();
        return new Result("1");
    }

    private Result visitAddNode(Node node) throws InvalidParameterException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(!(node instanceof AddNode))
            throw  new InvalidParameterException();
        return new Result(String.format("%s + %s", visit(((AddNode) node).nodeA).toString(), visit(((AddNode) node).nodeB).toString()));
    }

    private Result visitSubtractNode(Node node) throws InvalidParameterException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(!(node instanceof SubtractNode))
            throw  new InvalidParameterException();
        return new Result(String.format("%s + %s", visit(((SubtractNode) node).nodeA).toString(), visit(((SubtractNode) node).nodeB).toString()));
    }

    private Result visitMultiplyNode(Node node) throws InvalidParameterException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(!(node instanceof MultiplyNode))
            throw  new InvalidParameterException();
        String f = visit(((MultiplyNode) node).nodeA).toString();
        String g = visit(((MultiplyNode) node).nodeB).toString();
        return new Result(String.format("((%s * %s) + (%s * %s))", f, ((MultiplyNode) node).nodeB, g, ((MultiplyNode) node).nodeA));
    }

    private Result visitDivideNode(Node node) throws InvalidParameterException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(!(node instanceof DivideNode))
            throw  new InvalidParameterException();
        String f_prime = visit(((DivideNode) node).nodeA).toString();
        String g_prime = visit(((DivideNode) node).nodeB).toString();
        String f = ((DivideNode) node).nodeA.toString();
        String g = ((DivideNode) node).nodeB.toString();
        return new Result(String.format("((%s * %s) - (%s * %s)) / %s ^ 2", g, f_prime, f, g_prime, g));
    }

    private Result visitPlusNode(Node node) throws InvalidParameterException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(!(node instanceof PlusNode))
            throw  new InvalidParameterException();
        return new Result(String.format("%s", visit(((PlusNode) node).node)));
    }

    private Result visitMinusNode(Node node) throws InvalidParameterException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(!(node instanceof MinusNode))
            throw  new InvalidParameterException();
        return new Result(String.format("-%s", visit(((MinusNode) node).node)));
    }

    private Result visitPowerNode(Node node) throws InvalidParameterException, NoSuchMethodException, IllegalAccessException, InvocationTargetException
    {
        if(!(node instanceof PowerNode))
            throw  new InvalidParameterException();
        String f_prime = visit(((PowerNode) node).nodeA).toString();
        String g_prime = visit(((PowerNode) node).nodeB).toString();
        String f = ((PowerNode) node).nodeA.toString();
        String g = ((PowerNode) node).nodeB.toString();
        String res = String.format("%s * ((%s / %s) * %s + (%s * log(%s)))", node.toString(), g, f, f_prime, g_prime, f);
        return new Result(res);
    }

    private Result visitMathNode(Node node) throws InvalidParameterException, NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        if(!(node instanceof MathNode))
            throw new InvalidParameterException();
        String f_prime = visit(((MathNode) node).value).toString();
        String f = ((MathNode) node).value.toString();
        String operationPrime = DERIVATIVE_TABLE.getOrDefault(((MathNode) node).operation.toString(), "null");
        operationPrime = operationPrime.replace("x", String.format("%s", f));
        return new Result(String.format("%s * (%s)", operationPrime, f_prime));
    }

}
