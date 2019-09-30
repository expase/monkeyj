package com.github.monkeyj;

import com.github.monkeyj.ast.Program;
import com.github.monkeyj.value.IObject;

import java.io.*;
import java.util.List;

public class REPL {
    private final static String PROMPT = ">>";
    public static void start(Reader in, Writer out) {
        BufferedReader scanner = new BufferedReader(in);
        Interpreter interceptor = new Interpreter();
        Context context = new Context(null);
        while(true) {
            System.out.print(PROMPT);
            String line = "";
            try {
                line = scanner.readLine();
            } catch (IOException e) {
                break;
            }

            Lexer l = new Lexer(line);
            Parser parser = new Parser(l);
            Program program = parser.parseProgram();
            if(parser.getErrors().size() > 0) {
                printParseErrors(parser);
                continue;
            }

            IObject value = program.accept(interceptor, context);
            System.out.println("eval=" + value);

        }
    }

    private static void printParseErrors(Parser parser) {
        List<String> errors = parser.getErrors();

        StringBuilder msg = new StringBuilder();
        msg.append(String.format("parser has %d errors\n", errors.size()));
        for(String error : errors) {
            msg.append(String.format("parser error: %s\n", error));
        }
        System.out.println(msg.toString());
    }
    public static void main(String[] args) {
        start(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
    }
}
