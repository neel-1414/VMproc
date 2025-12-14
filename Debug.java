
import java.util.Objects;





public class Debug {
    public void disassemblerChunk(Chunk chunk, String name)
    {
        System.out.println("=== " + name + " ===");

        for(int offset = 0; offset < chunk.code.size();)
        {
            offset = disassembleInstructions(chunk , offset);
        }
    }
    int disassembleInstructions(Chunk chunk, int offset)
    {
        System.out.printf("%04d ",offset); // printing the index of opcode
         //now printing the line
        if(offset > 0 && Objects.equals(chunk.lines.get(offset), chunk.lines.get(offset-1)))
        {
            System.out.printf("   |");
        }
        else{
            System.out.printf("%4d ",chunk.lines.get(offset));
        }
        byte instruction = chunk.code.get(offset);
        //byte -> unsigned int -> enum
        Chunk.OpCode opCode = Chunk.OpCode.values()[Byte.toUnsignedInt(instruction)];
        
        switch (opCode) {
            case Chunk.OpCode.OP_RETURN -> {
                return simpleInstruction("OP_RETURN", offset);
            }
            
            case Chunk.OpCode.OP_CONSTANT -> {
                return constantInstruction("OP_CONSTANT",chunk, offset);
            }

            default -> {
                System.out.println("Unknown Opcode: "+opCode);
                return offset+1;
            }
        }
    }
    int simpleInstruction(String name, int offset)
    {
        System.out.println(" "+name);
        return offset+1;
    }
    int constantInstruction(String name, Chunk chunk, int offset) // offset or index of OP_CONSTANT in code arrayList just after that is operand 
    {
        byte constant = chunk.code.get(offset+1);
        System.out.printf("%-16s %4d",name, constant);
        System.out.println(chunk.constant.get(constant));
        return offset+2;
    }
}
