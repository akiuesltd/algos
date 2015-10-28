package com.akieus.algos;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

public class GraphTemp {

    public static void main(String[] args) {
//        printGraphDF(buildPersonWithFriends(), 4);
        printGraphBF(buildPersonWithFriends(), 3);
//        printGraphDFNonRecrusive(buildPersonWithFriends(), 3);
    }

    private static void print(Collection<Person> collection) {
        for (Person t : collection) {
            System.out.print(t.id + ", ");
        }
        System.out.println();
    }

    private static void printGraphDF(Person root, int depth) {
        Set<Person> set = new LinkedHashSet<>();
        traverseGraphDF(root, set, depth);
        print(set);
    }

    private static void printGraphBF(Person root, int depth) {
        if (depth < 1) {
            throw new IllegalArgumentException("depth must be > 0");
        }
        Set<Person> set = new LinkedHashSet<>();
        set.add(root);
        traverseGraphBF(root, set, depth - 1);
        print(set);
    }

    private static void traverseGraphDF(Person node, Set<Person> set, int depth) {
        if (node == null || depth < 1) {
            return;
        }
        for (Person child : node.getFriends()) {
            if (!set.contains(child)) {
                traverseGraphDF(child, set, depth - 1);
            }
        }
    }

    private static void traverseGraphBF(Person node, Set<Person> set, int depth) {
        if (node == null || depth < 1) {
            return;
        }
        set.addAll(node.getFriends());
        for (Person child : node.getFriends()) {
            traverseGraphBF(child, set, depth - 1);
        }
    }

    public static Person buildPersonWithFriends() {
        Person one = new Person(1);
        Person two = new Person(2);
        Person three = new Person(3);

        one.addFriend(two).addFriend(three);
        two.addFriend(4).addFriend(5).addFriend(one).addFriend(three);
        three.addFriend(6).addFriend(one).addFriend(two);

        return one;
    }


    public static class Person {
        private int id;
        private List<Person> friends;

        public Person(int id) {
            this(id, new LinkedList<>());
        }

        public Person(int id, List<Person> friends) {
            checkNotNull(friends);
            this.id = id;
            this.friends = friends;
        }

        public Person addFriend(int id) {
            return addFriend(new Person(id));
        }

        public Person addFriend(Person friend) {
            friends.add(friend);
            return this;
        }

        public List<Person> getFriends() {
            return friends;
        }

        @Override
        public String toString() {
            StringBuilder sb = new StringBuilder().append(id);
            if (!friends.isEmpty()) {
                sb.append(": ");
                for (Person friend : friends) {
                    sb.append(friend.id).append(", ");
                }
            }
            return sb.toString();
        }
    }
}
