package org.kelvin.java8.stream;

import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class BinaryTreeReduction
{
    public static class Node<T>
    {
        T data;
        Node<T> leftTree;
        Node<T> rightTree;
        Node<T> currentNodeInItr;

        public Node(T data)
        {
            this.data = data;
        }

        public Stream<Node<T>> stream()
        {
            return StreamSupport.stream(new Supplier<Spliterator<Node<T>>>()
                                        {
                                            @Override
                                            public Spliterator<Node<T>> get()
                                            {
                                                return new Spliterator<Node<T>>()
                                                {
                                                    @Override
                                                    public boolean tryAdvance(Consumer<? super Node<T>> action)
                                                    {
                                                        action.accept(Node.this);
                                                        return false;
                                                    }

                                                    @Override
                                                    public Spliterator<Node<T>> trySplit()
                                                    {
                                                        return null;
                                                    }

                                                    @Override
                                                    public long estimateSize()
                                                    {
                                                        return 0;
                                                    }

                                                    @Override
                                                    public int characteristics()
                                                    {
                                                        return Spliterator.ORDERED | Spliterator.IMMUTABLE;
                                                    }
                                                };
                                            }
                                        },
                    Spliterator.ORDERED | Spliterator.IMMUTABLE,
                    false);
        }
    }

    public static void main(String[] args)
    {
        final Consumer<Node<Integer>> nodeSumConsumer = (node) -> {

        };
        final Node<Integer> root = new Node<>(2);
        root.leftTree = new Node<>(1);
        root.rightTree = new Node<>(3);
        root.rightTree.rightTree = new Node<>(4);
        root.stream().forEach(nodeSumConsumer);
    }
}
