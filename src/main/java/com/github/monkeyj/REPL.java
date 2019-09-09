package com.github.monkeyj;

import com.github.monkeyj.ast.Program;
import com.github.monkeyj.ast.Statement;

import java.io.*;
import java.util.List;

import static com.github.monkeyj.TokenType.EOF;

public class REPL {
    private final static String PROMPT = ">>";
    public static void start(Reader in, Writer out) {
        BufferedReader scanner = new BufferedReader(in);
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
            checkParserErrors(parser);
            for(Statement statement : program.getStatements()) {
                System.out.println(statement.toString());
            }

        }
    }

    private static void checkParserErrors(Parser parser) {
        List<String> errors = parser.getErrors();
        if(errors.size() < 1) return;

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
