package main.java.com.blazedeveloper.xenon.nodes.statements.visitor;

import java.util.HashMap;

import main.java.com.blazedeveloper.xenon.Errorer;
import main.java.com.blazedeveloper.xenon.Token;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;
import main.java.com.blazedeveloper.xenon.nodes.expressions.visitor.NodeExpressionVisitor;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionAdd;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionDivide;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionMultiply;
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

        asm += reassignVarStmt.expr().accept(this, "rax");
        asm += setVariable(reassignVarStmt.identifier(), "rax");

        return asm;
    }

    @Override
    public String visit(NodeStatementDeclare nodeStatementDeclare) {
        StringBuilder asm = new StringBuilder();

        asm.append(push("0"));

        declareIdentifier(nodeStatementDeclare.identifier(), stackSize);

        return asm.toString();
    }

    @Override
    public String visit(NodeStatementIncrement nodeStatementIncrement) {
        String asm = "";
        Token identifier = nodeStatementIncrement.identifier();

        asm += getVariable(identifier, "rax"); // moves the variable into rax
        asm += "    inc rax\n"; // changes rax
        asm += setVariable(identifier, "rax"); // moves the value of rax into the variable

        return asm;
    }

    @Override
    public String visit(NodeStatementDecrement nodeStatementDecrement) {
        String asm = "";
        Token identifier = nodeStatementDecrement.identifier();

        asm += getVariable(identifier, "rax"); // moves the variable into rax
        asm += "    dec rax\n"; // changes rax
        asm += setVariable(identifier, "rax"); // moves the value of rax into the variable

        return asm;
    }

    @Override
    public String visit(NodeStatementAsm nodeStatementAsm, int i) {
        String inputasm = nodeStatementAsm.string_literal().content;

        if (inputasm.startsWith(" ")) {
            Errorer.usageErr("Inline ASM Statement Inproperly Formatted");
            return null;
        }

        return "    " + inputasm + "\n";
    }

    @Override
    public String visit(NodeStatementSqrt nodeStatementSqrt) {
        String asm = "";

        asm += "    fild qword " + variableLocation(nodeStatementSqrt.identifier()) + " ; converts 64 bit integer into float and pushes onto FPU stack\n";
        asm += "    fsqrt ; square roots the current top of the stack\n";
        asm += "    fistp qword " + variableLocation(nodeStatementSqrt.identifier()) + " ; converts back into an integer, and pops off the FPU stack and into the variable location on the memory stack.\n";

        return asm;
    }

    @Override
    public String visit(NodeStatementSquare nodeStatementSquare) {
        String asm = "";

        Token identifier = nodeStatementSquare.identifier();
        NodeExpressionIdent identifierExpr = new NodeExpressionIdent(nodeStatementSquare.identifier());

        asm += visit(
                new NodeStatementAssign(
                        identifier,
                        new NodeExpressionMultiply(
                                identifierExpr,
                                identifierExpr
                        )
                )
        );

        return asm;
    }

    @Override
    public String visit(NodeStatementSet setStmt) {
        String asm = "";

        asm += setStmt.expression().accept(this, "rax");
        asm += push("rax");

        declareIdentifier(setStmt.identifier(), stackSize);

        return asm;
    }

    @Override
    public String visit(NodeStatementExit exitStmt) {
        String asm = "";

        asm += mov("60", "rax");
        asm += exitStmt.expression().accept(this, "rdi");
        asm += "    syscall\n";

        return asm;
    }

    @Override
    public String visit(NodeExpressionIdent expr, String register) {
        return getVariable(expr.identifier(), register);
    }

    @Override
    public String visit(NodeExpressionIntLiteral expr, String register) {
        return mov(expr.int_literal().content, register);
    }


    // generating the calculation based on the tree, and pushing it to the register.
    @Override
    public String visit(NodeExpressionAdd expr, String register) {
        String asm = "";

        asm += expr.leftTerm().accept(this, "rax"); // accumulator
        asm += expr.rightTerm().accept(this, "rdi");
        asm += "    add rax, rdi\n";
        asm += mov("rax", register);

        return asm;
    }

    @Override
    public String visit(NodeExpressionSubtract expr, String register) {
        String asm = "";

        asm += expr.leftTerm().accept(this, "rax"); // accumulator
        asm += expr.rightTerm().accept(this, "rdi");
        asm += "    sub rax, rdi\n";
        asm += mov("rax", register);

        return asm;
    }

    @Override
    public String visit(NodeExpressionMultiply expr, String register) {
        String asm = "";

        asm += expr.leftTerm().accept(this, "rax"); // accumulator
        asm += expr.rightTerm().accept(this, "rdi");
        asm += "    imul rax, rdi\n";
        asm += mov("rax", register);

        return asm;
    }

    @Override
    public String visit(NodeExpressionDivide expr, String register) {
        String asm = "";

        asm += expr.leftTerm().accept(this, "rax"); // accumulator
        asm += expr.rightTerm().accept(this, "rdi");
        asm += "    idiv rdi\n";
        asm += mov("rax", register);

        return asm;
    }

    private String variableLocation(Token identifier) {

        if (variables.get(identifier.content) == null) {
            Errorer.syntaxErr("Undeclared identifier '" + identifier.content + "'!");
        }

        int offset = (stackSize - variables.get(identifier.content)) * 8;
        return "[rsp + " + offset + "]";
    }

    private String getVariable(Token identifier, String register) {
        return mov(variableLocation(identifier), register);
    }

    private String setVariable(Token identifier, String register) {
        return mov(register, variableLocation(identifier));
    }

    private String mov(String from, String to) {
        if (from.equals(to)) {
            return "";
        }

        return "    mov " + to + ", " + from + "\n";
    }

    private void declareIdentifier(Token identifier, int value) {
        if (variables.get(identifier.content) != null) {
            Errorer.usageErr("identifier '" + identifier.content + "' is already defined.");
        }

        variables.put(identifier.content, value);

    }

}
