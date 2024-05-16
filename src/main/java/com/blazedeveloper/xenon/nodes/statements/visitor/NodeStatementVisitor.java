package main.java.com.blazedeveloper.xenon.nodes.statements.visitor;

import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementExit;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementPrint;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementPrintLine;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementReassignVar;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementSet;

public interface NodeStatementVisitor {
    String visit(NodeStatementExit exitStmt);
    String visit(NodeStatementPrint printStmt, int i);
    String visit(NodeStatementPrintLine printLineStmt, int i);
    String visit(NodeStatementSet setStmt);
    String visit(NodeStatementReassignVar reassignVarStmt);
}
