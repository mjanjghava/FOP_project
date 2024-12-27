

import java.util.*;

public class ExpressionEvaluator {
    private final Map<String, Integer> variables;

    public ExpressionEvaluator(Map<String, Integer> variables) {
        this.variables = variables;
    }

    public int evaluate(String expr) {
        expr = expr.replace(";", "").trim();
        String[] tokens = expr.split(" ");

        if (tokens.length == 1) {
            String token = tokens[0];
            if (isNumeric(token)) {
                return Integer.parseInt(token);
            } else if (variables.containsKey(token)) {
                return variables.get(token);
            } else {
                throw new IllegalArgumentException("Unknown variable or invalid expression: " + token);
            }
        }

        Stack<Integer> values = new Stack<>();
        Stack<Character> operators = new Stack<>();

        for (String token : tokens) {
            if (isNumeric(token)) {
                values.push(Integer.parseInt(token));
            } else if (variables.containsKey(token)) {
                values.push(variables.get(token));
            } else if ("+-*/%".contains(token)) {
                while (!operators.isEmpty() && precedence(operators.peek()) >= precedence(token.charAt(0))) {
                    processStacks(values, operators);
                }
                operators.push(token.charAt(0));
            }
        }

        while (!operators.isEmpty()) {
            processStacks(values, operators);
        }

        if (values.isEmpty()) {
            throw new IllegalStateException("Invalid expression or empty stack: " + expr);
        }

        return values.pop();
    }

    public boolean evaluateCondition(String condition) {
        String[] parts = condition.split(" ");
        if (parts.length < 3) {
            throw new IllegalArgumentException("Invalid condition: " + condition);
        }

        StringBuilder leftExpr = new StringBuilder();
        StringBuilder rightExpr = new StringBuilder();
        int i = 0;

        while (i < parts.length && !isOperator(parts[i])) {
            leftExpr.append(parts[i]).append(" ");
            i++;
        }

        if (i >= parts.length) {
            throw new IllegalArgumentException("No valid operator found in condition: " + condition);
        }

        String operator = parts[i++];

        while (i < parts.length) {
            rightExpr.append(parts[i]).append(" ");
            i++;
        }

        int leftValue = evaluate(leftExpr.toString().trim());
        int rightValue = evaluate(rightExpr.toString().trim());

        return switch (operator) {
            case "<" -> leftValue < rightValue;
            case "<=" -> leftValue <= rightValue;
            case ">" -> leftValue > rightValue;
            case ">=" -> leftValue >= rightValue;
            case "==" -> leftValue == rightValue;
            case "!=" -> leftValue != rightValue;
            default -> throw new IllegalArgumentException("Invalid operator in condition: " + operator);
        };
    }

    private void processStacks(Stack<Integer> values, Stack<Character> operators) {
        if (values.size() < 2) {
            throw new IllegalStateException("Insufficient values in expression stack.");
        }

        int b = values.pop();
        int a = values.pop();
        char op = operators.pop();
        values.push(applyOperator(a, b, op));
    }

    private int applyOperator(int a, int b, char operator) {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> a / b;
            case '%' -> a % b;
            default -> throw new IllegalArgumentException("Invalid operator: " + operator);
        };
    }

    private boolean isNumeric(String str) {
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private boolean isOperator(String token) {
        return token.equals("<") || token.equals("<=") || token.equals(">") ||
                token.equals(">=") || token.equals("==") || token.equals("!=");
    }

    private int precedence(char operator) {
        return switch (operator) {
            case '+', '-' -> 1;
            case '*', '/', '%' -> 2;
            default -> -1;
        };
    }
}