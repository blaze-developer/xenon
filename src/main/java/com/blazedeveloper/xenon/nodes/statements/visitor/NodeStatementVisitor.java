package main.java.com.blazedeveloper.xenon.nodes.statements.visitor;

import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementExit;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementPrint;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementPrintLine;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementReassignVar;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementSet;

public interface NodeStatementVisitor {
    public String visit(NodeStatementExit exitStmt);
    public String visit(NodeStatementPrint printStmt, int i);
    public String visit(NodeStatementPrintLine printLineStmt, int i);
    public String visit(NodeStatementSet setStmt);
    public String visit(NodeStatementReassignVar reassignVarStmt);
}
