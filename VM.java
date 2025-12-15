
import java.util.Stack;


public class VM {
    private Chunk chunk;
    private  int ip;
    static final boolean DEBUG_RACE_EXECUTION = true;
    Stack<Object> stack = new Stack<>(); 

    public Interpret interpret(Chunk chunk)
    {
        resetStack();
        this.chunk = chunk; // chunk is stored in vm
        this.ip = 0;// instruction pointer
        return run();
    }
    void resetStack() // top empty the stack completely
    {
        stack.clear();
    }
    //most important this is running my bytecode
    Interpret run()
    {
        Debug d = new Debug();
        while(true)
        {
           
            if(DEBUG_RACE_EXECUTION) // just for debugging on every opcode
            {
                System.out.println("        ");
                for(int i =0; i<stack.size(); i++)
                {
                    System.out.print("[");
                    System.out.print(stack.get(i));
                    System.out.println("]");
                }
                d.disassembleInstructions(this.chunk, this.ip);
            }
            byte instructions = READ_BYTE();
            Chunk.OpCode opCode = Chunk.OpCode.values()[Byte.toUnsignedInt(instructions)];
            switch (opCode) {
                case OP_RETURN -> {
                    System.out.println(stack.pop());
                    return Interpret.INTERPRET_OK;
                }
                case OP_CONSTANT -> {
                    Object constant = READ_CONSTANT();
                    stack.push(constant);
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
};


