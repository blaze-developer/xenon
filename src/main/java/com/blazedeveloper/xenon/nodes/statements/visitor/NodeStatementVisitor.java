package main.java.com.blazedeveloper.xenon.nodes.statements.visitor;

import main.java.com.blazedeveloper.xenon.nodes.statements.*;

public interface NodeStatementVisitor {
    String visit(NodeStatementExit exitStmt);
    String visit(NodeStatementPrint printStmt, int i);
    String visit(NodeStatementPrintLine printLineStmt, int i);
    String visit(NodeStatementSet setStmt);
    String visit(NodeStatementAssign reassignVarStmt);
    String visit(NodeStatementDeclare nodeStatementDeclare);
    String visit(NodeStatementIncrement nodeStatementIncrement);
    String visit(NodeStatementDecrement nodeStatementDecrement);

    String visit(NodeStatementAsm nodeStatementAsm, int i);

    String visit(NodeStatementSqrt nodeStatementSqrt);
}
