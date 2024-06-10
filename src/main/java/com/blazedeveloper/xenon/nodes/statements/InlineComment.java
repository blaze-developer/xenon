package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public record InlineComment(String comment) implements NodeStatement {

    public String toString() {
        return "Comment: " + comment;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this);
    }
}
