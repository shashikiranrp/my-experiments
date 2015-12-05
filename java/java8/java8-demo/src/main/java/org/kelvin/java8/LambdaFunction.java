package org.kelvin.java8;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class LambdaFunction
{

    interface Operation<T>
    {
        T apply(T op1, T op2);
    }

    interface ComposableOperation<T> extends Operation<T>
    {

        default int getPrecedence()
        {
            return 5;
        }

        // Left associative bridge is default implementation
        default T compose(final ComposableOperation<T> nextOperation,
                                               final T accOp1, final T accOp2, final T bridgeOp)
        {
            return getPrecedence() < nextOperation.getPrecedence()
                    ? apply(nextOperation.apply(accOp1, accOp2), bridgeOp)
                    : nextOperation.apply(apply(accOp1, accOp2), bridgeOp);
        }
    }

    // lambda as arg
    static <T> T doOperation(final Operation<T> operation, final T op1, final T op2)
    {
        return operation.apply(op1, op2);
    }

    // lambda as return expression
    static Operation<Integer> getIntDiffOperation()
    {
        return ((op1, op2) -> op1 - op2);
    }

    // static method reference as lambda
    static int multiply(final int op1, final int op2)
    {
        return op1 * op2;
    }

    // object message as lambda
    int mod(final int op1, final int op2)
    {
        return op1 % op2;
    }

    public static void main(final String... args)
    {
        final Integer operand1 = 1, operand2 = 2, operand3 = 3;
        final Integer sum = LambdaFunction.<Integer>doOperation((op1, op2) -> op1 + op2, operand1, operand2);
        final Integer diff = LambdaFunction.doOperation(getIntDiffOperation(), operand1, operand2);
        final Integer product = LambdaFunction.doOperation(LambdaFunction::multiply, operand1, operand2);
        final Integer mod = LambdaFunction.doOperation(new LambdaFunction()::mod, operand1, operand2);

        // 1 + 2 * 3
        final ComposableOperation<Integer> additionOperation = (op1, op2) -> op1 + op2;
        final ComposableOperation<Integer> productOperation = new ComposableOperation<Integer>()
        {
            @Override
            public Integer apply(final Integer op1, final Integer op2)
            {
                return op1 * op2;
            }

            @Override
            public int getPrecedence()
            {
                return 6;
            }
        };
        final Integer exprVal = additionOperation.compose(productOperation, operand1, operand2, operand3);
        final Integer exprVal2 = productOperation.compose(additionOperation, operand1, operand2, operand3);
        System.out.println(String.format("OP1: %d OP2: %d OP3: %d %n SUM: %d %n DIFF: %d %n " +
                "PRODUCT: %d %n MOD: %d %n COMPOSE(OP1 + OP2 * OP3) 1: %d %n COMPOSE(OP1 + OP2 * OP3) 2: %d %n",
                operand1, operand2, operand3, sum, diff, product, mod, exprVal, exprVal2));
    }
}
