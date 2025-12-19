class Token {
    TokenType type;
    String lexeme;
    Object literal;
    int line;

    public Token(TokenType type, String lexeme, int line, Object literal)
    {
        this.type = type;
        this.lexeme = lexeme;
        this.literal = literal;
        this.line = line;
    }
}