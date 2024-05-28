package main.java.com.blazedeveloper.xenon.nodes.statements.visitor;

import java.util.HashMap;

import main.java.com.blazedeveloper.xenon.Errorer;
import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionAdd;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionSubtract;
import main.java.com.blazedeveloper.xenon.nodes.statements.*;

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

        asm += reassignVarStmt.expr.accept(this, "rax");
        asm += "    mov " + variableLocation(reassignVarStmt.identifier) + ", rax\n";

        return asm;
    }

    @Override
    public String visit(NodeStatementDeclare nodeStatementDeclare) {
        StringBuilder asm = new StringBuilder();

        asm.append(push("0"));

        declareIdentifier(nodeStatementDeclare.identifier, stackSize);

        return asm.toString();
    }

    @Override
    public String visit(NodeStatementSet setStmt) {
        String asm = "";

        asm += setStmt.expression.accept(this, "rax");
        asm += push("rax");

        declareIdentifier(setStmt.identifier, stackSize);

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
        return "    mov " + register + ", " + variableLocation(expr.identifier) + "\n";
    }

    @Override
    public String visit(NodeExpressionIntLiteral expr, String register) {
        return "    mov " + register + ", " + expr.int_literal.content + "\n";
    }


    // generating the calculation based on the tree, and pushing it to the register.
    @Override
    public String visit(NodeExpressionAdd expr, String register) {

        String asm = "";

        if (expr.leftTerm.isTerm() && expr.rightTerm.isTerm()) {
            asm += expr.leftTerm.accept(this, "rax"); // accumulator
            asm += expr.rightTerm.accept(this, "rdi");
            asm += "    add " + register + ", rdi\n";
            asm += "    mov " + register + ", rax\n";
        }

        return asm;
    }

    @Override
    public String visit(NodeExpressionSubtract expr, String register) {
        return null;
    }

    private String variableLocation(Token identifier) {

        if (variables.get(identifier.content) == null) {
            Errorer.syntaxErr("Undeclared identifier '" + identifier.content + "'!");
        }

        int offset = (stackSize - variables.get(identifier.content)) * 8;
        return "[rsp + " + offset + "]";
    }

    private void declareIdentifier(Token identifier, int value) {
        if (variables.get(identifier.content) != null) {
            Errorer.usageErr("identifier '" + identifier.content + "' is already defined.");
        }

        variables.put(identifier.content, value);

    }

}
