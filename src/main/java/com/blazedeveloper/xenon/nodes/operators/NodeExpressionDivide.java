package main.java.com.blazedeveloper.xenon.nodes.operators;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpression;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;

public record NodeExpressionDivide(NodeExpression leftTerm, NodeExpression rightTerm) implements NodeExpression {


    // actually generating the calculation, and
    @Override
    public String accept(NodeExpressionVisitor visitor, String register) {
        return visitor.visit(this, register);
    }

    @Override
    public boolean isTerm() {
        return false;
    }
}
