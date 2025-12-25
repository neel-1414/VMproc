import java.lang.classfile.Opcode;

import Chunk.OpCode;

public class Compiler {
    private Parser parser = new Parser();
    Scanner sc;
    Chunk chunk;
    public boolean compile(String source, Chunk chunk)
    {
        this.chunk = chunk;
        this.sc = new Scanner(source);
        advance();
        expression();
        consume(TokenType.TOKEN_EOF, "Expect end of expression");
        return !parser.hadError;
    }
    private void advance()
    {
        
        parser.previous = parser.current;
        while (true) { 
            parser.current = sc.scanToken();
            if(parser.current.type != TokenType.TOKEN_ERROR) break;

            error(parser.current.lexeme); // .start ?= in lexeme or literal
        }

    }
    private void error(String message)
    {
        errorAt(parser.previous, message);
    }
    private void errorAt(Token token, String message)
    {
        if(parser.panicMode) return;
        parser.panicMode = true;
        System.err.println("Error at : "+token.line);
        if(token.type == TokenType.TOKEN_EOF )
        {
            System.err.println(" at the end");
        }
        else if(token.type == TokenType.TOKEN_ERROR)
        {
            System.err.println();
        }
        else{
            System.err.println("at "+token.line+token.lexeme);
        }

        System.err.println(message);
        parser.hadError = true;
    }
    private void consume(TokenType type, String message)
    {
        if(parser.current.type == type){advance(); return;}
        error(message);
    }

    private void emitOp(Chunk.OpCode b)
    {
        chunk.writeCode(b, parser.previous.line);
    }
    private void emitByte(byte b)
    {
        chunk.writeByte(b, parser.previous.line);
    }

    private void emitBytes(byte byte1, byte byte2)
    {
        emitByte(byte1);
        emitByte(byte2);
    }
    private void emitReturn()
    {
        emitOp(Chunk.OpCode.OP_RETURN);
    }
}
class Parser {
    Token current;
    Token previous;
    boolean hadError = false;
    boolean panicMode = false;
}

//continue 305
