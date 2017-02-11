CC = mpiCC
CFLAGS  = zad5.cpp
TARGET = gauss_mpi

all: $(TARGET)

$(TARGET): $(PROGNAME)
	$(CC) -Wall `pkg-config --cflags opencv` $(CFLAGS) -o $(TARGET) `pkg-config --libs opencv` -lm
