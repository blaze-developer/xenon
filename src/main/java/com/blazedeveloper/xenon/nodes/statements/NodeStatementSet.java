package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpression;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public record NodeStatementSet(Token identifier, NodeExpression expression) implements NodeStatement {

    @Override
    public String toString() {
        return "Set: setting variable " + identifier.content + " to " + expression;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }


}
