include ../../build/rules.mk

all: compile link
	
compile:
	$(CC_C) $(CFLAGS) main.c 

link:
	$(CC) $(CFLAGS) -o palin main.o -lm

clean:
	$(RM) main.o palin
