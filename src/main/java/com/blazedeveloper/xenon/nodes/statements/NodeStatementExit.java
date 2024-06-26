package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpression;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public record NodeStatementExit(NodeExpression expression) implements NodeStatement {

    @Override
    public String toString() {
        return "Exit Statement, Expression: " + expression;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }


}
