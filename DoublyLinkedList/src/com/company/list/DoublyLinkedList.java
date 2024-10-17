package com.company.list;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class DoublyLinkedList<T extends Object> {
    void addHeadNode(ListNode<T> node, ListNode<T> head) {
        node.next = this.head;
        this.head.previous = node;
        this.head = node;
    }

    private class ListNode<E extends T> {
        private E item;
        private ListNode<E> next;
        private ListNode<E> previous;

        ListNode(E item) {
            this.item = item;
        }
    }

    private ListNode<T> head;
    private ListNode<T> tail;
    private int size;

    public void addFirst(T element) {
        ListNode<T> newHead = new ListNode<>(element);

        if (this.size == 0) {
            this.head = this.tail = newHead;
        } else if (this.size > 0) {
            addHeadNode(newHead, this.head);
        }

        size++;
    }

    public void addLast(T element) {
        ListNode<T> newTail = new ListNode<>(element);

        if (this.size == 0) {
            this.head = this.tail = newTail;
        } else if (this.size > 0) {
            newTail.previous = this.tail;
            this.tail.next = newTail;
            this.tail = newTail;
        }

        size++;
    }

    // Add element before the given item in the list
    public void addByItem(T item, T element) {
        ListNode<T> currNode = this.head;

        // If item is the head, make it the new head
        if (this.head.item == item) {
            ListNode<T> newHead = new ListNode<>(element);

            this.head.previous = newHead;
            newHead.next = this.head;
            this.head = newHead;

            this.size++;

            return;
        }

        for (int i = 0; i < this.size; i++) {
            if (currNode.item == item) {
                ListNode<T> newNode = new ListNode<>(element);

                newNode.previous = currNode.previous;
                newNode.next = currNode;
                currNode.previous.next = newNode;
                currNode.previous = newNode;

                this.size++;

                return;
            }

            currNode = currNode.next;
        }

        size++;
    }

    // Add element at given index
    public void addByIndex(int index, T element) {
        checkIndex(index);

        ListNode<T> newNode = new ListNode<>(element);

        if (index == 0) {
            this.head.previous = newNode;
            newNode.next = this.head;
            this.head = newNode;

            this.size++;

            return;
        }

        if (index == this.size - 1) {
            newNode.next = this.tail;
            newNode.previous = this.tail.previous;
            this.tail.previous.next = newNode;
            this.tail.previous = newNode;

            this.size++;

            return;
        }

        if (index <= this.size) {
            ListNode<T> currNode = this.head;

            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }

            newNode.next = currNode;
            newNode.previous = currNode.previous;
            currNode.previous.next = newNode;
            currNode.previous = newNode;
        } else {
            ListNode<T> currNode = this.tail;

            for (int i = this.size - 1; i > index; i--) {
                currNode = currNode.previous;
            }

            newNode.next = currNode;
            newNode.previous = currNode.previous;
            currNode.previous.next = newNode;
            currNode.previous = newNode;
        }

        size++;
    }

    public T removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException("List is empty!");
        }

        ListNode<T> removedNode = this.head;
        
        if (this.size == 1) {
            this.head = this.tail = null;

            this.size--;
            return removedNode.item;
        }

        this.head = this.head.next;
        this.head.previous = null;

        this.size--;
        return removedNode.item;
    }

    public T removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException("List is empty!");
        }

        ListNode<T> removedNode = this.tail;

        if (this.size == 1) {
            this.head = this.tail = null;

            this.size--;
            return removedNode.item;
        }

        this.tail = this.tail.previous;
        this.tail.next = null;

        this.size--;

        return removedNode.item;
    }

    public void removeByElement(T element) {
        ListNode<T> currNode = this.head;

        // Check if head is the remove node
        if (this.head.item == element) {
            this.head = currNode.next;

            currNode.next.previous = null;
            currNode.next = null;

            this.size--;
            return;
        }

        for (int i = 0; i <= this.size - 1; i++) {
            // If it gets to last iteration, then its the tail
            if (i == this.size - 1) {
                this.tail = currNode.previous;

                currNode.previous.next = null;
                currNode.previous = null;

                size--;
                return;
            }

            if (currNode.item == element) {
                currNode.previous.next = currNode.next;
                currNode.next.previous = currNode.previous;

                currNode.next = null;
                currNode.previous = null;

                this.size--;
                return;
            }

            currNode = currNode.next;
        }

        // If code reaches here, then it didnt find such element to remove
        throw new NoSuchElementException("No such element exists!");
    }

    public void removeByIndex(int index) {
        checkIndex(index);

        // Remove head
        if (index == 0) {
            ListNode<T> newHead = this.head.next;

            this.head.next.previous = null;
            this.head.next = null;

            this.head = newHead;

            this.size--;

            return;
        }

        // Remove tail
        if (index == this.size - 1) {
            ListNode<T> newTail = this.tail.previous;

            this.tail.previous.next = null;
            this.tail.previous = null;

            this.tail = newTail;

            this.size--;

            return;
        }

        if (index <= this.size) {
            ListNode<T> currNode = this.head;

            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }

            currNode.previous.next = currNode.next;
            currNode.next.previous = currNode.previous;
            currNode.next = null;
            currNode.previous = null;
        } else {
            ListNode<T> currNode = this.tail;

            for (int i = this.size - 1; i > index; i--) {
                currNode = currNode.previous;
            }

            currNode.previous.next = currNode.next;
            currNode.next.previous = currNode.previous;
            currNode.next = null;
            currNode.previous = null;
        }

        this.size--;
    }

    // Get element by index
    public T get(int index) {
        checkIndex(index);

        // If index is first, return head
        //TODO: why cast to E when item is E?
        if (index == 0) {
            return this.head.item;
        }

        // If index is last, return tail
        if (index == this.size - 1) {
            return this.tail.item;
        }

        ListNode<T> currNode = null;
        if (index <= this.size / 2) {
            currNode = this.head;

            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }
        } else {
            currNode = this.tail;

            for (int i = this.size - 1; i > index; i--) {
                currNode = currNode.previous;
            }
        }

        return currNode.item;
    }

    // Validate index
    private void checkIndex(int index) {
        if (index >= this.size || index < 0) {
            throw new ArrayIndexOutOfBoundsException("index doesn't exist!");
        }
    }

    public void forEach(Consumer<T> consumer) {
        ListNode<T> currNode = this.head;

        while (currNode != null) {
            consumer.accept(currNode.item);
            currNode = currNode.next;
        }
    }

    public T[] toArray() {
        T[] array = (T[]) Array.newInstance(this.head.item.getClass(), this.size);

        ListNode<T> currNode = this.head;
        for (int i = 0; i < this.size; i++) {
            array[i] = currNode.item;

            currNode = currNode.next;
        }

        return array;
    }
}