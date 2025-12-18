
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;


public class Main{
    static final VM vm = new VM();
    public static void main(String[] args) throws IOException{
        //the args are when we run main java Main (abc)  
        if(args.length == 0 ){
            repl();
        }
        else if(args.length == 1){
             runfile(args[0]);
        }
        else {
            System.err.println("To many arugmenets passed");
            System.exit(64);
        }
    }
    private final static void repl() throws IOException
    {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        while (true) {
            System.out.print("> ");
            String line = reader.readLine();
            if(line == null) break;

            InterpretResult result = vm.interpret(line);
            
        }
    }
    private static void runfile(String path) throws IOException
    {
        String source = Files.readString(Path.of(path)); // reads the complete file passed and decodes to string  UTF-8 by default
        InterpretResult result  = vm.interpret(source);
        if(result == InterpretResult.INTERPRET_COMPILE_ERROR) System.exit(65);
        if(result == InterpretResult.INTERPRET_RUNTIME_ERROR) System.exit(70);
    }

    {

    }
}