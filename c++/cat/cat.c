#include <stdlib.h>
#include <stdio.h>
#include <string.h>

int cat(const char *file_name) {
  FILE* ip_file = !strcmp(file_name, "-") ? stdin : fopen(file_name, "r");
  char ch = 0;
  if (NULL == ip_file) {
    perror(file_name);
    return 1;
  }
  while(-1 != (ch = fgetc(ip_file))) {
    fputc(ch, stdout);
  }
  return strcmp(file_name, "-") ? fclose(ip_file) : 0;
}


int main(int argc, char *argv[]) {
  int res = 0;
  if(1 == argc) {
    res |= cat("-");
  } else {
    /*
     * relying on std-c's promise of ending `argv' with `NULL'
     */
    while(*++argv) {
      res |= cat(*argv);
    }
  }
  return res;
}
