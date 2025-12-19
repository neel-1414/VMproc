public class Compiler {
    public void compile(String source)
    {
        Scanner sc = new Scanner(source);
        int line =-1;
        while(true)
        {
            Token token = sc.scanToken();
            if(token.line != line)
            {
                System.out.printf("%04d", token.line);
                line = token.line;
            }
            else{
                System.out.println("   |");
            }
            System.out.println(token);

            if(token.type == TokenType.TOKEN_EOF) break;

        }
    }
}
