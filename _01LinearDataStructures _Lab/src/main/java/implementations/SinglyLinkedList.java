package implementations;

import interfaces.LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SinglyLinkedList<E> implements LinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element);

        if (isEmpty()) {
            this.head = newNode;
            this.tail = newNode;

        } else {
            newNode.next = this.head;
            this.head = newNode;
        }

        this.size++;
    }

    @Override
    public void addLast(E element) {
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
    public E removeFirst() {
        ensureNotEmpty();

        E element = this.head.element;
        this.head = this.head.next;

        this.size--;

        if (isEmpty())
            this.tail = null;

        return element;
    }

    @Override
    public E removeLast() {
        ensureNotEmpty();

        E element = this.tail.element;

        if (this.size == 1) {
            this.head = null;
            this.tail = null;

        } else {
            Node<E> current = this.head;

            while (current.next != this.tail)
                current = current.next;

            current.next = null;
            this.tail = current;
        }

        this.size--;

        return element;
    }

    @Override
    public E getFirst() {
        ensureNotEmpty();

        return this.head.element;
    }

    @Override
    public E getLast() {
        ensureNotEmpty();

        return this.tail.element;
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
            throw new IllegalStateException("The linked list is empty.");
    }

    private static class Node<E> {

        private final E element;
        private Node<E> next;

        private Node(E element) {
            this.element = element;
        }
    }
}