package main.java.com.blazedeveloper.xenon;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeProgram;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitorGen;
import main.java.com.blazedeveloper.xenon.nodes.statements.visitor.NodeStatementVisitorStringInit;

public class Generator {
    private final NodeProgram tree;
    private final NodeStatementVisitorStringInit visitorStringInit;
    private final NodeStatementVisitorGen visitorGen;

    public Generator(NodeProgram program) {
        this.tree = program;
        this.visitorStringInit = new NodeStatementVisitorStringInit();
        this.visitorGen = new NodeStatementVisitorGen();
    }

    public String generate() {
        StringBuilder asm = new StringBuilder("section .data\n    newline db 10\n");

        for (int i = 0; i < tree.statements.size(); i++) {
            asm.append(tree.statements.get(i).accept(visitorStringInit, i));
        }
        
        asm.append("\n\nsection .text\n    global _start\n_start:\n");

        for (int i = 0; i < tree.statements.size(); i++) {
            asm.append(tree.statements.get(i).accept(visitorGen, i));
        }

        asm.append("    mov rax, 60\n");
        asm.append("    mov rdi, 0\n");
        asm.append("    syscall");

        return asm.toString();
    }

}
