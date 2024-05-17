package main.java.com.blazedeveloper.xenon.nodes.expressions.visitor;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;

public interface NodeExpressionVisitor {
    String visit(NodeExpressionIdent expr, String register);
    String visit(NodeExpressionIntLiteral expr, String register);
}
