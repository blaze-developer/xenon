package main.java.com.blazedeveloper.xenon.nodes.expressions.visitor;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;

public interface NodeExpressionVisitor {
    public String visit(NodeExpressionIdent expr, String register);
    public String visit(NodeExpressionIntLiteral expr, String register);
}
