#include <stdio.h>

long fib(long n) {
    return n <= 2 ? ((n >> 1) & 1) : fib(n-1) + fib(n-2);
}


int main() {
    long num;
    puts("number...?");
    return (scanf("%ld",&num),printf("Fib is %ld...\n",fib(num)),0);
}
