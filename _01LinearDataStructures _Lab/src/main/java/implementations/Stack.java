package implementations;

import interfaces.AbstractStack;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Stack<E> implements AbstractStack<E> {

    private Node<E> top;
    private int size;

    @Override
    public void push(E element) {

        Node<E> newNode = new Node<>(element);
        newNode.next = this.top;

        this.top = newNode;
        this.size++;
    }

    @Override
    public E pop() {
        ensureNotEmpty();

        E element = this.top.element;
        this.top = this.top.next;

        this.size--;

        return element;
    }

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.top.element;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {

        return new Iterator<E>() {

            private Node<E> current = top;

            @Override
            public boolean hasNext() {
                return this.current != null;
            }

            @Override
            public E next() {

                if (!hasNext())
                    throw new NoSuchElementException();

                E element = this.current.element;
                this.current = this.current.next;

                return element;
            }
        };
    }

    private void ensureNotEmpty() {

        if (isEmpty())
            throw new IllegalStateException("The stack is empty.");}

    private static class Node<E> {

        private final E element;
        private Node<E> next;

        private Node(E element) {
            this.element = element;
        }
    }
}