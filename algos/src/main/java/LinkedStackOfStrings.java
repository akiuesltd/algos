/**
 * @author aks
 * @since 15/08/15
 */
public class LinkedStackOfStrings {

    public static void main(String[] args) {
        LinkedStackOfStrings stack = new LinkedStackOfStrings();
        String input = "to be or not to - be - - that - - - is";
        for(String token : input.split(" ")) {
            if (token.equals("-")) {
                System.out.print(stack.pop() + " ");
            } else {
                stack.push(token);
            }
        }

//        while(!stack.isEmpty()) {
//            System.out.print(stack.pop() + " ");
//        }
    }

    private Node first = null;
    public LinkedStackOfStrings() {
    }

    public void push(String str) {
        first = new Node(str, first);
    }

    public String pop() {
        if (isEmpty()) {
            throw new IllegalStateException("Empty");
        }
        String str = first.content;
        first = first.next;
        return str;
    }

    public boolean isEmpty() {
        return first == null;
    }

    private class Node {
        private String content;
        private Node next;
        private Node(String content) {
            this.content = content;
        }
        private Node(String content, Node next) {
            this.content = content;
            this.next = next;
        }
    }
}
