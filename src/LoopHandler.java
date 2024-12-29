import java.util.*;

public class LoopHandler {
    private final Interpreter interpreter;

    public LoopHandler(Interpreter interpreter) {
        this.interpreter = interpreter;
    }

    public int handleWhile(String line, String[] lines, int startIndex) {
        String condition = extractCondition(line);
        List<String> body = extractBlock(lines, startIndex);

        int nextLineIndex = startIndex + body.size() + 1;
        while (interpreter.evaluateCondition(condition)) {
            for (String bodyLine : body) {
                interpreter.eval(bodyLine.trim());
            }
        }
        return nextLineIndex;
    }

    public int handleFor(String line, String[] lines, int startIndex) {
        String loopVar = extractForLoopVar(line);
        int[] range = extractRange(line);
        int start = range[0], end = range[1];

        interpreter.getVariables().put(loopVar, start);
        List<String> body = extractBlock(lines, startIndex);

        int currentIndex = startIndex + body.size();
        while (interpreter.getVariables().get(loopVar) < end) {
            for (String bodyLine : body) {
                interpreter.eval(bodyLine.trim());
            }
            interpreter.getVariables().put(loopVar, interpreter.getVariables().get(loopVar) + 1);
        }
        return currentIndex;
    }

    private String extractCondition(String line) {
        return line.substring(line.indexOf(" ") + 1, line.length() - 1).trim();
    }

    private String extractForLoopVar(String line) {
        return line.substring(4, line.indexOf("in")).trim();
    }

    private int[] extractRange(String line) {
        String rangeContent = line.substring(line.indexOf("(") + 1, line.lastIndexOf(")")).trim();
        String[] rangeParts = rangeContent.split(",");
        return new int[] {
                interpreter.evaluateExpression(rangeParts[0].trim()),
                interpreter.evaluateExpression(rangeParts[1].trim())
        };
    }

    private List<String> extractBlock(String[] lines, int startIndex) {
        List<String> body = new ArrayList<>();
        String blockIndent = getIndentation(lines[startIndex]);

        for (int i = startIndex + 1; i < lines.length; i++) {
            String currentLine = lines[i];
            String currentIndent = getIndentation(currentLine);

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
