package com.github.monkeyj;

import java.io.*;

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
            for(Token token = l.nextToken(); token.getType() != EOF; token = l.nextToken()) {
                System.out.println(token);
            }
        }
    }

    public static void main(String[] args) {
        start(new InputStreamReader(System.in), new OutputStreamWriter(System.out));
    }
}
