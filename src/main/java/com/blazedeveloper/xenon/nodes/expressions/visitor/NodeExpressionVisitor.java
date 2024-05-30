package main.java.com.blazedeveloper.xenon.nodes.expressions.visitor;

import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionAdd;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionDivide;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionMultiply;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionSubtract;

public interface NodeExpressionVisitor {
    String visit(NodeExpressionIdent expr, String register);
    String visit(NodeExpressionIntLiteral expr, String register);

    String visit(NodeExpressionAdd expr, String register);
    String visit(NodeExpressionSubtract expr, String register);

    String visit(NodeExpressionMultiply expr, String register);

    String visit(NodeExpressionDivide expr, String register);
}
