CC = g++
CFLAGS  = -fopenmp
TARGET = gauss_omp
PROGNAME = main.cpp

all: $(TARGET)

$(TARGET): $(PROGNAME)
	$(CC) $(CFLAGS)  `pkg-config --cflags opencv` $(PROGNAME) -o $(TARGET) `pkg-config --libs opencv`
