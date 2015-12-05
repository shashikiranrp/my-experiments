package org.kelvin.java8.stream;

/**
 * @author <a href="mailto:shasrp@yahoo-inc.com">Shashikiran</a>
 */
public class BinaryTreeReduction
{
    static class Node<T>
    {
        T data;
        Node<T> leftTree;
        Node<T> rightTree;

        public Node(T data, Node<T> leftTree, Node<T> rightTree)
        {
            this.data = data;
            this.leftTree = leftTree;
            this.rightTree = rightTree;
        }
    }
}
