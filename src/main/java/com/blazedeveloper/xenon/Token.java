package main.java.com.blazedeveloper.xenon;

public class Token {
    public final Type type;
    public final String content;
    public final String subContent;

    public Token(Type type, String content, String subContent) {
        if (type == null) {
            throw new Error("TokenType cannot be null!");
        }

        this.type = type;
        this.content = content;
        this.subContent = subContent;
    }

    public Token(Type type, String content) {
        this(type, content, "");
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
        ASM,
        STRING_LITERAL,
        INT_LITERAL,
        ENDLINE,
        EQ,
        SET,
        DECLARE,
        IDENTIFIER,
        ADD,
        SUB,
        MUL,
        PARENOPEN,
        PARENCLOSE,
        SQRT,
        SQUARE,
        INLINE_COMMENT,
        DIV,
        DECLAREBOOL,
        TRUE,
        FALSE
    }
}
