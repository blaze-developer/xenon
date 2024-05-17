package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpression;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

import java.beans.Expression;

public class NodeStatementAssign implements NodeStatement {

    public final Token identifier;
    public final NodeExpression expr;

    public NodeStatementAssign(Token identifier, NodeExpression expr) {
        this.identifier = identifier;
        this.expr = expr;
    }

    @Override
    public String toString() {
        return "Variable Reassignment: reassigning " + identifier.content + " to " + expr;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }
    
}
