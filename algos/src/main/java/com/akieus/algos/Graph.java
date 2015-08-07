package com.akieus.algos;

import java.util.*;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by aks on 07/08/15.
 */
public class Graph {

    public static void main(String[] args) {
        printGraphDF(buildPersonWithFriends(), 3);
        printGraphBF(buildPersonWithFriends(), 3);
    }

    private static void print(Collection<Person> collection) {
        for (Person t : collection) {
            System.out.print(t.id + ", ");
        }
        System.out.println();
    }

    private static void printGraphDF(Person root, int depth) {
        List<Person> visited = new LinkedList<>();
        traverseDF(root, depth, visited);
        print(visited);
    }

    private static void printGraphBF(Person person, int depth) {
        print(traverseBF(person, depth));
    }

    private static void traverseDF(Person person, int depth, List<Person> visited) {
        if (depth == 0) {
            return;
        }

        if (!visited.contains(person)) {
            visited.add(person);
        }

        for (Person friend : person.getFriends()) {
            traverseDF(friend, depth - 1, visited);
        }
    }

    private static List<Person> traverseBF(Person person, int depth) {
        List<Person> visited = new LinkedList<>();
        if (depth == 0) {
            return visited;
        }

        LinkedList<PersonHolder> queue = new LinkedList<>();
        queue.add(new PersonHolder(person, 1));
        while (true) {
            if (queue.isEmpty()) {
                break;
            }

            PersonHolder next = queue.removeFirst();
            if (!visited.contains(next.person)) {
                visited.add(next.person);
            }
            if (next.depth < depth) {
                for (Person friend : next.person.getFriends()) {
                    if (!visited.contains(friend)) {
                        queue.add(new PersonHolder(friend, next.depth + 1));
                    }
                }
            }
        }


        return visited;
    }

    private static class PersonHolder {
        private final Person person;
        private final int depth;

        public PersonHolder(Person person, int depth) {
            this.person = person;
            this.depth = depth;
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
