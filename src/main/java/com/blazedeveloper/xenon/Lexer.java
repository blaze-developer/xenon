
package main.java.com.blazedeveloper.xenon;

import java.util.ArrayList;

public class Lexer {

    private final String source;
    private String buffer;
    private int curIndex = 0;

    public Lexer(String source) {
        this.source = source;      
    }

    private Character peek(int k) {
        // If end of file, return null
        if (curIndex + k >= source.length())
            return null;

        // Else, Return the peeked character
        return source.charAt(curIndex + k);
    }
    
    private Character peek() {
        // If end of file, return null
        return peek(0);
    }

    private boolean peekExists(int k) {
        return peek(k) != null;
    }

    private boolean peekExists() {
        return peekExists(0);
    }

    private Character consume(int k) {
        Character peeked = peek(k);

        if (peeked == null) {
            return peeked;
        }

        curIndex += k + 1;
        return peeked;

    }

    private Character consume() {
        return consume(0);
    }

    private void clearBuffer() {
        buffer = "";
    }

    public ArrayList<Token> tokenize() {
        ArrayList<Token> tokens = new ArrayList<Token>();
        
        clearBuffer();


        while (peekExists()) {

            if (Character.isLetter(peek())) { // keyword state

                while (peekExists() && Character.isLetterOrDigit(peek())) {
                    buffer += consume();
                }

                switch (buffer) {
                    case Constants.Keywords.EXIT:
                        tokens.add(new Token(Token.Type.EXIT));
                        break;

                    case Constants.Keywords.PRINT:
                        tokens.add(new Token(Token.Type.PRINT));
                        break;

                    case Constants.Keywords.PRINTLN:
                        tokens.add(new Token(Token.Type.PRINTLN));
                        break;

                    case Constants.Keywords.SET:
                        tokens.add(new Token(Token.Type.SET));
                        break;

                    case Constants.Keywords.DECLARE:
                        tokens.add(new Token(Token.Type.DECLARE));
                        break;

                    default:
                        tokens.add(new Token(Token.Type.IDENTIFIER, buffer));
                }

                clearBuffer();

            } else if (Character.isDigit(peek())) { // integer literal state

                while (peekExists() && Character.isDigit(peek())) {
                    buffer += consume();
                }

                tokens.add(new Token(Token.Type.INT_LITERAL, buffer));

                clearBuffer();

            } else if (peek() == '\"') { // string literal state

                consume(); // consume the opening quote

                while (peekExists() && peek() != '\"') {

                    if (peek() == null) {
                        System.err.println("Error! String left unterminated at character " + curIndex + "!"); // TODO: Make seperate error handler
                        System.exit(ExitCode.FAILURE);
                    }

                    buffer += consume();
                }

                consume(); // consume the ending quote

                tokens.add(new Token(Token.Type.STRING_LITERAL, buffer));

                clearBuffer();

            } else if (peek() == ';') {
                tokens.add(new Token(Token.Type.ENDLINE));
                consume();
            } else if (peek() == '/' && peekExists(1) && peek(1) == '/') {
                consume(1);

                while (peekExists() && peek() != '\n') {
                    consume();
                }

            } else if (peek() == '/' && peekExists(1) && peek(1) == '*') { // has a /*

                consume(1); // consume the /*

                // boolean isEnd = ();

                while (!(peekExists(-2) && peek(-2) == '*' && peekExists(-1) && peek(-1) == '/')) {
                    consume();
                }

            } else if (Character.isWhitespace(peek())) {
                consume();
            } else {
                switch(peek()) {
                    case '=':
                        tokens.add(new Token(Token.Type.EQ));
                        break;

                    case '+':
                        tokens.add(new Token(Token.Type.ADD));
                        break;

                    case '-':
                        tokens.add(new Token(Token.Type.SUB));
                        break;

                    case '*':
                        tokens.add(new Token(Token.Type.MUL));
                        break;

                    case '/':
                        tokens.add(new Token(Token.Type.DIV));
                        break;

                    default:
                        Errorer.syntaxErr("Unknown Token " + peek());
                        break;
                }

                consume();
            }

        }

        return tokens;
    }
}