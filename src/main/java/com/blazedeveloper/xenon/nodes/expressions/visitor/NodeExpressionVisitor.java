package main.java.com.blazedeveloper.xenon.nodes.expressions.visitor;

import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionAdd;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionSubtract;

public interface NodeExpressionVisitor {
    String visit(NodeExpressionIdent expr, String register);
    String visit(NodeExpressionIntLiteral expr, String register);

    String visit(NodeExpressionAdd expr, String register);
    String visit(NodeExpressionSubtract expr, String register);
}
