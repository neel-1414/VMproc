
public class Main{
    public static void main(String[] args) {
        Chunk c = new Chunk();

        //passing the instruction and the line number 
        int index = c.addconstant(4);
        c.writeCode(Chunk.OpCode.OP_CONSTANT, 1);
        c.writeByte((byte)index, 1);
        c.writeCode(Chunk.OpCode.OP_RETURN, 1);
        Debug debug = new Debug();
        debug.disassemblerChunk(c, "Test 1");
    }
}