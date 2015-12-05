package org.kelvin.java8.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class InfiniteStream<T> implements Spliterator<T>
{

    final Supplier<T> supplier;

    public InfiniteStream(final Supplier<T> supplier)
    {
        this.supplier = supplier;
    }

    @Override
    public boolean tryAdvance(Consumer<? super T> action)
    {
        action.accept(supplier.get());
        return true;
    }

    @Override
    public Spliterator<T> trySplit()
    {
        return null;
    }

    @Override
    public long estimateSize()
    {
        return -1;
    }

    @Override
    public int characteristics()
    {
        return Spliterator.ORDERED | Spliterator.NONNULL | Spliterator.IMMUTABLE;
    }
}
