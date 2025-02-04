package main.java.com.blazedeveloper.xenon.nodes.expressions;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;

public record NodeExpressionBoolLiteral(Token boolLiteral) implements NodeExpression {
    @Override
    public String accept(NodeExpressionVisitor visitor, String register) {
        return "";
    }

    @Override
    public boolean isTerm() {
        return false;
    }
}
