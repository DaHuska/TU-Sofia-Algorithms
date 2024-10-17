#include <stdio.h>
#include <malloc.h>
#include <stdlib.h>

typedef struct LinkNode {
    int item;
    struct LinkNode* next;
    struct LinkNode* previous;
} LinkNode;

typedef struct DoublyLinkedList {
    struct LinkNode* head;
    struct LinkNode* tail;
    int size;

    void (*addFirst)(int, struct DoublyLinkedList*);
    void (*addLast)(int, struct DoublyLinkedList*);
    void (*addByItem)(int, int, struct DoublyLinkedList*);
    void (*addByIndex)(int, int, struct DoublyLinkedList*);
    void (*removeFirst)(struct DoublyLinkedList*);
    void (*removeLast)(struct DoublyLinkedList*);
    void (*removeByItem)(int, struct DoublyLinkedList*);
    void (*removeByIndex)(int, struct DoublyLinkedList*);
    int (*getByIndex)(int, struct DoublyLinkedList*);
    int (*getByItem)(int, struct DoublyLinkedList*);
    int* (*toArray)(struct DoublyLinkedList*);
} DoublyLinkedList;

void addNode(LinkNode* node, LinkNode* currNode) {
    node->next = currNode;
    node->previous = currNode->previous;
    currNode->previous->next = node;
    currNode->previous = node;
}

void addHeadNode(LinkNode* node, DoublyLinkedList* list) {
    node->next = list->head;
    list->head->previous = node;
    list->head = node;
}

void removeNode(LinkNode* node) {
    node->previous->next = node->next;
    node->next->previous = node->previous;

    free(node);
    node = NULL;
}

void removeTailNode(DoublyLinkedList* list) {
    list->tail = list->tail->previous;

    free(list->tail->next);
    list->tail->next = NULL;

}

void removeHeadNode(DoublyLinkedList* list) {
    list->head = list->head->next;

    free(list->head->previous);
    list->head->previous = NULL;
}

void validateIndex(int index, int listSize) {
    if (index < 0) {
        printf("Index cant be negative!\n");
        exit(1);
    } else if (index > listSize - 1) {
        printf("Index out of bounds!\n");
        exit(1);
    }
}

void addFirst(int element, DoublyLinkedList* list) {
    LinkNode* newNode;
    newNode = malloc(sizeof(LinkNode));

    if (newNode == NULL) {
        printf("Couldn't allocate memory!");
        exit(1);
    }

    newNode->item = element;

    if (list->size == 0) {
        list->head = list->tail = newNode;
    } else {
        addHeadNode(newNode, list);
    }

    list->size += 1;
}

void addLast(int element, DoublyLinkedList* list) {
    LinkNode* newNode;
    newNode = malloc(sizeof(LinkNode));

    if (newNode == NULL) {
        printf("Couldn't allocate memory!");
        exit(1);
    }

    newNode->item = element;

    if (list->size == 0) {
        list->head = list->tail = newNode;
    } else {
        addNode(newNode, list->tail);
    }

    list->size += 1;
}

void addByItem(int item, int element, DoublyLinkedList* list) {
    LinkNode* newNode;
    newNode = malloc(sizeof(LinkNode));

    if (newNode == NULL) {
        printf("Couldn't allocate memory!");
        exit(1);
    }

    newNode->item = element;

    LinkNode* currNode = list->head;

    for (int i = 0; i < list->size; i++) {
        // If the item is the head of the list
        if (currNode->item == item && i == 0) {
            addHeadNode(newNode, list);

            list->size += 1;
            return;
        } else if (currNode->item == item) {
            addNode(newNode, currNode);

            list->size += 1;
            return;
        }

        currNode = currNode->next;
    }

    printf("No such item found!\n");
}

void addByIndex(int index, int element, DoublyLinkedList* list) {
    validateIndex(index, list->size);

    LinkNode* newNode;
    newNode = malloc(sizeof(LinkNode));

    if (newNode == NULL) {
        printf("Couldn't allocate memory!");
        exit(1);
    }

    newNode->item = element;

    // Check if index is at head
    if (index == 0) {
        addHeadNode(newNode, list);

        list->size += 1;
        return;
    }

    // Check if index is at tail
    if (index == list->size - 1) {
        addNode(newNode, list->tail);

        list->size += 1;
        return;
    }

    LinkNode* currNode;
    // If index is in first half of the list
    if (list->size / 2 >= index) {
        currNode  = list->head;

        for (int i = 0; i <= index; i++) {
            currNode = currNode->next;
        }

        addNode(newNode, currNode);

        list->size += 1;
        return;
        // If index is in second half of the list
    } else {
        currNode = list->tail;

        for (int i = list->size - 1; i >= index; i--) {
            currNode = currNode->previous;
        }

        addNode(newNode, currNode);

        list->size += 1;
    }
}

void removeFirst(DoublyLinkedList* list) {
    if (list->size == 1) {
        free(list->head);
        list->head = list->tail = NULL;

        list->size -= 1;
        return;
    }

    removeHeadNode(list);

    list->size -= 1;
}

void removeLast(DoublyLinkedList* list) {
    if (list->size == 1) {
        free(list->tail);
        list->tail = list->head = NULL;

        list->size -= 1;
        return;
    }

    removeTailNode(list);

    list->size -= 1;
}

void removeByItem(int item, DoublyLinkedList* list) {
    // Check if the item is the onliest element
    if (list->head->item == item && list->size == 1) {
        free(list->head);
        list->head = list->tail = NULL;

        list->size -= 1;
        return;
    }
    // Check if the head is to be removed
    if (list->head->item == item) {
        removeHeadNode(list);

        list->size -= 1;
        return;
    }

    LinkNode* currNode;
    currNode = list->head;

    for (int i = 0; i <= list->size - 1; i++) {
        // Check if the tail is to be removed
        if (currNode->item == item && i == list->size - 1) {
            removeTailNode(list);

            list->size -= 1;
            return;
        }
        if (currNode->item == item) {
            removeNode(currNode);

            list->size -= 1;
            return;
        }

        currNode = currNode->next;
    }
}

void removeByIndex(int index, DoublyLinkedList* list) {
    validateIndex(index, list->size);

    // Check if index is at head
    if (index == 0) {
        removeHeadNode(list);

        list->size -= 1;
        return;
    }

    // Check if index is at tail
    if (index == list->size - 1) {
        removeTailNode(list);

        list->size -= 1;
        return;
    }

    LinkNode* currNode;
    // If index is in first half of the list
    if (list->size / 2 >= index) {
        currNode = list->head->next;

        for (int i = 1; i <= index; i++) {
            if (i == index) {
                removeNode(currNode);

                list->size -= 1;
                return;
            }

            currNode = currNode->next;
        }
    // If index is in second half of the list
    } else {
        currNode = list->tail->previous;

        for (int i = list->size - 2; i >= index; i--) {
            if (i == index) {
                removeNode(currNode);

                list->size -= 1;
                return;
            }

            currNode = currNode->previous;
        }
    }
}

int getByIndex(int index, DoublyLinkedList* list) {
    validateIndex(index, list->size);

    // If index is in first half of the list
    LinkNode* currNode;
    if (list->size / 2 >= index) {
        currNode = list->head;

        for (int i = 0; i < index; i++) {
            currNode = currNode->next;
        }
        // If index is in second half of the list
    } else {
        currNode = list->tail;

        for (int i = list->size - 1; i > index; i--) {
            currNode = currNode->previous;
        }
    }
    return currNode->item;
}

int getByItem(int item, DoublyLinkedList* list) {
    LinkNode* currNode;
    currNode = list->head;

    for (int i = 0; i < list->size; i++) {
        if (currNode->item == item) {
            return currNode->item;
        }

        currNode = currNode->next;
    }

    printf("No such element found!\n");
    return -1;
}

int* toArray(DoublyLinkedList* list) {
    int* items;
    items = malloc(list->size * sizeof(int));

    if (items == NULL) {
        printf("Couldn't allocate memory!");
        exit(1);
    }

    LinkNode* currNode;
    currNode = list->head;

    for (int i = 0; i <= list->size - 1; i++) {
        int num = currNode->item;
        items[i] = num;

        currNode = currNode->next;
    }

    return items;
}

int main(void) {
    DoublyLinkedList doublyLinkedList;

    doublyLinkedList.addFirst = &addFirst;
    doublyLinkedList.addLast = &addLast;
    doublyLinkedList.addByItem = &addByItem;
    doublyLinkedList.addByIndex = &addByIndex;
    doublyLinkedList.removeFirst = &removeFirst;
    doublyLinkedList.removeLast = &removeLast;
    doublyLinkedList.removeByItem = &removeByItem;
    doublyLinkedList.removeByIndex = &removeByIndex;
    doublyLinkedList.getByIndex = &getByIndex;
    doublyLinkedList.getByItem = &getByItem;
    doublyLinkedList.toArray = &toArray;

    doublyLinkedList.addFirst(5, &doublyLinkedList);
    doublyLinkedList.addFirst(10, &doublyLinkedList);
    doublyLinkedList.addFirst(20, &doublyLinkedList);
    doublyLinkedList.addFirst(30, &doublyLinkedList);
    doublyLinkedList.addFirst(40, &doublyLinkedList);
//    doublyLinkedList.removeFirst(&doublyLinkedList);

//    printf("%d", doublyLinkedList.getByIndex(5, &doublyLinkedList));
//    doublyLinkedList.addLast(70, &doublyLinkedList);
//    doublyLinkedList.addByItem(10, 20, &doublyLinkedList);
//    doublyLinkedList.addByItem(5, 30, &doublyLinkedList);
//    doublyLinkedList.addByIndex(2, 50, &doublyLinkedList);
//    doublyLinkedList.addByIndex(2, 60, &doublyLinkedList);

//    doublyLinkedList.removeFirst(&doublyLinkedList);
//    doublyLinkedList.removeLast(&doublyLinkedList);
//    doublyLinkedList.removeByItem(5, &doublyLinkedList);
//    doublyLinkedList.removeByIndex(7, &doublyLinkedList);
//    printf("%d", doublyLinkedList.getByItem(11, &doublyLinkedList));

//    int* items;
//    items = doublyLinkedList.toArray(&doublyLinkedList);

    return 0;
}
