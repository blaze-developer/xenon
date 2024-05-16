package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public class NodeStatementPrint implements NodeStatement {
    public final Token string_literal;

    public NodeStatementPrint(Token string_literal) {
        this.string_literal = string_literal;
    }
    
    @Override
    public String toString() {
        return "Print, String: " + string_literal.content;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this, index);
    }
}
