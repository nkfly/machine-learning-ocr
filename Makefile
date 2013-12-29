CLASSPATH := bin:src
CLASSPATH := $(CLASSPATH):libs/commons-cli-1.2/commons-cli-1.2.jar:libs/weka.jar:libs/libsvm.jar:libs/lingpipe-4.1.0.jar

ALGORITHM = ann

# files
TRAIN_FILE = ./data/ml2013final_train.dat
RESAMPLED_FILE = ./data/resampled.dat
FILLED_FILE = ./data/filled.dat
TEST_FILE = ./data/ml2013final_test1.nolabel.dat
OUTPUT = ./data/output.dat

all: build

build:
	javac -cp $(CLASSPATH) src/ml/humaning/ZodiacCharacterRecognizer.java -d bin

run:
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a $(ALGORITHM)\
		-tr $(TRAIN_FILE)\
		-te $(TEST_FILE)\
		-o $(OUTPUT)

runSVM:
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a svm\
		-tr $(TRAIN_FILE)\
		-te $(TEST_FILE)\
		-o $(OUTPUT)

runKNN: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a knn\
		-k 10\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)\
		-o $(OUTPUT)

runLinearSVM: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a linear-svm\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)\
		-o $(OUTPUT)

resample: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a resample\
		-i $(TRAIN_FILE)\
		-o $(RESAMPLED_FILE)\
		-rspn 200

fill: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a fill\
		-i $(TRAIN_FILE)\
		-o $(FILLED_FILE)

clean:
	rm -rf bin/*
