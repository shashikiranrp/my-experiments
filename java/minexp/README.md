Java Solution for below problem:

You received a transmission containing an expression of single digit positive integers (say: 9 * (5 - 4) * 3). However, during transmission all operators and brackets were lost and you only received     9 5 4 3.  Given the array of digits, you have to calculate the least positive integer value of the expression that could NOT have been received by you.  The binary operators possible are *, +, -, / and brackets possible are ( and ).  Note that / is an integer division i.e. 9/5 = 1.

Example:  You have received 6,6,4,4. The minimum positive integer that could NOT have been formed is  18. This is because integers less than 18 are formed as below.

1 = 6 /6 + 4-4
2 = 6/6 + 4/4
3 = 6  +( 6/4) -4
4 = (6+6+4) / 4

18 cannot be formed

Input Format:
First line contains an integer N, the number of digits in the array.
Then follow N lines each containing a digit.
Output Format:
Print a single integer which is the required answer.
Sample Input:
4
6
6
4
4
Sample Output:
18


