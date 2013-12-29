CLASSPATH := bin:src
CLASSPATH := $(CLASSPATH):libs/commons-cli-1.2/commons-cli-1.2.jar:libs/weka.jar:libs/libsvm.jar:libs/lingpipe-4.1.0.jar:libs/ij.jar

ALGORITHM = ann

# files
TRAIN_FILE = ./data/ml2013final_train.dat
TEST_FILE = ./data/ml2013final_test1.nolabel.dat
OUTPUT = ./data/output.dat

all:
	javac -cp $(CLASSPATH) src/ml/humaning/ZodiacCharacterRecognizer.java -d bin

run:
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a $(ALGORITHM)\
		-tr $(TRAIN_FILE)\
		-te $(TEST_FILE)\
		-o $(OUTPUT)

clean:
	rm -rf bin/*
