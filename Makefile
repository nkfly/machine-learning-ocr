CLASSPATH := bin:src
CLASSPATH := $(CLASSPATH):libs/commons-cli-1.2/commons-cli-1.2.jar
CLASSPATH := $(CLASSPATH):libs/weka.jar
CLASSPATH := $(CLASSPATH):libs/libsvm.jar
CLASSPATH := $(CLASSPATH):libs/lingpipe-4.1.0.jar
CLASSPATH := $(CLASSPATH):libs/LibLINEAR.jar
CLASSPATH := $(CLASSPATH):libs/liblinear-1.92.jar

ALGORITHM = ann

# files
TRAIN_FILE = ./data/ml2013final_train.dat
RESAMPLED_FILE = ./data/resampled.dat
DOWNSAMPLED_FILE = ./data/downsampled.dat
DOWNSAMPLED_26x30_FILE = ./data/downsampled_26x30.dat
FILLED_FILE = ./data/filled.dat
TEST_FILE = ./data/ml2013final_test1.nolabel.dat

# output files
OUTPUT = ./data/output.dat
KNN_OUTPUT = ./data/knn_output.dat
LINEARSVM_OUTPUT = ./data/linear_svm_output.dat

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
		-o $(KNN_OUTPUT)

runLinearSVM: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a linear-svm\
		-s 7\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)\
		-o $(LINEARSVM_OUTPUT)

runAdaBoost: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a adaboost\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)\
		-o $(OUTPUT)

runUniform: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a uniform-aggregation\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)\
		-o $(OUTPUT)

resample: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a resample\
		-i $(TRAIN_FILE)\
		-o $(RESAMPLED_FILE)\
		-rspn 200

downsample: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a downsample\
		-i $(TRAIN_FILE)\
		-o $(DOWNSAMPLED_26x30_FILE)\
		-s 4

fill: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a fill\
		-i $(TRAIN_FILE)\
		-o $(FILLED_FILE)

clean:
	rm -rf bin/*
