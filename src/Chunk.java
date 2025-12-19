import java.util.ArrayList;
import java.util.List;

public class Chunk{
  //storeing  op code
   public enum OpCode{
      OP_CONSTANT, // to retrive a constant from constant pool
      OP_RETURN, // to return / stop
      OP_NEGATE,
      OP_ADD,
      OP_SUBTRACT,
      OP_DIVIDE,
      OP_MULTIPLY
   }
   //using this store the instructions
   List<Byte>code = new ArrayList<>();
  //using this store the operands
   List<Object>constants = new ArrayList<>();
  //using this store the line number 
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
    constants.add(value);
    return constants.size()-1;
  }
  public byte getcode(int index)
  {
    return code.get(index);
  }

};
