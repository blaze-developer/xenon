package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public class NodeStatementDeclare implements NodeStatement {
    public final Token identifier;

    public NodeStatementDeclare(Token identifier) {
        this.identifier = identifier;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }

}
