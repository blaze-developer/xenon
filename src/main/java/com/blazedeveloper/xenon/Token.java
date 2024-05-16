package main.java.com.blazedeveloper.xenon;

public class Token {
    public final Type type;
    public final String content;

    public Token(Type type, String content) {
        if (type == null) {
            throw new Error("TokenType cannot be null!");
        }

        this.type = type;
        this.content = content;
    }

    public Token(Type type) {
        this(type, null);
    }

    public boolean is(Type type) {
        return this.type == type;
    }

    public String toString() {
        return type + " - " + content;
    }

    public enum Type {
        EXIT,
        PRINT,
        PRINTLN,
        STRING_LITERAL,
        INT_LITERAL,
        ENDLINE,
        EQ,
        SET,
        IDENTIFIER
    }
}
