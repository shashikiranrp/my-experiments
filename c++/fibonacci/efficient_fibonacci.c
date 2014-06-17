#include <stdio.h>

static int count;

long __fib(long p1, long p2) {
   return (++count,p1 + p2);
}

long __fibOfDepth(long lastButOne, long last, int depth) {
  long currFib = __fib(lastButOne,last);
  return depth == 0 ? currFib: __fibOfDepth(last, currFib, depth - 1);
}

long fib(int first, int diff, int depth) {
  return depth <= 0 ? -1 : 
         depth == 1 ? first : 
	 depth == 2 ? __fib(first, diff) :
	 __fibOfDepth(first, __fib(first, diff), depth - 3);
}

int
main()
{
        int first, diff,depth;
	puts("Enter first entry followed by the constat difference and end your entry by fibonocci depth...:)?");
	scanf("%d %d %d",&first,&diff,&depth);
	printf("the value of sum is %ld\n", fib(first, diff, depth));
	printf("iterations %d\n", count);
	return 0;

}

