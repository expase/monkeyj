package com.github.monkeyj;


import static com.github.monkeyj.TokenType.*;


public class Lexer {
    private String input;
    private int position; //current position in input(points to current char)
    private int readPosition; //current reading position in input(after current char)
    private char ch; //current char under examination

    public Lexer(String input) {
        this.input = input;
        readChar();
    }

    private void readChar() {
        if (readPosition >= input.length()) {
            ch = 0;
        } else {
            ch = input.charAt(readPosition);
        }

        position = readPosition;
        readPosition += 1;
    }

    private char peekChar() {
        if(readPosition >= input.length()) {
            return 0;
        } else {
            return input.charAt(readPosition);
        }
    }

    public Token nextToken() {
        Token token = new Token();

        skipWhitespace();

        switch (ch) {
            case '=': {
                if (peekChar() == '=') {
                    char c = ch;
                    readChar();
                    token = newToken(EQ, "==");
                } else {
                    token = newToken(ASSIGN, str(ch));
                }

            } break;
            case '!': {
                if (peekChar() == '=') {
                    char c = ch;
                    readChar();
                    token = newToken(NOT_EQ, "!=");
                } else {
                    token = newToken(BANG, str(ch));
                }
            } break;

            case '/':
                token = newToken(SLASH, str(ch)); break;
            case '*':
                token = newToken(ASTERISK, str(ch)); break;
            case '<':
                token = newToken(LT, str(ch)); break;
            case '>':
                token = newToken(GT, str(ch)); break;
            case ';': token = newToken(SEMICOLON, str(ch)); break;
            case '-': token = newToken(MINUS, str(ch)); break;
            case '(': token = newToken(LPAREN, str(ch)); break;
            case ')': token = newToken(RPAREN, str(ch)); break;
            case ',': token = newToken(COMMA, str(ch)); break;
            case '+': token = newToken(PLUS, str(ch)); break;
            case '{': token = newToken(LBRACE, str(ch)); break;
            case '}': token = newToken(RBRACE, str(ch)); break;
            case '"': token = newToken(STRING, readString());break;
            case '[': token = newToken(LBRACKET, str(ch));break;
            case ']': token = newToken(RBRACKET, str(ch));break;
            case '&': {
                if (peekChar() == '&') {
                    readChar();
                    token = newToken(AND, "&&");
                }
            } break;
            case '|': {
                if (peekChar() == '|') {
                    readChar();
                    token = newToken(OR, "||");
                }
            } break;
            case 0: {
                token.setLiteral("");
                token.setType(EOF);
            } break;

            default:
                if (isLetter(ch)) {
                    token.setLiteral(readIdentifier());
                    token.setType(TokenType.lookupIdent(token.getLiteral()));
                    return token;
                } else if (isDigit(ch)){
                    token.setType(INT);
                    token.setLiteral(readNumber());
                    return token;
                } else {
                    token = newToken(ILLEGAL, String.valueOf(ch));
                } break;
        }

        readChar();

        return token;
    }

    private String readIdentifier() {
        StringBuilder buf = new StringBuilder();
        while(isLetter(ch)) {
            buf.append(ch);
            readChar();
        }
        return buf.toString();
    }

    private String readString() {
        StringBuilder buf  = new StringBuilder();
        while(true) {
            readChar();
            if(ch == '"') break;
            buf.append(ch);
        }
        return buf.toString();
    }

    private String readNumber() {
        StringBuilder buf = new StringBuilder();
        while(isDigit(ch)) {
            buf.append(ch);
            readChar();
        }
        return buf.toString();
    }

    private void skipWhitespace() {
        while(ch == ' ' || ch == '\t' || ch == '\n' || ch == '\r') {
            readChar();
        }
    }

    private Token newToken(TokenType type, String literal) {
        return new Token(type, literal);
    }

    private static boolean isDigit(char c) {
        return '0' <= c && c <= '9';
    }

    private static boolean isLetter(char c) {
        return ('a' <= c && c <= 'z')
                || ('A' <=c && c <= 'Z')
                || c == '_';
    }

    private static String str(char c) {
        return String.valueOf(c);
    }
}
