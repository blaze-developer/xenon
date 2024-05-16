package main.java.com.blazedeveloper.xenon.nodes.statements;

import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitor;

public class NodeStatementPrintLine implements NodeStatement {
    public final Token string_literal;

    public NodeStatementPrintLine(Token string_literal) {
        this.string_literal = string_literal;
    }

    public String toString() {
        return "Printline Statement, String: " + string_literal.content;
    }

    @Override
    public String accept(NodeStatementVisitor visitor, int index) {
        return visitor.visit(this, index);
    }

}
