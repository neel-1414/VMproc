import java.util.ArrayList;
import java.util.List;

public class Chunk{
  //store our op code
   public enum OpCode{
    //opcode value as 0 stored automatically
    OP_CONSTANT, // to retrive a constant from constant pool
    OP_RETURN, // to return / stop 
   }
   //store the instructions
   List<Byte>code = new ArrayList<>();
  //store the operands
   List<Object>constant = new ArrayList<>();
  //store the line number 
   List<Integer>lines = new ArrayList<>();

  public void writeCode(OpCode opcode, int line)
  {
    code.add((byte)opcode.ordinal());
    lines.add(line);
  }
  public void writeByte(byte operand, int line)
  {
    code.add(operand);
    lines.add(line);
  }
  public int addconstant(Object value)
  {
    constant.add(value);
    return constant.size()-1;
  }

};
