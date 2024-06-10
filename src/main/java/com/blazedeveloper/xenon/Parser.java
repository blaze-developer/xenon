package main.java.com.blazedeveloper.xenon;

import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpression;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIdent;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeExpressionIntLiteral;
import main.java.com.blazedeveloper.xenon.nodes.expressions.NodeProgram;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionAdd;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionDivide;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionMultiply;
import main.java.com.blazedeveloper.xenon.nodes.operators.NodeExpressionSubtract;
import main.java.com.blazedeveloper.xenon.nodes.statements.*;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.function.Supplier;

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

    private NodeExpression parseExpr(Token expression, boolean isMainLoop) {

        return switch (expression.type) {
            case INT_LITERAL -> {
                if (isMainLoop)
                    consume();
                yield new NodeExpressionIntLiteral(expression);
            }
            case IDENTIFIER -> {
                if (isMainLoop)
                    consume();
                yield new NodeExpressionIdent(expression);
            }
            case PARENOPEN -> {

                System.out.println("parenOpen");

                consume();

                ArrayList<Token> expressionTokens = new ArrayList<>();

                while(exists() && peek().type != Token.Type.PARENCLOSE) {
                    expressionTokens.add(consume());
                }

                // peek() is currently parenthesis close
                consume(); // consume it

                yield parseOperation(expressionTokens);
            }
            default -> {
                Errorer.syntaxErr("Invalid Expression: " + expression);
                yield null;
            }
        };
    }

    private NodeExpression parseOperation(ArrayList<Token> tokens) {
        if (tokens.size() == 0) {
            Errorer.syntaxErr("Empty math expression!");
        }

        if (tokens.size() == 1) {
            return parseExpr(tokens.get(0), false);
        }

        NodeExpression parsedExpression;

        for (int i = tokens.size() - 2; i >= 0; i--) { // iterate from right to left
            switch(tokens.get(i).type) {
                case ADD -> {
                    Token right = tokens.remove(i + 1);
                    tokens.remove(i); // remove the operator
                    Token left = tokens.get(i - 1);
                    return new NodeExpressionAdd(parseOperation(tokens), parseExpr(right, false));
                }
                case SUB -> {
                    Token right = tokens.remove(i + 1);
                    tokens.remove(i); // remove the operator
                    Token left = tokens.get(i - 1);
                    return new NodeExpressionSubtract(parseOperation(tokens), parseExpr(right, false));
                }
                case MUL -> {
                    Token right = tokens.remove(i + 1);
                    tokens.remove(i); // remove operator
                    Token left = tokens.get(i - 1);
                    return new NodeExpressionMultiply(parseOperation(tokens), parseExpr(right, false));
                }
                case DIV -> {
                    Token right = tokens.remove(i + 1);
                    tokens.remove(i); // remove operator
                    Token left = tokens.get(i - 1);
                    return new NodeExpressionDivide(parseOperation(tokens), parseExpr(right, false));
                }
                default -> Errorer.syntaxErr("Unknown Operator '" + tokens.get(i).toString() + "'!");
            }

            System.out.println(tokens);
        }

        return null;
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

            stmt = new NodeStatementExit(parseExpr(peek(1), true));

            consume(1);

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

    private NodeStatementSqrt parseSqrt() {
        if (exists(1) && peek(1).type == Token.Type.IDENTIFIER) {
            if (isSemi(2)) {
                // is valid
                NodeStatementSqrt statementSqrt = new NodeStatementSqrt(peek(1));
                consume(2);

                return statementSqrt;

            } else {
                Errorer.syntaxErr("Expected ';'");
            }
        } else {
            Errorer.syntaxErr("Expected identifier.");
        }

        return null;
    }

    private NodeStatementSquare parseSquare() {
        if (exists(1) && peek(1).type == Token.Type.IDENTIFIER) {
            if (isSemi(2)) {
                // is valid
                NodeStatementSquare statementSquare = new NodeStatementSquare(peek(1));
                consume(2);

                return statementSquare;

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
                // is valid
                Token identifier = consume(1);
                consume(); // consume the equals
                NodeExpression expr = parseExpr(peek(), true);

                NodeStatementSet statementSet = new NodeStatementSet(identifier, expr);

                if (isSemi(0)) {
                    consume(); // consume the semicolon
                    return statementSet;
                } else {
                    Errorer.syntaxErr("Expected ';'");
                    return null;
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

            Token ident = consume();
            consume(); // consume eq
            NodeExpression expr = parseExpr(peek(), true);

            NodeStatementAssign node = new NodeStatementAssign(ident, expr);
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

            System.out.println("Parsing Token " + peek().toString());

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

                    if (exists(1) && peek(1).is(Token.Type.ADD) && exists(2) && peek(2).is(Token.Type.ADD) && exists(3) && peek(3).is(Token.Type.ENDLINE)) {
                        statements.add(new NodeStatementIncrement(peek()));
                        consume(3);
                        break;
                    }

                    if (exists(1) && peek(1).is(Token.Type.SUB) && exists(2) && peek(2).is(Token.Type.SUB) && exists(3) && peek(3).is(Token.Type.ENDLINE)) {
                        statements.add(new NodeStatementDecrement(peek()));
                        consume(3);
                        break;
                    }

                    statements.add(parseAssignment());
                    break;

                case DECLARE:
                    statements.add(parseDeclare());
                    break;

                case SQRT:
                    statements.add(parseSqrt());
                    break;

                case ASM:
                    statements.add(parseAsm());
                    break;

                case SQUARE:
                    statements.add(parseSquare());
                    break;

                default:
                    Errorer.syntaxErr("Invalid Statement at token " + peek().type);
            }
        }

        return new NodeProgram(statements);
    }

    private NodeStatement parseAsm() {
        if (isSemi(2)) {
            NodeStatementAsm stmt = new NodeStatementAsm(parseString(consume(1)));

            consume();

            return stmt;
        } else {
            Errorer.syntaxErr("Expected ';'");
        }

        return null;
    }
}
