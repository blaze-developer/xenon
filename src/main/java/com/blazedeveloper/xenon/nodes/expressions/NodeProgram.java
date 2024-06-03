package main.java.com.blazedeveloper.xenon.nodes.expressions;

import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatement;

import java.util.ArrayList;

public record NodeProgram(ArrayList<NodeStatement> statements) {}