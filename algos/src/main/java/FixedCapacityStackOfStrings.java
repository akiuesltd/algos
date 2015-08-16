/**
 * @author aks
 * @since 15/08/15
 */
public class FixedCapacityStackOfStrings {

    private final String[] stack;
    private int ptr = 0;

    public FixedCapacityStackOfStrings(int n) {
        this.stack = new String[n];
    }

    public static void main(String[] args) {
        FixedCapacityStackOfStrings stack = new FixedCapacityStackOfStrings(10);
        String input = "to be or not to - be - - that - - - is";
        for(String token : input.split(" ")) {
            if (token.equals("-")) {
                System.out.print(stack.pop() + " ");
            } else {
                stack.push(token);
            }
        }
    }

    public void push(String str) {
        stack[ptr++] = str;
    }

    public String pop() {
        return stack[--ptr];
    }

    public boolean isEmpty() {
        return ptr == 0;
    }
}
