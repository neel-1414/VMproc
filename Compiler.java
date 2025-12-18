public class Compiler {
    public void compile(String source)
    {
        Scan sc = new Scan();
        sc.initScan(source);
        int line =-1;
        while(true)
        {
            Token token = scanToken();
            if(token.line != line)
            {
                System.out.printf("%04d", token.line);
                line = token.line;
            }
            else{
                System.out.println("   |");
            }
            System.out.println(token);

        }
    }
}
