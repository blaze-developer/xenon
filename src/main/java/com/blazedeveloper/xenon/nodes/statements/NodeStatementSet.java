package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public class NodeStatementSet implements NodeStatement {
    public final Token identifier;
    public final Token intlit;

    public NodeStatementSet(Token identifier, Token intlit) {
        this.identifier = identifier;
        this.intlit = intlit;
    }

    @Override
    public String toString() {
        return "Set: setting variable " + identifier.content + " to " + intlit.content;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }
    
}
