package org.kelvin.java8.stream;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.StreamSupport;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class BasicStreamExamples
{
    public static void main(String[] args)
    {
        final Collection<Integer> hundredOnes = Collections.nCopies(100, new Integer(1));
        System.out.println("No of elelments: " + hundredOnes.stream().count());
        final Supplier<Integer> randomIntSupplier
                = () -> new Random().nextInt(1000);
        final int sumOf100RandomNumers
                = StreamSupport.stream(new InfiniteStream<Integer>(randomIntSupplier), false).
                limit(100).reduce(0, (acc, x) -> acc + x);
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
                reduce("", (acc, x) -> acc + x);
        System.out.println("CONCAT(3 names 3 times): " + threeConcatOfnameFile);
    }
}
