package com.company.list;

import java.lang.reflect.Array;
import java.util.NoSuchElementException;
import java.util.function.Consumer;

public class DoublyLinkedList<T extends Object> {
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

    private void addHeadNode(ListNode<T> node) {
        node.next = this.head;
        this.head.previous = node;
        this.head = node;
    }

    private void addTailNode(ListNode<T> node) {
        node.previous = this.tail;
        this.tail.next = node;
        this.tail = node;
    }

    private void addNode(ListNode<T> newNode, ListNode<T> currNode) {
        newNode.previous = currNode.previous;
        newNode.next = currNode;
        currNode.previous.next = newNode;
        currNode.previous = newNode;
    }

    private void removeHeadNode() {
        this.head = this.head.next;
        this.head.previous = null;
    }

    private void removeTailNode() {
        this.tail = this.tail.previous;
        this.tail.next = null;
    }

    private void removeNode(ListNode<T> node) {
        node.previous.next = node.next;
        node.next.previous = node.previous;

        node.next = null;
        node.previous = null;
    }

    public void addFirst(T element) {
        ListNode<T> newHead = new ListNode<>(element);

        // Check if list is empty
        if (this.size == 0) {
            this.head = this.tail = newHead;
        } else if (this.size > 0) {
            addHeadNode(newHead);
        }

        size++;
    }

    public void addLast(T element) {
        ListNode<T> newTail = new ListNode<>(element);

        // Check if list is empty
        if (this.size == 0) {
            this.head = this.tail = newTail;
        } else if (this.size > 0) {
            addTailNode(newTail);
        }

        size++;
    }

    // Add element before the given item in the list
    public void addByItem(T item, T element) {
        ListNode<T> currNode = this.head;

        // If item is the head, make it the new head
        if (this.head.item == item) {
            ListNode<T> newHead = new ListNode<>(element);
            addHeadNode(newHead);

            this.size++;
            return;
        }

        for (int i = 0; i < this.size; i++) {
            if (currNode.item == item) {
                ListNode<T> newNode = new ListNode<>(element);
                addNode(newNode, currNode);

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

        // Check if index is at head
        if (index == 0) {
            addHeadNode(newNode);

            this.size++;
            return;
        }

        // Check if index is at tail
        if (index == this.size - 1) {
            addNode(newNode, this.tail);

            this.size++;
            return;
        }

        if (index <= this.size) {
            ListNode<T> currNode = this.head;

            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }

            addNode(newNode, currNode);
        } else {
            ListNode<T> currNode = this.tail;

            for (int i = this.size - 1; i > index; i--) {
                currNode = currNode.previous;
            }

            addNode(newNode, currNode);
        }

        size++;
    }

    public void removeFirst() {
        if (this.size == 0) {
            throw new NoSuchElementException("List is empty!");
        }

        ListNode<T> removedNode = this.head;

        // Check there is only 1 element in the list
        if (this.size == 1) {
            this.head = this.tail = null;

            this.size--;
            return;
        }

        removeHeadNode();

        this.size--;
    }

    public void removeLast() {
        if (this.size == 0) {
            throw new NoSuchElementException("List is empty!");
        }

        ListNode<T> removedNode = this.tail;

        // Check if there is only 1 element in the list
        if (this.size == 1) {
            this.head = this.tail = null;

            this.size--;
            return;
        }

        removeTailNode();

        this.size--;
    }

    public void removeByElement(T element) {
        // Check if head is the remove node
        if (this.head.item == element) {
            removeHeadNode();

            this.size--;
            return;
        }

        ListNode<T> currNode = this.head;
        for (int i = 0; i <= this.size - 1; i++) {
            // If it gets to last iteration, then its the tail
            if (i == this.size - 1) {
                removeTailNode();

                size--;
                return;
            }

            if (currNode.item == element) {
                removeNode(currNode);

                this.size--;
                return;
            }

            currNode = currNode.next;
        }

        // If code reaches here, then it didn't find such element to remove
        throw new NoSuchElementException("No such element exists!");
    }

    public void removeByIndex(int index) {
        checkIndex(index);

        // Remove head
        if (index == 0) {
            removeHeadNode();

            this.size--;
            return;
        }

        // Remove tail
        if (index == this.size - 1) {
            removeTailNode();

            this.size--;
            return;
        }

        if (index <= this.size) {
            ListNode<T> currNode = this.head;

            for (int i = 0; i < index; i++) {
                currNode = currNode.next;
            }

            removeNode(currNode);
        } else {
            ListNode<T> currNode = this.tail;

            for (int i = this.size - 1; i > index; i--) {
                currNode = currNode.previous;
            }

            removeNode(currNode);
        }

        this.size--;
    }

    // Get element by index
    public T get(int index) {
        checkIndex(index);

        // If index is first, return head
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