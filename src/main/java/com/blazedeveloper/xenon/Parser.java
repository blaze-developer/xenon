package main.java.com.blazedeveloper.xenon;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpression;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeProgram;
import main.java.com.blazedeveloper.xenon.nodes.statements.*;

import java.util.ArrayList;

public class Parser {
    private final ArrayList<Token> tokens;
    private int curIndex = 0;

    private final ArrayList<NodeStatement> statements = new ArrayList<>();

    private Token peek(int k) {
        int indexToPeek = curIndex + k;

        if (indexToPeek >= tokens.size())
            return null;

        return tokens.get(indexToPeek);
    }

    private Token peek() {
        return peek(0);
    }

    private Token consume(int k) {
        Token peeked = peek(k);

        if (peeked != null)
            curIndex += k + 1;
        
        return peeked;
    }

    private Token consume() {
        return consume(0);
    }

    private boolean exists(int k) {
        return peek(k) != null;
    }

    private boolean exists() {
        return exists(0);
    }

    private boolean isSemi(int k) {
        return exists(k) && peek(k).type == Token.Type.ENDLINE;
    }

    private NodeExpression parseExpr(Token expression) {

        return switch (expression.type) {
            case INT_LITERAL -> new NodeExpressionIntLiteral(expression);
            case IDENTIFIER -> new NodeExpressionIdent(expression);
            case PARENOPEN -> {
                consume();

                ArrayList<Token> expressionTokens = new ArrayList<>();

                while(exists() && peek().type != Token.Type.PARENCLOSE) {
                    expressionTokens.add(consume());
                }

                // peek() is currently parenthesis close
                consume(); // consume it

                System.out.println(expressionTokens.toString());

                yield null;
            }
            default -> {
                Errorer.syntaxErr("Invalid Expression: " + expression);
                yield null;
            }
        };
    }

    private Token parseString(Token token) {
        if (token.type == Token.Type.STRING_LITERAL) {
            return token;
        } else {
            Errorer.syntaxErr("Expected STRING_LITERAL token, got " + token);
            return null;
        }
    }

    // Precondition: current token is EXIT token.
    private NodeStatementExit parseExit() {
        NodeStatementExit stmt;

        if (isSemi(2)) { // if ending in semicolon

            stmt = new NodeStatementExit(parseExpr(consume(1)));

            consume(); // consume semicolon

            return stmt;
        } else {
            Errorer.syntaxErr("Expected ';'");
        }

        return null; // just to silence warnings
    }

    // Precondition: current token is PRINT token.
    private NodeStatementPrint parsePrint() {
        if (isSemi(2)) {
            NodeStatementPrint stmt;

            stmt = new NodeStatementPrint(parseString(consume(1)));

            consume();

            return stmt;
        } else {
            Errorer.syntaxErr("Expected ';'");
        }

        return null;
    }

    // Precondition: current token is PRINTLN token.
    private NodeStatementPrintLine parsePrintLn() {
        if (isSemi(2)) {
            NodeStatementPrintLine stmt;

            stmt = new NodeStatementPrintLine(parseString(consume(1)));

            consume();

            return stmt;
        } else {
            Errorer.syntaxErr("Expected ';'");
        }

        return null;
    }

    private NodeStatementDeclare parseDeclare() {
        if (exists(1) && peek(1).type == Token.Type.IDENTIFIER) {
            if (isSemi(2)) {
                // is valid
                NodeStatementDeclare statementDecl = new NodeStatementDeclare(peek(1));
                consume(2);

                return statementDecl;

            } else {
                Errorer.syntaxErr("Expected ';'");
            }
        } else {
            Errorer.syntaxErr("Expected identifier.");
        }

        return null;
    }

    private NodeStatementSet parseSet() {
        if (exists(1) && peek(1).type == Token.Type.IDENTIFIER) {
            if (exists(2) && peek(2).type == Token.Type.EQ) {
                if (isSemi(4)) {
                    // is valid
                    NodeStatementSet statementSet = new NodeStatementSet(peek(1), parseExpr(peek(3)));
                    consume(4);

                    return statementSet;

                } else {
                    Errorer.syntaxErr("Expected ';'");
                }
            } else {
                Errorer.syntaxErr("Expected '='");
            }
        } else {
            Errorer.syntaxErr("Expected identifier.");
        }

        return null;
    }

    public NodeStatementAssign parseAssignment() {
        if (exists(1) && peek(1).type == Token.Type.EQ) {

            NodeStatementAssign node = new NodeStatementAssign(consume(), parseExpr(peek()));
            // parse expression should have consumed all stuff and made a expression :3

            if (isSemi(0)) {
                consume();

                return node;
            } else {
                Errorer.syntaxErr("Expected ';'");
            }

            return null;

        } else {
            Errorer.syntaxErr("Expected '='");
        }
        return null;
    }

    public Parser(ArrayList<Token> tokens) {
        this.tokens = tokens;
    }

    public NodeProgram parse() {

        while (exists()) {

            switch(peek().type) {
    
                case EXIT:
                    statements.add(parseExit());
                    break;

                case PRINT:
                    statements.add(parsePrint());
                    break;

                case PRINTLN:
                    statements.add(parsePrintLn());
                    break;

                case SET:
                    statements.add(parseSet());
                    break;

                case IDENTIFIER:
                    statements.add(parseAssignment());
                    break;

                case DECLARE:
                    statements.add(parseDeclare());
                    break;

                default:
                    Errorer.syntaxErr("Invalid Statement at token " + peek().type);
            }
        }

        return new NodeProgram(statements);
    }
}
