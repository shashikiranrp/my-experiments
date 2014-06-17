#include <stdio.h>
#include <stdlib.h>
#include <ctype.h>
#include <math.h>
#include <string.h>

#define BUFFER_SIZE 1100000

static int isPrime(long num) {
  long limit = sqrt(num);
  long i = 2;
  for(; i < limit; ++i) {
     if(0 == num % i) {
       fprintf(stdout,"%ld[%ld]\n", num,i);
       return 0;
     }
  }
  return 1;
}

/*
 * returns the number, between `from' and `to' if it is prime or return -1
 */
static long sliceAndPrintPrime(char *from, char *to) {
  /*
   * since the number is palindrome, just need to check whether the first digit is even and it covers zero start and even number cases...:)
   */
  if(0 == (*from - '0') % 2) {
    return -1;
  }
  long palindromeNumber = 0L;
  int p = 0;
  while(from <= to) {
    palindromeNumber += pow(10, p++) * (*from++ - '0');
  }
  return isPrime(palindromeNumber) ? palindromeNumber : -1;
}

int main(int argc, char* argv[]) {
  if(4 != argc && 3 != argc) {
    fprintf(stderr, "usage: %s <ip_file_name>|- <step_count> [--all]\n", argv[0]);
    return 1;
  }

  FILE* ip_file = !strcmp(argv[1], "-") ? stdin : fopen(argv[1], "r");
  if(NULL == ip_file) {
    perror(argv[1]);
    return 1;
  }
  
  int step = atoi(argv[2]);
  if (0 >= step) {
    fputs("stepcount should be positive!!!\n", stderr);
    return 1;
  } 

  char *head = (char*) malloc (sizeof(char) * BUFFER_SIZE);
  char *tail = head, *eLocator, *sLocator = eLocator =  NULL, *root;
  char ch = -1;;
  int exitNow = NULL != argv[3] && !strcmp(argv[3],"--all") ? 0 : 1;
  long currentPrime = 0L;
  while(-1 != (ch = fgetc(ip_file))) {
    isalnum(ch) ? *tail++ = ch : 0;
  }
  *tail = '\0';
  tail = head + step - 1;
  root = head;

  while(*tail) {
    if(*head == *tail) {
      if(NULL == sLocator &&  NULL == eLocator) {
        sLocator = head,eLocator = tail;
      }
      if(head < tail) {
        ++head,--tail;
      } else {
        head = sLocator + 1,tail = eLocator + 1,currentPrime = sliceAndPrintPrime(sLocator, eLocator);
	      if(-1 != currentPrime) {
          fprintf(stdout,"%ld(%ld)\n",currentPrime,sLocator - root);
	        currentPrime = -1;
	        if(exitNow) {
            exit(0);
	        }
	      }
	      sLocator = eLocator = NULL;
     }
    } else {
      if(NULL == sLocator && NULL == eLocator) {
        ++head, ++tail; 
      } else {
        head = sLocator + 1,tail = eLocator + 1,sLocator = eLocator = NULL;
      }
    }
  }

  free(root);
  fclose(ip_file);
  return 0;
}
