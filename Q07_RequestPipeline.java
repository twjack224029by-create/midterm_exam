import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

public class Q07_RequestPipeline {

    public static boolean isBalanced(String text) {
        if (text == null) {
            return false;
        }
        if (text.isEmpty()) {
            return true;
        }

        Deque<Character> stack = new ArrayDeque<>();

        for (char ch : text.toCharArray()) {
            if (ch == '(' || ch == '[' || ch == '{') {
                stack.push(ch);
            } else if (ch == ')' || ch == ']' || ch == '}') {
                if (stack.isEmpty()) {
                    return false;
                }
                char open = stack.pop();
                if (!isMatchingPair(open, ch)) {
                    return false;
                }
            }
        }

        return stack.isEmpty();
    }

    private static boolean isMatchingPair(char open, char close) {
        return (open == '(' && close == ')')
            || (open == '[' && close == ']')
            || (open == '{' && close == '}');
    }

    public static List<String> process(String[] commands) {
        if (commands == null) {
            return Collections.emptyList();
        }

        Deque<String> normalQueue = new ArrayDeque<>();
        Deque<String> urgentQueue = new ArrayDeque<>();
        List<String> result = new ArrayList<>();

        for (String cmd : commands) {
            if (cmd == null || cmd.trim().isEmpty()) {
                continue;
            }

            String[] parts = cmd.trim().split("\\s+");
            String action = parts[0];

            if ("PROCESS".equals(action) && parts.length == 1) {
                if (!urgentQueue.isEmpty()) {
                    result.add(urgentQueue.poll());
                } else if (!normalQueue.isEmpty()) {
                    result.add(normalQueue.poll());
                } else {
                    result.add("EMPTY");
                }
            } else if (("NORMAL".equals(action) || "URGENT".equals(action)) && parts.length == 2) {
                String id = parts[1];
                if ("NORMAL".equals(action)) {
                    normalQueue.offer(id);
                } else {
                    urgentQueue.offer(id);
                }
            }
        }

        return result;
    }
}
