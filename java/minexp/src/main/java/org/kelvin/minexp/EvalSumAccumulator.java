package org.kelvin.minexp;

import java.util.Set;
import java.util.TreeSet;

/**
 * 
 * @author Shashikiran
 */
public class EvalSumAccumulator
{

    private TreeSet<Integer> expressionReductions = new TreeSet<>();
    private InfixExpressionEvaluator evaluator = new InfixExpressionEvaluator();

    public void accumulate(String expr)
    {
        try {
            int value = evaluator.eval(expr);
            if (value < 0) {
                //Ignore we don't care about negative values.
                return;
            }
            expressionReductions.add(value);
        } catch (ArithmeticException e) {
            //Ignore Divide by zero Error
        }
    }

    public Set<Integer> getReductions()
    {
        return expressionReductions;
    }

    public Integer getFirstMinimumImpossibleNumber()
    {
        if (this.expressionReductions.isEmpty()) {
            return 0;
        }
        if (this.expressionReductions.size() == 1) {
            return expressionReductions.first() + 1;
        }

        int index = 0, seqLen = this.expressionReductions.size();
        Integer[] values = this.expressionReductions.toArray(new Integer[0]);
        int currNum, nextNum;
        while (index < seqLen) {
            currNum = values[index];
            nextNum = values[index + 1];
            if (nextNum - currNum != 1) {
                return currNum + 1;
            }
            index++;
        }

        return values[index - 1] + 1;
    }
}
