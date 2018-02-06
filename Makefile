make:
	jflex lexical.jflex
	java -cp .:lib/java-cup-11a.jar java_cup.Main -interface -expect 2 -dump_grammar < grammar.cup > grammar_dump 2>&1
	javac -cp .:lib/java-cup-11a.jar Main.java
clean:
	rm parser.* sym.* Lexer.* *.class *.log
test:
	touch error.log
	truncate -s 0 error.log
	for filename in tests/*.c ; do \
		java -cp .:lib/java-cup-11a-runtime.jar Main $$filename 2>&1 | sed "s,^,Error in $$filename: ," >> error.log ; \
	done
debug:
	java -cp .:lib/java-cup-11a-runtime.jar Main tests/test_success.c "debug"
