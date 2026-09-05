package implementations;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ReversedList<E> implements Iterable<E> {

    private static final int INITIAL_CAPACITY = 2;

    private Object[] elements;
    private int size;

    public ReversedList() {
        this.elements = new Object[INITIAL_CAPACITY];
    }

    public void add(E element) {
        ensureCapacity();

        this.elements[this.size] = element;
        this.size++;
    }

    public int size() {
        return this.size;
    }

    public int capacity() {
        return this.elements.length;
    }

    public E get(int index) {
        checkIndex(index);
        return elementAt(index);
    }

    public E removeAt(int index) {
        checkIndex(index);

        int actualIndex = getActualIndex(index);

        @SuppressWarnings("unchecked")
        E removedElement = (E) this.elements[actualIndex];

        int elementsToMove = this.size - actualIndex - 1;

        if (elementsToMove > 0)
            System.arraycopy(this.elements, actualIndex + 1, this.elements, actualIndex, elementsToMove);

        this.size--;
        this.elements[this.size] = null;

        return removedElement;
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

        Object[] newElements = new Object[this.elements.length * 2];


        System.arraycopy(this.elements, 0, newElements, 0, this.elements.length);

        this.elements = newElements;
    }

    private E elementAt(int index) {
        int actualIndex = getActualIndex(index);

        @SuppressWarnings("unchecked")
        E element = (E) this.elements[actualIndex];

        return element;
    }

    private int getActualIndex(int index) {
        return this.size - 1 - index;
    }

    private void checkIndex(int index) {

        if (index < 0 || index >= this.size)
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + this.size);
    }
}