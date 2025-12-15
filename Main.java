public class Main{
    public static void main(String[] args) {
        Chunk c = new Chunk();

        //passing the instruction and the line number 
        int index = c.addconstant(4.2);

        c.writeCode(Chunk.OpCode.OP_CONSTANT, 1);
        c.writeByte((byte)index, 2);
        c.writeCode(Chunk.OpCode.OP_NEGATE, 3);
    
        c.writeCode(Chunk.OpCode.OP_RETURN, 4);

        // Debug debug = new Debug();
        VM vm = new VM();
        vm.interpret(c);
        // debug.disassemblerChunk(c, "Test 1");
    }
}