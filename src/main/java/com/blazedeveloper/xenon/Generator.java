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
        String asm = "section .data\n    newline db 10\n";

        for (int i = 0; i < tree.statements.size(); i++) {
            asm += tree.statements.get(i).accept(visitorStringInit, i);
        }
        
        asm += "\n\nsection .text\n    global _start\n_start:\n";

        for (int i = 0; i < tree.statements.size(); i++) {
            asm += tree.statements.get(i).accept(visitorGen, i);
        }

        asm += "    mov rax, 60\n";
        asm += "    mov rdi, 0\n";
        asm += "    syscall";

        return asm;
    }

}
