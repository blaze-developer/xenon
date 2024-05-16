package main.java.com.blazedeveloper.xenon.nodes.statements.visitor;

import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementExit;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementPrint;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementPrintLine;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementReassignVar;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementSet;

public class NodeStatementVisitorStringInit implements NodeStatementVisitor {

    @Override
    public String visit(NodeStatementExit exitStmt) {
        return "";
    }

    @Override
    public String visit(NodeStatementPrint printStmt, int index) {
        String asm = "";

        asm += "    string" + index + " db \"" + printStmt.string_literal.content + "\"\n";
        asm += "    stringlen" + index + " equ $-string" + index + "\n";

        return asm;
    }

    @Override
    public String visit(NodeStatementPrintLine printLineStmt, int index) {
        String asm = "";

        asm += "    string" + index + " db \"" + printLineStmt.string_literal.content + "\"\n";
        asm += "    stringlen" + index + " equ $-string" + index + "\n";

        return asm;
    }

    @Override
    public String visit(NodeStatementSet setStmt) {
        return "";
    }

    @Override
    public String visit(NodeStatementReassignVar reassignVarStmt) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'visit'");
    }
    
}
