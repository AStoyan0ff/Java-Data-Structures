package implementations;

import interfaces.Deque;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayDeque<E> implements Deque<E> {

    private static final int DEFAULT_CAPACITY = 7;

    private int head;
    private int tail;
    private int size;

    private Object[] elements;

    public ArrayDeque() {
        this.elements = new Object[DEFAULT_CAPACITY];

        this.head = this.elements.length / 2;
        this.tail = this.head;
    }

    @Override
    public void add(E element) {
        addLast(element);
    }

    @Override
    public void offer(E element) {
        addLast(element);
    }

    @Override
    public void addFirst(E element) {
        ensureCapacity();

        this.head = previousIndex(this.head);
        this.elements[this.head] = element;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        ensureCapacity();

        this.elements[this.tail] = element;
        this.tail = nextIndex(this.tail);
        this.size++;
    }

    @Override
    public void push(E element) {
        addLast(element);
    }

    @Override
    public void insert(int index, E element) {
        checkInsertIndex(index);
        ensureCapacity();

        for (int current = this.size; current > index; current--)
            setElement(current, elementAt(current - 1));

        setElement(index, element);

        this.tail = nextIndex(this.tail);
        this.size++;
    }

    @Override
    public void set(int index, E element) {
        checkIndex(index);
        setElement(index, element);
    }

    @Override
    public E peek() {

        if (isEmpty())
            return null;

        return elementAt(0);
    }

    @Override
    public E poll() {
        return removeFirst();
    }

    @Override
    public E pop() {
        return removeLast();
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return elementAt(index);
    }

    @Override
    public E get(Object object) {
        for (int index = 0; index < this.size; index++) {
            E element = elementAt(index);

            if (Objects.equals(element, object))
                return element;
        }

        return null;
    }

    @Override
    public E remove(int index) {
        checkIndex(index);

        E removedElement = elementAt(index);

        for (int current = index; current < this.size - 1; current++)
            setElement(current, elementAt(current + 1));

        this.tail = previousIndex(this.tail);
        this.elements[this.tail] = null;
        this.size--;

        return removedElement;
    }

    @Override
    public E remove(Object object) {
        for (int index = 0; index < this.size; index++) {

            if (Objects.equals(elementAt(index), object))
                return remove(index);
        }

        return null;
    }

    @Override
    public E removeFirst() {

        if (isEmpty())
            return null;

        E removedElement = elementAt(0);

        this.elements[this.head] = null;
        this.head = nextIndex(this.head);
        this.size--;

        if (isEmpty())
            this.tail = this.head;

        return removedElement;
    }

    @Override
    public E removeLast() {
        if (isEmpty())
            return null;

        this.tail = previousIndex(this.tail);

        @SuppressWarnings("unchecked")
        E removedElement = (E) this.elements[this.tail];

        this.elements[this.tail] = null;
        this.size--;

        if (isEmpty())
            this.head = this.tail;

        return removedElement;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int capacity() {
        return this.elements.length;
    }

    @Override
    public void trimToSize() {
        Object[] trimmedElements = new Object[this.size];

        for (int idx = 0; idx < this.size; idx++)
            trimmedElements[idx] = elementAt(idx);

        this.elements = trimmedElements;
        this.head = 0;
        this.tail = this.size;
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index;

            @Override
            public boolean hasNext() {
                return this.index < size;
            }

            @Override
            public E next() {

                if (!hasNext())
                    throw new NoSuchElementException();

                return elementAt(this.index++);
            }
        };
    }

    private void ensureCapacity() {
        if (this.size < this.elements.length)
            return;

        int newCapacity;

        if (this.elements.length == 0)
            newCapacity = 1;

        else
            newCapacity = this.elements.length * 2;

        Object[] newElements = new Object[newCapacity];
        int newHead = (newCapacity - this.size) / 2;

        for (int idx = 0; idx < this.size; idx++) {
            newElements[newHead + idx] = elementAt(idx);
        }

        this.elements = newElements;
        this.head = newHead;
        this.tail = (this.head + this.size) % this.elements.length;
    }

    private int physicalIndex(int logicalIndex) {
        return (this.head + logicalIndex) % this.elements.length;
    }

    private int nextIndex(int index) {
        return (index + 1) % this.elements.length;
    }

    private int previousIndex(int index) {
        return (index - 1 + this.elements.length) % this.elements.length;
    }

    @SuppressWarnings("unchecked")
    private E elementAt(int index) {
        return (E) this.elements[physicalIndex(index)];
    }

    private void setElement(int index, E element) {
        this.elements[physicalIndex(index)] = element;
    }

    private void checkIndex(int index) {

        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
    }

    private void checkInsertIndex(int index) {

        if (index < 0 || index > this.size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
    }
}