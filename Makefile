
.PHONY: all

all: sat-solver tester

# even non-Haskell solutions will need to compile the tester to run the tests
tester: Tester.hs
	ghc -o tester Tester.hs

# can comment this if your solution is not in Haskell
sat-solver: src/ogochi/sat/*.java src/manifest.txt
	javac src/ogochi/sat/*.java
	jar cfm sat-solver src/manifest.txt -C src ogochi/sat

clean:
	rm sat-solver tester *.hi *.o
	rm src/ogochi/sat/*.class
