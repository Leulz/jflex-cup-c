make:
	jflex lexical.jflex
	java -cp .:lib/java-cup-11b.jar:lib/commons-lang3-3.6.jar java_cup.Main -interface -expect 1 grammar.cup
	javac -cp src/:lib/commons-lang3-3.6.jar -d bin/ src/exception/*.java src/model/*.java src/util/*.java src/codegen/*.java
	javac -cp .:bin:lib/java-cup-11b.jar:lib/commons-lang3-3.6.jar Main.java
clean:
	rm -rf parser.* sym.* Lexer.* *.class *.log bin/* tests/*.h
test:
	touch error.log
	truncate -s 0 error.log
	for filename in tests/*.c ; do \
		java -cp .:bin:lib/java-cup-11b-runtime.jar:lib/commons-lang3-3.6.jar Main $$filename 2>&1 | sed "s,^,Error in $$filename: ," >> error.log ; \
	done
debug:
	java -cp .:bin:lib/java-cup-11b-runtime.jar:lib/commons-lang3-3.6.jar Main tests/test_success.c "debug"
