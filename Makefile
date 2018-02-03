clean:
	rm parser.* sym.* Lexer.* CUP\$$parser\$$actions.class Main.class

make:
	jflex lexical.jflex
	java -cp .:lib/java-cup-11a.jar java_cup.Main -interface -expect 2 -dump_grammar < grammar.cup > grammar_dump 2>&1
	javac -cp .:lib/java-cup-11a.jar Main.java

run:
	java -cp .:lib/java-cup-11a-runtime.jar Main example.c
debug:
	java -cp .:lib/java-cup-11a-runtime.jar Main example.c "debug"
