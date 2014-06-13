package org.kelvin.minexp;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @author Shashikiran
 */
public class OperatorSequenceGenrator
{

    private static final String OPERATORS = "+-*/";
    private static final int OPERATORS_LEN = OPERATORS.length();
    private final int operatorBoxCount;

    public OperatorSequenceGenrator(int operatorBoxCount)
    {
        if (operatorBoxCount < 0) {
            throw new IllegalArgumentException("count can't be negative!");
        }
        this.operatorBoxCount = operatorBoxCount;
    }

    List<String> getSequence()
    {
        List<String> sequence = new ArrayList<>();
        /**
         * Maintain index for each box and initialize to zero.
         */
        List<Integer> indicies = new ArrayList<>(operatorBoxCount);
        for (int i = 0; i < operatorBoxCount; ++i) {
            indicies.add(0);
        }
        boolean stopFlag = false;
        while (true) {
            stopFlag = true;
            for (int index : indicies) {
                stopFlag &= index == OPERATORS_LEN - 1;
            }
            StringBuilder builder = new StringBuilder();
            for (int index : indicies) {
                builder.append(OPERATORS.charAt(index));
            }
            sequence.add(builder.toString());
            if (stopFlag) {
                break;
            }
            int iix = 0;
            while (this.operatorBoxCount > iix) {
                indicies.set(iix, (indicies.get(iix) + 1) % OPERATORS_LEN);
                if (indicies.get(iix) > 0) {
                    break;
                }
                iix++;
            }

        }
        return sequence;
    }
}
