package org.kelvin.minexp;

import java.util.List;
import java.util.Scanner;

/**
 * 
 * @author Shashikiran
 */
public class App
{

    static boolean isAssociativeSequence(String str)
    {
        if (null == str || str.isEmpty()) {
            return false;
        }

        char firstChar = str.charAt(0);
        if ('+' != firstChar && '*' != firstChar) {
            return false;
        }

        for (Character character : str.toCharArray()) {
            if (firstChar != character) {
                return false;
            }
        }
        return true;
    }

    static String[] splitAndGetAsArray(String str)
    {
        if (null == str || str.isEmpty()) {
            return new String[0];
        }

        String[] res = new String[str.length()];
        int index = 0;
        for (Character character : str.toCharArray()) {
            res[index++] = Character.toString(character);
        }
        return res;
    }

    static String[] getOperands()
    {
        Scanner scanner = new Scanner(System.in);
        int size;
        System.out.println("Enter the number of elements!");
        size = scanner.nextInt();
        if (0 >= size) {
            throw new RuntimeException("invalid size: " + size);
        }

        int index = 0;
        String operands[] = new String[size];
        while (index < size) {
            String operand = scanner.next();
            if (operand.length() != 1) {
                throw new RuntimeException("operand should be a single digit!");
            }
            for (Character ch : operand.toCharArray()) {
                if (!Character.isDigit(ch)) {
                    throw new RuntimeException("Illegal code point: " + ch);
                }
            }
            operands[index++] = operand;
        }
        return operands;
    }

    public static void main(String[] args)
    {
        EvalSumAccumulator accumulator = new EvalSumAccumulator();
        String[] operands = getOperands();
        List<String> assocs = new AssociationGenrator(operands).getAssociations();
        List<String> opSeqs = new OperatorSequenceGenrator(operands.length - 1).getSequence();
        int count = 0;
        for (String opSeq : opSeqs) {
            String[] operators = splitAndGetAsArray(opSeq);
            if (isAssociativeSequence(opSeq)) {
                accumulator.accumulate(String.format(assocs.get(0), (Object[]) operators));
                count++;
                continue;
            }
            for (String assoc : assocs) {
                accumulator.accumulate(String.format(assoc, (Object[]) operators));
                count++;
            }
        }

        System.out.println(accumulator.getFirstMinimumImpossibleNumber());
    }
}
