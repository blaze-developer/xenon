package main.java.com.blazedeveloper.xenon.nodes.expressions;

import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatement;

import java.util.ArrayList;

public class NodeProgram {
    public final ArrayList<NodeStatement> statements;

    public NodeProgram(ArrayList<NodeStatement> statements) {
        this.statements = statements;
    }
}
