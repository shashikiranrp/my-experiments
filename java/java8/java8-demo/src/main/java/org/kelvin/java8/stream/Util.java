package org.kelvin.java8.stream;

import java.util.Random;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class Util
{
    public static Stream<Integer> naturalNumbers()
    {
        return StreamSupport.stream(new InfiniteStream<Integer>(new Supplier<Integer>()
        {
            int nextNaturalNumber = 0;

            @Override
            public Integer get()
            {
                return ++nextNaturalNumber;
            }
        }), false);
    }

    public static Stream<Integer> infiniteRandomPositiveIntegers(final int maxInt)
    {
        return Stream.generate(() -> new Random().nextInt(maxInt));
    }
}
