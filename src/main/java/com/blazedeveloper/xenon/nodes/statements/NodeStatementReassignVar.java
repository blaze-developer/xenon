package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public class NodeStatementReassignVar implements NodeStatement {

    public final Token identifier;
    public final Token intlit;

    public NodeStatementReassignVar(Token identifier, Token intlit) {
        this.identifier = identifier;
        this.intlit = intlit;
    }

    @Override
    public String toString() {
        return "Variable Reassignment: reassigning " + identifier.content + " to " + intlit.content;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }
    
}
