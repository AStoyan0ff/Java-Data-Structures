package implementations;

import interfaces.List;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class ArrayList<E> implements List<E> {

    private static final int INITIAL_CAPACITY = 4;

    private E[] elements;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        this.elements = (E[]) new Object[INITIAL_CAPACITY];
    }

    @Override
    public boolean add(E element) {

        ensureCapacity();

        this.elements[this.size] = element;
        this.size++;

        return true;
    }

    @Override
    public boolean add(int index, E element) {

        checkIndexForAdd(index);
        ensureCapacity();

        System.arraycopy(
                this.elements,
                index,
                this.elements,
                index + 1,
                this.size - index
        );

        this.elements[index] = element;
        this.size++;

        return true;
    }

    @Override
    public E get(int index) {
        checkIndex(index);

        return this.elements[index];
    }

    @Override
    public E set(int index, E element) {
        checkIndex(index);

        E previousElement = this.elements[index];
        this.elements[index] = element;

        return previousElement;
    }

    @Override
    public E remove(int index) {

        checkIndex(index);

        E removedElement = this.elements[index];

        int elementsToMove = this.size - index - 1;

        if (elementsToMove > 0) {
            System.arraycopy(
                    this.elements,
                    index + 1,
                    this.elements,
                    index,
                    elementsToMove
            );
        }

        this.size--;
        this.elements[this.size] = null;

        shrinkIfNeeded();

        return removedElement;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public int indexOf(E element) {

        for (int index = 0; index < this.size; index++) {
            if (Objects.equals(this.elements[index], element)) return index;
        }

        return -1;
    }

    @Override
    public boolean contains(E element) {
        return indexOf(element) != -1;
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

                if (!hasNext()) {
                    throw new NoSuchElementException();
                }

                return elements[this.index++];
            }
        };
    }

    private void ensureCapacity() {

        if (this.size == this.elements.length) {

            this.elements = Arrays.copyOf(
                    this.elements,
                    this.elements.length * 2
            );
        }
    }

    private void shrinkIfNeeded() {

        if (this.elements.length > INITIAL_CAPACITY
                && this.size < this.elements.length / 3) {

            int newCapacity = Math.max(
                    INITIAL_CAPACITY,
                    this.elements.length / 2
            );

            this.elements = Arrays.copyOf(
                    this.elements,
                    newCapacity
            );
        }
    }

    private void checkIndex(int index) {

        if (index < 0 || index >= this.size) {

            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + this.size
            );
        }
    }

    private void checkIndexForAdd(int index) {

        if (index < 0 || index > this.size) {

            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", Size: " + this.size
            );
        }
    }
}