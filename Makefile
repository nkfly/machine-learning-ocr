CLASSPATH := bin:src
CLASSPATH := $(CLASSPATH):libs/commons-cli-1.2/commons-cli-1.2.jar
CLASSPATH := $(CLASSPATH):libs/weka.jar
CLASSPATH := $(CLASSPATH):libs/libsvm.jar
CLASSPATH := $(CLASSPATH):libs/lingpipe-4.1.0.jar
CLASSPATH := $(CLASSPATH):libs/LibLINEAR.jar
CLASSPATH := $(CLASSPATH):libs/liblinear-1.92.jar

ALGORITHM = ann

# files
TRAIN_FILE = ./data/train.dat
RESAMPLED_FILE = ./data/resampled.dat
DOWNSAMPLED_FILE = ./data/downsampled.dat
DOWNSAMPLED_26x30_FILE = ./data/downsampled_26x30.dat
CROP_DOWNSAMPLE_35x35_FILE = ./data/crop_downsample_35x35.dat
CROP_DOWNSAMPLE_15x15_FILE = ./data/crop_downsample_15x15.dat
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

# ===== Algorithms =====
runSVM:
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a svm\
		-tr $(TRAIN_FILE)\
		-te $(TEST_FILE)

runKNN: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a knn\
		-m cv\
		-k 2\
		-tr $(CROP_DOWNSAMPLE_15x15_FILE)\
		-te $(RESAMPLED_FILE)

runLinearSVM: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a linear-svm\
		-m cv\
		-s 7\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)

runAdaBoost: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a adaboost\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)

# ===== Aggregation =====
runUniform: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a uniform\
		-m cv\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)\
		-o $(OUTPUT)

runLinearAggregation: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a linear-svm-aggregation\
		-m cv\
		-s 7\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)\
		-o $(OUTPUT)

runAggregation: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a aggregation\
		-m cv\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)\
		-o $(OUTPUT)

# ===== preprocessing =====
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
		-o $(CROP_DOWNSAMPLE_15x15_FILE)\
		-s 7

downsample_custom: build
	java -Xmx2048m -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a downsample\
		-i $(TRAIN_FILE)\
		-o $(DOWNSAMPLED_CUSTOMFILE)\
        -x $(XX)\
        -y $(YY)

fill: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a fill\
		-i $(TRAIN_FILE)\
		-o $(FILLED_FILE)

mergeTest: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a merge-test\
		-i ./data/test1.dat.txt\
		-o ./data/test1.dat

# ===== clean =====
clean:
	rm -rf bin/*
