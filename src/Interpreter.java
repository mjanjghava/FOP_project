import java.util.*;

public class Interpreter {
    private final Map<String, Integer> variables = new HashMap<>();

    public void eval(String program) {
        String[] lines = program.split("\n");
        int i = 0;
        while (i < lines.length) {
            String line = lines[i].trim();
            int commentIndex = line.indexOf("//");
            if (commentIndex != -1) {
                line = line.substring(0, commentIndex).trim();
            }

            if (line.startsWith("while")) {
                i = new LoopHandler(this).handleWhile(line, lines, i);
            } else if (line.startsWith("if")) {
                i = new ConditionalHandler(this).handleIf(line, lines, i);
            } else if (line.startsWith("print")) {
                handlePrint(line);
            } else if (line.contains("=")) {
                handleAssignment(line);
            } else if (line.startsWith("for")) {
                i = new LoopHandler(this).handleFor(line, lines, i);
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

    private void handlePrint(String line) {
        String content = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();
        Object result = evaluateExpression(content);

        System.out.println(result);
    }

    public static void main(String[] args) {
        Interpreter interpreter = new Interpreter();
        String program = """

        """;

        interpreter.eval(program);
    }
}
