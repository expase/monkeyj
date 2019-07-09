package com.github.monkeyj;

public class Token {
    private TokenType type;
    private String literal;

    public Token() {

    }
    public Token(TokenType type,String literal) {
        this.type = type;
        this.literal = literal;
    }
    public TokenType getType() {
        return type;
    }

    public void setType(TokenType type) {
        this.type = type;
    }

    public String getLiteral() {
        return literal;
    }

    public void setLiteral(String literal) {
        this.literal = literal;
    }

    public boolean equals(Object o) {
        if(!(o instanceof Token)) return false;
        Token token = (Token)o;

        return token.getType() == type && token.getLiteral().equals(literal);
    }

    public String toString() {
        return type.name()+"[" + literal + "]";
    }
}
