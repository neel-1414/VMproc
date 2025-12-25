
import java.util.Stack;


public class VM {
    private Chunk chunk;
    private  int ip;
    static final boolean DEBUG_RACE_EXECUTION = true;
    Stack<Object> stack = new Stack<>(); 

    public InterpretResult interpret(String source)
    {
        Chunk chunk = new Chunk();
        Compiler c = new Compiler();
        if(!c.compile(source, chunk))
        {
            return InterpretResult.INTERPRET_COMPILE_ERROR;
        }
        this.chunk = chunk;
        this.ip = 0;
        InterpretResult result = run();
        return result;
    }
    void resetStack() // top empty the stack completely
    {
        stack.clear();
    }
    //most important this is running my bytecode
    InterpretResult run()
    {
        Debug d = new Debug();
        while(true)
        {
           
            if(DEBUG_RACE_EXECUTION) // just for debugging on every opcode
            {
                System.out.println("        ");
                for(int i =stack.size()-1; i>=0; i--)
                {
                    System.out.print("     ");
                    System.out.print("[");
                    System.out.printf("%.2f",stack.get(i));
                    System.out.println("]");
                }
                System.out.println();
                d.disassembleInstructions(this.chunk, this.ip);
            }
            byte instructions = READ_BYTE();
            Chunk.OpCode opCode = Chunk.OpCode.values()[Byte.toUnsignedInt(instructions)];
            switch (opCode) {
                case OP_RETURN -> {
                    System.out.printf("%.4f",stack.pop());
                    System.out.println();
                    return InterpretResult.INTERPRET_OK;
                }
                case OP_CONSTANT -> {
                    Object constant = READ_CONSTANT();
                    stack.push((double)constant);
                }

                case OP_ADD -> {
                    BINARY_OP('+');
                }

                case OP_SUBTRACT -> {
                    BINARY_OP('-');
                }

                case OP_MULTIPLY -> {
                    BINARY_OP('*');
                }

                case OP_DIVIDE -> {
                    BINARY_OP('/');
                }

                case OP_NEGATE-> {
                    if(!stack.isEmpty())
                    {
                        Object value  = stack.pop();
                        stack.push(-((double)value));

                    }
                }

                default -> throw new AssertionError();
            }
        }
    }
    private byte READ_BYTE()
    {
        return chunk.getcode(ip++);
    }
    private Object READ_CONSTANT()
    {
        int index = READ_BYTE()&0xff; // here i converted signed byte -128 to 127 to unsigned byte 0 to 255;
        return chunk.constants.get(index);
    }

     // Arithmatic function
    public void BINARY_OP(char op)
    {
        double b = (double)stack.pop();
        double a = (double)stack.pop();
        switch (op) {
            case '+' -> stack.push(a+b);
            case '-' -> stack.push(a-b);
            case '*' -> stack.push(a*b);
            case '/' -> stack.push(a/b);
            default -> throw new AssertionError();
        }

    }
};


