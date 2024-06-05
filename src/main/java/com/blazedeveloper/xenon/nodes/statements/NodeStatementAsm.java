package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public record NodeStatementAsm(Token string_literal) implements NodeStatement {

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this, index);
    }
}
