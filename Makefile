all:
	javac -cp src src/ml/humaning/core/Learner.java -d bin

clean:
	rm -rf bin/*
