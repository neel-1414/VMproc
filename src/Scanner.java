
import java.util.HashMap;


public class Scanner {
    private  int line = 1;
    private  int start =0;
    private  int current =0;
    String source;
    HashMap<String , TokenType> identify = new HashMap<>();
    public Scanner()
    {
        identify.put("and",    TokenType.TOKEN_AND);
        identify.put("class",  TokenType.TOKEN_CLASS);
        identify.put("else",   TokenType.TOKEN_ELSE);
        identify.put("false",  TokenType.TOKEN_FALSE);
        identify.put("for",    TokenType.TOKEN_FOR);
        identify.put("fun",    TokenType.TOKEN_FUN);
        identify.put("if",     TokenType.TOKEN_IF);
        identify.put("nil",    TokenType.TOKEN_NIL);
        identify.put("or",     TokenType.TOKEN_OR);
        identify.put("print",  TokenType.TOKEN_PRINT);
        identify.put("return", TokenType.TOKEN_RETURN);
        identify.put("super",  TokenType.TOKEN_SUPER);
        identify.put("this",   TokenType.TOKEN_THIS);
        identify.put("true",   TokenType.TOKEN_TRUE);
        identify.put("var",    TokenType.TOKEN_VAR);
        identify.put("while",  TokenType.TOKEN_WHILE);

    }

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
        if(isAlpha(c)) return identifiers();
        if(isDigit(c)) return number();
        switch (c) {
            //single character tokennext
            case '('-> {return makeNullToken(TokenType.TOKEN_LEFT_PAREN);}
            case ')' -> {return makeNullToken(TokenType.TOKEN_RIGHT_PAREN);}
            case '}' -> {return makeNullToken(TokenType.TOKEN_RIGHT_BRACE);}
            case '{' -> {return makeNullToken(TokenType.TOKEN_LEFT_BRACE);}
            case ';' -> {return makeNullToken(TokenType.TOKEN_SEMICOLON);}
            case ',' -> {return makeNullToken(TokenType.TOKEN_COMMA);}
            case '-' -> {return makeNullToken(TokenType.TOKEN_MINUS);}
            case '+' -> {return makeNullToken(TokenType.TOKEN_PLUS);}
            case '*' -> {return makeNullToken(TokenType.TOKEN_STAR);}

            //double character tokens != and >=
            case '!' -> {
                return makeNullToken(match('=') ? TokenType.TOKEN_BANG_EQUAL : TokenType.TOKEN_BANG);
            }
            case '>' -> {
                return makeNullToken(match('=') ? TokenType.TOKEN_GREATER_EQUAL : TokenType.TOKEN_EQUAL);
            }
            case '=' -> {
                return makeNullToken(match('=') ? TokenType.TOKEN_EQUAL_EQUAL : TokenType.TOKEN_EQUAL);
            }
            case '<' -> {
                return makeNullToken(match('=') ? TokenType.TOKEN_LESS_EQUAL : TokenType.TOKEN_LESS);
            }

            //literals
            case '"' -> {return string();}
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

    private Token makeNullToken(TokenType type)
    {
        String lexeme = source.substring(start, current);
        return new Token(type, lexeme, line, null);
    }
    
    boolean match(char expected)
    {
        if(isAtEnd()) return false;
        if(source.charAt(current) == expected) return  true;

        current++;
        return false;
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
                case '\n':
                    line++;
                    advance();
                    break;
                case '%':
                    if(peeknext() == '%')
                    {
                        while(peek() !='\n' && !isAtEnd()) advance();
                    }
                    else{
                        return;
                    }
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
    private char peeknext()
    {
        if(isAtEnd()) return '\0';
        return source.charAt(current+1);
    }
    private Token errorToken(String message)
    {
        return new Token(TokenType.TOKEN_ERROR, message, line, null);
    }
    private Token string()
    {
        while(peek()!= '"' && !isAtEnd())
        {
            if(peek() == '\n') line++;
            advance();
        }
        if(isAtEnd()) return errorToken("Unterminated String");

        advance();
        return makeToken(TokenType.TOKEN_STRING, source.substring(start+1, current-1), source.substring(start, current));
        //thing to remember that we only want the value in literal and the lexem contains the string
    }
    //may remove later added rn to atleast store string literal
    private Token makeToken(TokenType type, Object literal, String lexeme)
    {
        return new Token(type, lexeme, line, literal);
    }
    private boolean isDigit(char c)
    {
        return c >= '0' && c<='9';
    }
    private Token number()
    {
        while(isDigit(peek())) advance();
        if(peek() == '.' && isDigit(peeknext()))
        {
            advance();
            while(isDigit(peek())) advance();
        }

        return makeToken(TokenType.TOKEN_NUMBER, Integer.valueOf(source.substring(start+1, current-1)) ,source.substring(start,current));
    }
    
    private boolean isAlpha(char c)
    {
        return (c >= 'a' && c<='z') || (c >= 'A' && c<= 'Z') || (c == '_');
    }
    private Token identifiers()
    {
        while(isAlpha(peek()) || isDigit(peek())) advance();

        return makeToken(identifierType(source.substring(start+1, current-1)), source.substring(start+1, current-1), source.substring(start,current));
    }
    private TokenType identifierType(String s)
    {
       if(identify.get(s) == null) return  TokenType.TOKEN_IDENTIFIER;
       return identify.get(s);
    }
}


