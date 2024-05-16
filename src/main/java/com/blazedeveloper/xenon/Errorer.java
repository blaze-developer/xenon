package main.java.com.blazedeveloper.xenon;

public class Errorer {
    public static void syntaxErr(String err) {
        System.err.println("Syntax Error: " + err);
        System.exit(ExitCode.Error.SYNTAX);
    }

    public static void usageErr(String err) {
        System.err.println("Usage Error: " + err);
        System.exit(ExitCode.Error.USAGE);
    }
}
