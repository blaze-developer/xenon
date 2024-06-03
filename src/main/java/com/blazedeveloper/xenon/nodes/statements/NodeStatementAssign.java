package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpression;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public record NodeStatementAssign(Token identifier, NodeExpression expr) implements NodeStatement {

    @Override
    public String toString() {
        return "Variable Reassignment: reassigning " + identifier.content + " to " + expr;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }

}
