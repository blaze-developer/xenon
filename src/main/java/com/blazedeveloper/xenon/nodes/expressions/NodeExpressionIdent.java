package main.java.com.blazedeveloper.xenon.nodes.expressions;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;

public class NodeExpressionIdent implements NodeExpression {
    public final Token identifier;

    public NodeExpressionIdent(Token identifier) {
        this.identifier = identifier;
    }

    public String toString() {
        return "Identifier of " + identifier.content;
    }

    @Override
    public String accept(NodeExpressionVisitor visitor, String register) {
        return visitor.visit(this, register);
    }
}
