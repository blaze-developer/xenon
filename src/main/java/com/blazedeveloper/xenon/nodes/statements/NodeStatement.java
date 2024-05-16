package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public interface NodeStatement {
    public abstract String toString();
    public String accept(NodeStatementVisitor visitor, int index);
}
