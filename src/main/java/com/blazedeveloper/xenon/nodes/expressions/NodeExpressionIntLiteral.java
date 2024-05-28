package main.java.com.blazedeveloper.xenon.nodes.expressions;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;

public class NodeExpressionIntLiteral implements NodeExpression {
    public final Token int_literal;

    public NodeExpressionIntLiteral(Token int_literal) {
        this.int_literal = int_literal;
    }

    public String toString() {
        return "Integer Literal of " + int_literal.content;
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