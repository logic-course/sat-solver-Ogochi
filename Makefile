
.PHONY: all

all: sat-solver tester

tester: Tester.hs
	ghc -o tester Tester.hs

# Requires Java 11 in order to compile solver
sat-solver: src/ogochi/sat/*.java src/manifest.txt
	# sudo apt install openjdk-11-jdk-headless
	javac src/ogochi/sat/*.java
	jar cfm sat-solver src/manifest.txt -C src ogochi/sat

clean:
	rm src/ogochi/sat/*.class
	rm sat-solver tester *.hi *.o
