package main.java.com.blazedeveloper.xenon.nodes.statements.visitor;

import java.util.HashMap;

import main.java.com.blazedeveloper.xenon.Errorer;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementExit;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementPrint;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementPrintLine;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementAssign;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatementSet;

public class NodeStatementVisitorGen implements NodeStatementVisitor, NodeExpressionVisitor {

    private int stackSize = 0;

    private final HashMap<String, Integer> variables = new HashMap<>();

    private String push(String register) {
        stackSize++;
        return "    push " + register + "\n";
    }

    private String pop(String register) {
        stackSize--;
        return "    pop " + register + "\n";
    }

    @Override
    public String visit(NodeStatementPrint printStmt, int index) {
        String asm = "";

        asm += "    mov rax, 1\n";
        asm += "    mov rdi, 1\n";
        asm += "    mov rsi, string" + index + "\n";
        asm += "    mov rdx, stringlen" + index + "\n";
        asm += "    syscall\n";

        return asm;
    }

    @Override
    public String visit(NodeStatementPrintLine printLineStmt, int index) {
        String asm = "";

        asm += "    mov rax, 1\n";
        asm += "    mov rdi, 1\n";
        asm += "    mov rsi, string" + index + "\n";
        asm += "    mov rdx, stringlen" + index + "\n";
        asm += "    syscall\n";
        asm += "    mov rax, 1\n";
        asm += "    mov rdi, 1\n";
        asm += "    mov rsi, newline\n";
        asm += "    mov rdx, 1\n";
        asm += "    syscall\n";

        return asm;
    }

    @Override
    public String visit(NodeStatementAssign reassignVarStmt) {
        String asm = "";

        asm += "    ";

        return asm;
    }

    @Override
    public String visit(NodeStatementSet setStmt) {
        String asm = "";

        asm += setStmt.expression.accept(this, "rax");
        asm += "    push rax\n";

        if (variables.get(setStmt.identifier.content) != null) {
            Errorer.usageErr("variable '" + setStmt.identifier.content + "' is already defined.");
        }

        variables.put(setStmt.identifier.content, stackSize);

        return asm;
    }

    @Override
    public String visit(NodeStatementExit exitStmt) {
        String asm = "";

        asm += "    mov rax, 60\n";
        asm += exitStmt.expression.accept(this, "rdi");
        asm += "    syscall\n";

        return asm;
    }

    @Override
    public String visit(NodeExpressionIdent expr, String register) {
        
        if (variables.get(expr.identifier.content) == null) {
            Errorer.syntaxErr("Undeclared identifier '" + expr.identifier.content + "'!");
        }

        int varPos = variables.get(expr.identifier.content);

        int offset = (stackSize - varPos) * 8;

        return "    mov " + register + ", [rsp + " + offset + "]\n"; 
    }

    @Override
    public String visit(NodeExpressionIntLiteral expr, String register) {
        return "    mov " + register + ", " + expr.int_literal.content + "\n";
    }

}
