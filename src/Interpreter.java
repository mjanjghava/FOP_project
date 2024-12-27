import java.util.*;

public class Interpreter {
    private final Map<String, Integer> variables = new HashMap<>();

    public void eval(String program) {
        String[] lines = program.split("\n");
        int i = 0;
        while (i < lines.length) {
            String line = lines[i].trim();

            if (line.startsWith("while")) {
                i = new LoopHandler(this).handleWhile(line, lines, i);
            } else if (line.startsWith("if")) {
                i = new ConditionalHandler(this).handleIf(line, lines, i);
            } else if (line.startsWith("print")) {
                handlePrint(line); // Updated handlePrint method is used here
            } else if (line.contains("=")) {
                handleAssignment(line);
            } else if (!line.isEmpty()) {
                throw new IllegalArgumentException("Invalid syntax: " + line);
            }
            i++;
        }
    }

    public int evaluateExpression(String expr) {
        return new ExpressionEvaluator(variables).evaluate(expr);
    }

    public boolean evaluateCondition(String condition) {
        return new ExpressionEvaluator(variables).evaluateCondition(condition);
    }

    public Map<String, Integer> getVariables() {
        return variables;
    }

    private void handleAssignment(String line) {
        String[] parts = line.split("=");
        if (parts.length != 2) {
            throw new IllegalArgumentException("Invalid assignment: " + line);
        }

        String varName = parts[0].trim();
        int value = evaluateExpression(parts[1].trim());
        variables.put(varName, value);
    }

    // Updated handlePrint method
    private void handlePrint(String line) {
        String content = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();
        if (content.startsWith("\"") && content.endsWith("\"")) {
            // Print the string as it is
            System.out.println(content.substring(1, content.length() - 1));
        } else {
            // Evaluate and print the expression
            int value = evaluateExpression(content);
            System.out.println(value);
        }
    }

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        String program = """
        
        """;

        interpreter.eval(program);
    }
}
