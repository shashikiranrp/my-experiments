package org.kelvin.java8.stream;

import org.kelvin.java8.LambdaFunction;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class BasicStreamExamples
{
    public static void main(String[] args)
    {
        final int sumOf100RandomNumers
                = Util.infiniteRandomPositiveIntegers(1000).limit(100).reduce(0, (acc, x) -> acc + x);
        System.out.println("SUM(100 random integers): " + sumOf100RandomNumers);

        final Supplier<String> bigStringSupplier
                = () -> {
            try {
                return new String(Files.readAllBytes(Paths.get("/Users/shasrp/Code/Personal/Github/my-experiments/java/java8/java8-demo/src/main/resources/name.txt")));
            } catch (IOException e) {
                return "";
            }
        };
        final String threeConcatOfnameFile
                = StreamSupport.stream(new InfiniteStream<String>(bigStringSupplier), false).
                limit(3).reduce("", (acc, x) -> acc + x);
        System.out.println("CONCAT(3 names 3 times): " + threeConcatOfnameFile);

        //Map and Collectors examples
        System.out.println("SUM of first 10 natural numbers: " +
                Util.naturalNumbers().limit(10).reduce(0, (acc, next) -> acc + next));
        System.out.println("product of squares of first 5 natural numbers: " +
                Util.naturalNumbers().limit(5).
                        map(((Integer nat) -> LambdaFunction.<Integer>doOperation(LambdaFunction::multiply, nat, nat))).
                        reduce(1, LambdaFunction::multiply));

        System.out.println(" EVEN NAT < 100: " +
                Util.naturalNumbers().limit(100).filter((nat) -> 0 == (nat % 2)).collect(Collectors.toList()));

        System.out.println("UNIQUE NAT in random ints: " +
                Util.infiniteRandomPositiveIntegers(100).limit(101).collect(Collectors.toSet()));

    }
}
