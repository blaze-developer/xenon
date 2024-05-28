package main.java.com.blazedeveloper.xenon.nodes.operators;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpression;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;

public class NodeExpressionSubtract implements NodeExpression {

    public final NodeExpression leftTerm;
    public final NodeExpression rightTerm;

    public NodeExpressionSubtract(NodeExpression leftTerm, NodeExpression rightTerm) {
        this.leftTerm = leftTerm;
        this.rightTerm = rightTerm;
    }

    @Override
    public String accept(NodeExpressionVisitor visitor, String register) {
        return visitor.visit(this, register);
    }

    @Override
    public boolean isTerm() {
        return false;
    }


}
