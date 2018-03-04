make:
	jflex lexical.jflex
	java -cp .:lib/java-cup-11a.jar java_cup.Main -interface -expect 1 grammar.cup
	javac -cp src/ -d bin/ src/exception/*.java src/model/*.java src/util/*.java
	javac -cp .:bin:lib/java-cup-11a.jar Main.java
clean:
	rm -rf parser.* sym.* Lexer.* *.class *.log bin/*
test:
	touch error.log
	truncate -s 0 error.log
	for filename in tests/*.c ; do \
		java -cp .:bin:lib/java-cup-11a-runtime.jar Main $$filename 2>&1 | sed "s,^,Error in $$filename: ," >> error.log ; \
	done
debug:
	java -cp .:bin:lib/java-cup-11a-runtime.jar Main tests/test_success.c "debug"
