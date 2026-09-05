package implementations;

import interfaces.Solvable;
import java.util.ArrayDeque;

public class BalancedParentheses implements Solvable {

    private String parentheses;

    public BalancedParentheses(String parentheses) {
        this.parentheses = parentheses;
    }

    @Override
    public Boolean solve() {
        ArrayDeque<Character> stack = new ArrayDeque<>();

        for (int idx = 0; idx < this.parentheses.length(); idx++) {
            char curr = this.parentheses.charAt(idx);

            if (isOpeningParenthesis(curr)) {
                stack.push(curr);

            } else if (isClosingParenthesis(curr)) {
                if (stack.isEmpty())
                    return false;

                char opening = stack.pop();

                if (!areMatching(opening, curr))
                    return false;
            }
        }

        return stack.isEmpty();
    }

    private boolean isOpeningParenthesis(char symbol) {
        return symbol == '(' || symbol == '{' || symbol == '[';
    }

    private boolean isClosingParenthesis(char symbol) {
        return symbol == ')' || symbol == '}' || symbol == ']';
    }

    private boolean areMatching(char opening, char closing) {

        return  opening == '(' && closing == ')' ||
                opening == '{' && closing == '}' ||
                opening == '[' && closing == ']';

    }
}