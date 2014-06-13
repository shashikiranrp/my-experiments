package org.kelvin.minexp;

import java.util.Stack;

/**
 * 
 * @author Shashikiran
 */
public class InfixExpressionEvaluator
{

    private static final String possibleOperators = "-+/*";
    private static final String possibleOperands = "0123456789";

    public int eval(String infix)
    {
        return evaluatePostfix(convertToPostfix(infix));
    }

    private String convertToPostfix(String infixExpression)
    {
        char[] tokens = infixExpression.toCharArray();
        Stack<Character> stack = new Stack<>();
        StringBuilder postfixBuilder = new StringBuilder(infixExpression.length());

        for (char c : tokens) {
            if (isOperator(c)) {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    if (operatorGreaterOrEqual(stack.peek(), c)) {
                        postfixBuilder.append(stack.pop());
                    } else {
                        break;
                    }
                }
                stack.push(c);
            } else if (c == '(') {
                stack.push(c);
            } else if (c == ')') {
                while (!stack.isEmpty() && stack.peek() != '(') {
                    postfixBuilder.append(stack.pop());
                }
                if (!stack.isEmpty()) {
                    stack.pop();
                }
            } else if (isOperand(c)) {
                postfixBuilder.append(c);
            }
        }
        while (!stack.empty()) {
            postfixBuilder.append(stack.pop());
        }
        return postfixBuilder.toString();
    }

    private int evaluatePostfix(String postfixExpr)
    {
        char[] chars = postfixExpr.toCharArray();
        Stack<Integer> stack = new Stack<>();
        for (char operator : chars) {
            if (isOperand(operator)) {
                stack.push(operator - '0');
            } else if (isOperator(operator)) {
                int operand1 = stack.pop();
                int operand2 = stack.pop();
                int result;
                switch (operator) {
                    case '*':
                        result = operand1 * operand2;
                        stack.push(result);
                        break;
                    case '/':
                        result = operand2 / operand1;
                        stack.push(result);
                        break;
                    case '+':
                        result = operand1 + operand2;
                        stack.push(result);
                        break;
                    case '-':
                        result = operand2 - operand1;
                        stack.push(result);
                        break;
                    default:
                        throw new IllegalArgumentException("Illegal operation: " + operator);
                }
            }
        }
        return stack.pop();
    }

    private int getPrecedence(char operator)
    {
        int ret = 0;
        if (operator == '-' || operator == '+') {
            ret = 1;
        } else if (operator == '*' || operator == '/') {
            ret = 2;
        }
        return ret;
    }

    private boolean operatorGreaterOrEqual(char op1, char op2)
    {
        return getPrecedence(op1) >= getPrecedence(op2);
    }

    private boolean isOperator(char val)
    {
        return possibleOperators.indexOf(val) >= 0;
    }

    private boolean isOperand(char val)
    {
        return possibleOperands.indexOf(val) >= 0;
    }
}