/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package main.java.com.blazedeveloper.xenon;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeProgram;
import main.java.com.blazedeveloper.xenon.nodes.statements.NodeStatement;

import java.io.IOException;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class App {

    public static void main(String[] args) {
        if (args.length < 1 || args.length > 2) {
            System.out.println("\nIncorrect Usage! Correct usage: compile [file] [debug-mode]");
            System.exit(1);
        }

        // Setting Debug Mode

        boolean debugMode = false;

        if (args.length == 2) {
            debugMode = args[1].equals("true");
        }

        String code = "";

        // Read File

        try {
            code = Files.readString(Paths.get(args[0]));
        } catch (IOException e) {
            System.out.println("Error reading file. Does it exist?");
            System.exit(1);
        } catch (OutOfMemoryError e) {
            System.out.println("File was too large!");
            System.exit(1);
        } catch (SecurityException e) {
            System.out.println("You dont have permission to access that file.");
            System.exit(1);
        }

        // Tokenize

        Lexer lexer = new Lexer(code);

        ArrayList<Token> tokens = lexer.tokenize();

        // Debug Logging
        if (debugMode) {
            System.out.println("Finished Tokenizing!\n\nTokens:");

            for (Token token : tokens) {
                System.out.println(token.type + ": " + token.content);
            }

            System.out.print("\n");

        }

        Parser parser = new Parser(tokens);

        NodeProgram programTree = parser.parse();

        if (debugMode) {
            System.out.println("Finished Parsing!\n\nStatements:");

            for (NodeStatement statement : programTree.statements) {
                System.out.println(statement);
            }

            System.out.print("\n");

        }

        Generator gen = new Generator(programTree);

        String asm = gen.generate();

        if (debugMode) {
            System.out.println("Finished creating assembly code! Writing...");
        }

        String outName = Paths.get(args[0]).getFileName().toString().replaceFirst("[.][^.]+$", "");

        // Write to output.asm
        try {
            FileWriter writer = new FileWriter(outName + ".asm");
            writer.write(asm);
            writer.close();
            System.out.println("Successfully compiled to asm.");
        } catch (IOException e) {
            System.out.println("Could not write to output asm file.");
            System.exit(ExitCode.FAILURE);
        }
    }
}