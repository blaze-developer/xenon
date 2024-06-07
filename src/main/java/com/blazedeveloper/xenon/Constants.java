package main.java.com.blazedeveloper.xenon;

import java.util.HashMap;

public class Constants {
    public static class Keywords {

        public static final HashMap<String, Token.Type> keywords = new HashMap<>();

        static {
            keywords.put("exit", Token.Type.EXIT);
            keywords.put("print", Token.Type.PRINT);
            keywords.put("printline", Token.Type.PRINTLN);
            keywords.put("init", Token.Type.SET);
            keywords.put("declare", Token.Type.DECLARE);
            keywords.put("asm", Token.Type.ASM);
            keywords.put("sqrt", Token.Type.SQRT);
        }

    }
}
