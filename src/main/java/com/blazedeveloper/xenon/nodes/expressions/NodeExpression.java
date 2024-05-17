package main.java.com.blazedeveloper.xenon.nodes.expressions;

import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;

public interface NodeExpression {
    String accept(NodeExpressionVisitor visitor, String register);
}
