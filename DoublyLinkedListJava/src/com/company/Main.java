package com.company;

import com.company.list.DoublyLinkedList;

public class Main {

    public static void main(String[] args) {
        DoublyLinkedList<Integer> number = new DoublyLinkedList<>();

        number.addFirst(2);
        number.addFirst(5);
        number.addFirst(8);
        number.addFirst(10);
        number.addFirst(12);

        System.out.println(number.get(2));
        number.forEach(e -> System.out.println(e));

//        number.addByItem(10, 20);
//        number.addByIndex(3, 20);
//        number.removeByElement(2);
//        number.removeByIndex(2);

//        Integer[] integers = number.toArray();
//
//        for (Integer num : integers) {
//            System.out.print(num + " ");
//        }

//        DoublyLinkedList<String> text = new DoublyLinkedList<>();
//
//        text.addFirst("cool");
//        text.addFirst("is");
//        text.addFirst("alex");

//        System.out.println(text.removeLast());
//        System.out.println(text.get(2));
//        System.out.println(text.removeFirst());
//        text.addFirst("first");
//        text.addLast("last");
//        text.addByItem("alex", "beforeAlex");
//        text.addByIndex(5, "fifthIndex");

//        text.removeFirst();
//        text.removeLast();
//        text.removeByElement("last");
//        text.removeByIndex(2);

//        text.forEach(e -> System.out.print(e + " "));

//        String[] array = text.toArray();
//
//        for (int i = 0; i < array.length; i++) {
//            System.out.print(array[i] + " ");
//        }
    }
}
