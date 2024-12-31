FOP_Project: Python-to-Java Interpreter

Overview

This project implements a Python-to-Java interpreter, supporting a minimal subset of Python. The emphasis is on interpreting basic constructs such as:
• Variables and Assignments
• Arithmetic Operations
• Conditional Statements (Limited to if statements)
• Iterative Control Flow (Supports both for and while loops)

The interpreter can execute predefined algorithms, demonstrating its ability to handle common computational problems. These algorithms are included in a separate "algorithm file" located in the same folder as the interpreter. They can be run using the interpreter.

Features
Supported Constructs
1. Variables and Assignments: Allows defining and using variables.
2. Arithmetic Operations: Supports addition, subtraction, multiplication, division, and modulus.
3.  Conditionals: Handles if statements (Note: else and elif statements are not supported).
4.Loops: Supports for and while loops.

Algorithms Included

A file named algorithms.txt (in the same folder as this project) contains Python-like code for the following algorithms, which can be copied into the program variable and executed using the interpreter:

1. Sum of First N Numbers
2. Factorial of N
3. GCD of Two Numbers
4. Reverse a Number
5. Check if a Number is Prime (Not supported by the current interpreter)
6. Check if a Number is Palindrome
7. Find the Largest Digit in a Number (has issues. prints last digit not the largest one)
8. Sum of Digits
9. Multiplication Table
10. Nth Fibonacci Number

Usage
Initialization:

Instantiate the Interpreter class in the main method.

Write Code to Execute:

Use the program variable to write the Python-like code you want to interpret.

Run the Program:

Call the eval method of the Interpreter class with the program as input.

Example

public static void main(String[] args) {
    Interpreter interpreter = new Interpreter();
    String program = """
    N = 10
    sum = 0
    for i in range(1, N + 1):
        sum = sum + i
    print(sum)
    """;

    interpreter.eval(program);
}

File Structure

• Interpreter.java: Contains the main logic for parsing and executing Python-like code.
• algorithms.txt: Contains examples of the supported algorithms in Python syntax. These can be directly copied into the        program variable for execution.

Additional Notes
• Extensibility: The interpreter is designed to be modular, allowing for easy addition of new features or constructs in the 
  future.
• Error Handling: Includes basic syntax and runtime error handling to ensure smooth execution.
• Learning Resource: Ideal for understanding the fundamentals of interpreters and language translation.
