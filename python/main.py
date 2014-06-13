#!/usr/bin/python

#You received a transmission containing an expression of single digit positive integers (say: 9 * (5 - 4) * 3). However, during transmission all operators and brackets were lost and you only received     9 5 4 3.  Given the array of digits, you have to calculate the least positive integer value of the expression that could NOT have been received by you.  The binary operators possible are *, +, -, / and brackets possible are ( and ).  Note that / is an integer division i.e. 9/5 = 1.
# 
# Example:  You have received 6,6,4,4. The minimum positive integer that could NOT have been formed is  18. This is because integers less than 18 are formed as below.
# 
# 1 = 6 /6 + 4-4
# 2 = 6/6 + 4/4
# 3 = 6  +( 6/4) -4
# 4 = (6+6+4) / 4
# 
# 18 cannot be formed
# 
# Input Format:
# First line contains an integer N, the number of digits in the array.
# Then follow N lines each containing a digit.
# Output Format:
# Print a single integer which is the required answer.
# Sample Input:
# 4
# 6
# 6
# 4
# 4
# Sample Output:
# 18
#

import itertools

OPERATORS = ['+', '-', '*', '/']
OPERATORS_LEN = len(OPERATORS)

#Generates operators sequence
def getOperatorPathGen(numOfOperators=3):
  if 1 > numOfOperators:
    raise StopIteration
  
  indexes = list(itertools.repeat(0, numOfOperators))
  while True:
    stopFlag = all(map(lambda idx: idx == OPERATORS_LEN - 1, indexes))
    cur = map(lambda indx: OPERATORS[indx], indexes)
    yield cur
    if stopFlag:
      break
    iix = 0
    while numOfOperators > iix:
      indexes[iix] = (indexes[iix] + 1) % OPERATORS_LEN
      if indexes[iix]:
        break
      iix += 1
  raise StopIteration

# for associating parenthesis
def paren_assoc(seq):
  grouper = lambda a,b:(a,b)
  lifter = lambda x:x

  if len(seq)==1:
    yield lifter(seq[0])
  else:
    for i in range(len(seq)):
      left,right = seq[:i],seq[i:] 

      for l in paren_assoc(left):
        for r in paren_assoc(right):
          yield grouper(l,r)

# Reduce the given terms and operators
def reduceToExp(terms, operators):
  exp = ""
  operators.append('')
  for pair in zip(terms, operators):
    exp += pair[0] +  pair[1]
  return exp

# Expression genrator
def expGen(assocs, operators_list):
  for assoc in assocs:
    terms = assoc.split(',')
    for oprs in operators_list:
      yield reduceToExp(terms, oprs)

#Assumes seq is monotonically increasing
def findFirstMissingNumberInSeq(seq):
  if len(seq) == 1:
    return None
  elif len(seq) == 2:
    return None if seq[1] - seq[0] == 1 else seq[0] + 1
  for i in seq:
    index = 0
    seq_len = len(seq)
    while index < seq_len:
      curr_num = seq[index]
      next_num = seq[index + 1]
      if next_num - curr_num != 1:
        return curr_num + 1
      index += 1

#print's the min value by evaluating each expression
def main(assocs, operators_list):
  if not len(operators_list):
    print abs(int(assocs[0]) - 1)
    return
  res = []
  for exp in list(expGen(assocs, operators_list)):
    try:
      res.append(eval(exp))
    except ZeroDivisionError:
      pass
  # We need uniq positive values
  se = sorted(set(filter(lambda x: x >= 0,res)))
  print "Minimum number that couldn't have been recieved is: %d" % findFirstMissingNumberInSeq(se)

# Let's start
if __name__ == "__main__":
  noOfElements = int(raw_input("Enter the number of elements: "))
  seq = []
  idx = 0
  while idx < noOfElements:
    nxt_num = int(raw_input("Next number? "))
    if nxt_num < 0:
      print "Not a negative number!"
      continue
    seq.append(nxt_num)
    idx += 1

  main(map(str, paren_assoc(seq)), list(getOperatorPathGen(noOfElements - 1)))

