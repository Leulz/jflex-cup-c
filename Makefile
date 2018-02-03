clean:
	rm parser.* sym.* Lexer.* CUP\$$parser\$$actions.class Main.class

make:
	jflex lexical.jflex
	java -cp .:lib/java-cup-11a.jar java_cup.Main -interface -expect 2 < grammar.cup
	javac -cp .:lib/java-cup-11a.jar Main.java

run:
	java -cp .:lib/java-cup-11a-runtime.jar Main example
