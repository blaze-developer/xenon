package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitorGen;

public record NodeStatementSqrt(Token identifier) implements NodeStatement {

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }

}
