
public class Scanner {
    private  int line = 1;
   private  int start =0;
    private  int current =0;
    String source;
    public Scanner(String source)
    {
        this.source = source;
    }
    Token scanToken()
    {
        skipWhiteSpaces();
        this.start = this.current; 

        if(isAtEnd()) 
            {
                return new Token(TokenType.TOKEN_EOF, "", line, null);
            }

        char c = advance();
        switch (c) {
            //single character tokennext
            case '('-> {return makeToken(TokenType.TOKEN_LEFT_PAREN);}
            case ')' -> {return makeToken(TokenType.TOKEN_RIGHT_PAREN);}
            case '}' -> {return makeToken(TokenType.TOKEN_RIGHT_BRACE);}
            case '{' -> {return makeToken(TokenType.TOKEN_LEFT_BRACE);}
            case ';' -> {return makeToken(TokenType.TOKEN_SEMICOLON);}
            case ',' -> {return makeToken(TokenType.TOKEN_COMMA);}
            case '-' -> {return makeToken(TokenType.TOKEN_MINUS);}
            case '+' -> {return makeToken(TokenType.TOKEN_PLUS);}
            case '*' -> {return makeToken(TokenType.TOKEN_STAR);}

            //double character tokens != and >=
            case '!' -> {
                return makeToken(match('=') ? TokenType.TOKEN_BANG_EQUAL : TokenType.TOKEN_BANG);
            }
            case '>' -> {
                return makeToken(match('=') ? TokenType.TOKEN_GREATER_EQUAL : TokenType.TOKEN_EQUAL);
            }
            case '=' -> {
                return makeToken(match('=') ? TokenType.TOKEN_EQUAL_EQUAL : TokenType.TOKEN_EQUAL);
            }
            case '<' -> {
                return makeToken(match('=') ? TokenType.TOKEN_LESS_EQUAL : TokenType.TOKEN_LESS);
            }
        }

        return new Token(TokenType.TOKEN_ERROR, "Unexpected Character", line, null);
    }

    private  char advance()
    {
        current++;
        return source.charAt(current-1);
    }

   private  boolean isAtEnd()
    {
        return this.current == this.source.length();
    }

    private Token makeToken(TokenType type)
    {
        String lexeme = source.substring(start, current);
        return new Token(type, lexeme, line, null);
    }
    
    boolean match(char expected)
    {
        if(isAtEnd()) return false;
        if(source.charAt(current) == expected) return  true;

        current++;
        return true;
    }

    private void skipWhiteSpaces()
    {
       
        while(true)
        {
            char c = peek();
            switch(c){
                case ' ':
                case '\t':
                case '\r':
                    advance();
                    break;
                default:
                    return;
            }
        }
    }
    private char peek()
    {
        return source.charAt(current);
    }

    
}


