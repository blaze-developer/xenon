package main.java.com.blazedeveloper.xenon.nodes.statements.visitor;

import main.java.com.blazedeveloper.xenon.nodes.statements.*;

public class NodeStatementVisitorStringInit implements NodeStatementVisitor {

    @Override
    public String visit(NodeStatementExit exitStmt) {
        return "";
    }

    @Override
    public String visit(NodeStatementPrint printStmt, int index) {
        String asm = "";

        asm += "    string" + index + " db \"" + printStmt.string_literal().content + "\"\n";
        asm += "    stringlen" + index + " equ $-string" + index + "\n";

        return asm;
    }

    @Override
    public String visit(NodeStatementPrintLine printLineStmt, int index) {
        String asm = "";

        asm += "    string" + index + " db \"" + printLineStmt.string_literal().content + "\"\n";
        asm += "    stringlen" + index + " equ $-string" + index + "\n";

        return asm;
    }

    @Override
    public String visit(NodeStatementSet setStmt) {
        return "";
    }

    @Override
    public String visit(NodeStatementAssign reassignVarStmt) {
        return "";
    }

    @Override
    public String visit(NodeStatementDeclare nodeStatementDeclare) {
        return "";
    }

    @Override
    public String visit(NodeStatementIncrement nodeStatementIncrement) {
        return "";
    }

    @Override
    public String visit(NodeStatementDecrement nodeStatementDecrement) {
        return "";
    }

    @Override
    public String visit(NodeStatementAsm nodeStatementAsm, int i) {
        return "";
    }

    @Override
    public String visit(NodeStatementSqrt nodeStatementSqrt) {
        return "";
    }

    @Override
    public String visit(NodeStatementSquare nodeStatementSquare) {
        return "";
    }

}
