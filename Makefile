CLASSPATH := bin:src
CLASSPATH := $(CLASSPATH):libs/commons-cli-1.2/commons-cli-1.2.jar
CLASSPATH := $(CLASSPATH):libs/weka.jar
CLASSPATH := $(CLASSPATH):libs/libsvm.jar
CLASSPATH := $(CLASSPATH):libs/lingpipe-4.1.0.jar
CLASSPATH := $(CLASSPATH):libs/LibLINEAR.jar
CLASSPATH := $(CLASSPATH):libs/liblinear-1.92.jar
CLASSPATH := $(CLASSPATH):libs/WekaLibSVM.jar

ALGORITHM = ann

# files
# TRAIN_FILE = ./data/train_cropped.dat
TRAIN_FILE = ./data/trainWith1_cropped.dat
RESAMPLED_FILE = ./data/resampled.dat
DOWNSAMPLED_FILE = ./data/downsampled.dat
DOWNSAMPLED_26x30_FILE = ./data/downsampled_26x30.dat
CROP_DOWNSAMPLE_35x35_FILE = ./data/crop_downsample_35x35.dat
CROP_DOWNSAMPLE_21x21_FILE = ./data/crop_downsample_21x21.dat
CROP_DOWNSAMPLE_15x15_FILE = ./data/crop_downsample_15x15.dat
CROP_DOWNSAMPLE_15x15_TEST_FILE = ./data/crop_downsample_test_15x15.dat
CROP_DOWNSAMPLE_15x15_ZEROONE_FILE = ./data/crop_downsample_zeroone_15x15.dat
FILLED_FILE = ./data/filled.dat
TEST_FILE = ./data/test2_cropped.dat

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
runKNN: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a knn\
		-m cv\
		-k 13\
		-tr $(CROP_DOWNSAMPLE_15x15_FILE)\
		-te $(CROP_DOWNSAMPLE_15x15_TEST_FILE)

runLinearSVM: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a linear-svm\
		-m lm\
		-s 7\
		-tr $(RESAMPLED_FILE)\
		-te $(RESAMPLED_FILE)

runPolySVM: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a poly-svm\
		-m cv\
		-tr $(CROP_DOWNSAMPLE_15x15_FILE)\
		-te $(CROP_DOWNSAMPLE_15x15_TEST_FILE)

runNaiveBayes: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a naive-bayes\
		-m cv\
		-tr $(CROP_DOWNSAMPLE_15x15_FILE)\
		-te $(CROP_DOWNSAMPLE_15x15_TEST_FILE)

runRandomForest: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a random-forest\
		-m cv\
		-tr $(CROP_DOWNSAMPLE_15x15_FILE)\
		-te $(CROP_DOWNSAMPLE_15x15_TEST_FILE)

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
		-tr $(CROP_DOWNSAMPLE_15x15_FILE)\
		-te $(CROP_DOWNSAMPLE_15x15_TEST_FILE)

runLinearAggregation: build
	java -cp $(CLASSPATH) ml.humaning.ZodiacCharacterRecognizer\
		-a linear-svm-aggregation\
		-m cv\
		-s 7\
		-tr $(CROP_DOWNSAMPLE_15x15_FILE)\
		-te $(CROP_DOWNSAMPLE_15x15_TEST_FILE)

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
		-i $(TEST_FILE)\
		-o $(CROP_DOWNSAMPLE_15x15_TEST_FILE)\
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
