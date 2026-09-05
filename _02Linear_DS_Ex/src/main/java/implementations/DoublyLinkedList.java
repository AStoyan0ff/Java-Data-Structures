package implementations;

import interfaces.LinkedList;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class DoublyLinkedList<E> implements LinkedList<E> {

    private Node<E> head;
    private Node<E> tail;
    private int size;

    private static class Node<E> {

        private E element;
        private Node<E> previous;
        private Node<E> next;

        private Node(E element, Node<E> previous, Node<E> next) {
            this.element = element;
            this.previous = previous;
            this.next = next;

            if (previous != null)
                previous.next = this;

            if (next != null)
                next.previous = this;
        }
    }

    public DoublyLinkedList() {
    }

    @Override
    public void addFirst(E element) {
        Node<E> newNode = new Node<>(element, null, this.head);

        this.head = newNode;

        if (this.tail == null)
            this.tail = newNode;

        this.size++;
    }

    @Override
    public void addLast(E element) {
        Node<E> newNode = new Node<>(element, this.tail, null);

        this.tail = newNode;

        if (this.head == null)
            this.head = newNode;

        this.size++;
    }

    @Override
    public E removeFirst() {
        ensureNotEmpty();

        E element = this.head.element;
        Node<E> oldHead = this.head;

        if (this.size == 1) {
            this.head = null;
            this.tail = null;

        } else {
            this.head = this.head.next;
            this.head.previous = null;
            oldHead.next = null;
        }

        this.size--;

        return element;
    }

    @Override
    public E removeLast() {
        ensureNotEmpty();

        E element = this.tail.element;
        Node<E> oldTail = this.tail;

        if (this.size == 1) {
            this.head = null;
            this.tail = null;

        } else {
            this.tail = this.tail.previous;
            this.tail.next = null;
            oldTail.previous = null;
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

        if (this.isEmpty())
            throw new IllegalStateException("The linked list is empty");
    }
}