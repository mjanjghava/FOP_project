
import java.util.List;
import java.util.ArrayList;

public class ConditionalHandler {

    private final Interpreter interpreter;

    public ConditionalHandler(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public int handleIf(String line, String[] lines, int startIndex) {
        // Ensure parentheses are present
        int openParenIndex = line.indexOf("(");
        int closeParenIndex = line.lastIndexOf(")");

        if (openParenIndex == -1 || closeParenIndex == -1) {
            throw new IllegalArgumentException("Invalid condition syntax in: " + line);
        }

        String condition = line.substring(openParenIndex + 1, closeParenIndex).trim();
        List<String> body = extractBlock(lines, startIndex);

        int currentIndex = startIndex + body.size();

        // Handle 'if' block
        if (interpreter.evaluateCondition(condition)) {
            for (String bodyLine : body) {
                interpreter.eval(bodyLine.trim());
            }
        }
        // Handle 'else' block if it exists
        else if (currentIndex < lines.length && lines[currentIndex].trim().startsWith("else")) {
            List<String> elseBody = extractBlock(lines, currentIndex);
            for (String elseLine : elseBody) {
                interpreter.eval(elseLine.trim());
            }
            currentIndex += elseBody.size(); // Update index after else block
        }

        return currentIndex; // Return the updated index after handling the condition (if/else)
    }

    private List<String> extractBlock(String[] lines, int startIndex) {
        List<String> body = new ArrayList<>();
        String blockIndent = getIndentation(lines[startIndex]);

        for (int i = startIndex + 1; i < lines.length; i++) {
            String currentLine = lines[i];
            String currentIndent = getIndentation(currentLine);

            // Check for the end of the block (less indentation or empty line)
            if (!currentLine.trim().isEmpty() && currentIndent.length() <= blockIndent.length()) {
                break;
            }

            if (!currentLine.trim().isEmpty()) {
                body.add(currentLine.trim());
            }
        }

        return body;
    }

    private String getIndentation(String line) {
        return line.replaceAll("\\S.*", "");
    }
}
