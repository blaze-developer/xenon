package main.java.com.blazedeveloper.xenon.nodes.expressions;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;

public record NodeExpressionIdent(Token identifier) implements NodeExpression {

    public String toString() {
        return "Identifier of " + identifier.content;
    }

    @Override
    public String accept(NodeExpressionVisitor visitor, String register) {
        return visitor.visit(this, register);
    }

    @Override
    public boolean isTerm() {
        return true;
    }
}
