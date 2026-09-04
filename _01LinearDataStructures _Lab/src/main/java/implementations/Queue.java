package implementations;

import interfaces.AbstractQueue;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Queue<E> implements AbstractQueue<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public void offer(E element) {

        Node<E> newNode = new Node<>(element);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;

        } else {
            this.tail.next = newNode;
            this.tail = newNode;
        }

        this.size++;
    }

    @Override
    public E poll() {
        ensureNotEmpty();
        E element = this.head.element;

        this.head = this.head.next;
        this.size--;

        if (isEmpty())
            this.tail = null;

        return element;
    }

    @Override
    public E peek() {
        ensureNotEmpty();
        return this.head.element;
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

            private Node<E> current = head;

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
            throw new IllegalStateException("The queue is empty.");
    }

    private static class Node<E> {

        private final E element;
        private Node<E> next;

        private Node(E element) {
            this.element = element;
        }
    }
}