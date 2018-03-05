import java.io.*;
   
public class Main {
	static public void main(String argv[]) {    
		/* Start the parser */
		try {
    		parser p = new parser(new Lexer(new FileReader(argv[0])));
			Object result;
			if (argv.length > 1 && argv[1].equals("debug")) {
				result = p.debug_parse().value;
			} else {
				result = p.parse().value;

				FileWriter writer = new FileWriter(argv[0] + ".h");
				writer.write(p.toAssemblyCode());
				writer.close();
			}
		} catch (Exception e) {
			/* do cleanup here -- possibly rethrow e */
			e.printStackTrace();
		}
	}
}
