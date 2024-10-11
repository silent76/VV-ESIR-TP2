# Cyclomatic Complexity with JavaParser

With the help of JavaParser implement a program that computes the Cyclomatic Complexity (CC) of all methods in a given Java project. The program should take as input the path to the source code of the project. It should produce a report in the format of your choice (TXT, CSV, Markdown, HTML, etc.) containing a table showing for each method: the package and name of the declaring class, the name of the method, the types of the parameters and the value of CC.
Your application should also produce a histogram showing the distribution of CC values in the project. Compare the histogram of two or more projects.


Include in this repository the code of your application. Remove all unnecessary files like compiled binaries. Do include the reports and plots you obtained from different projects. See the [instructions](../sujet.md) for suggestions on the projects to use.

You may use [javaparser-starter](../code/javaparser-starter) as a starting point.

## Answer
This project calculates the Cyclomatic Complexity of methods in Java code using JavaParser. The Cyclomatic Complexity is a source code complexity measure that calculates the number of linearly independent paths through the code. A higher value usually means more complex methods, which could be less maintainable, testable, and debuggable.

V(G)=E−N+2P


Program Key Features:

	Cyclomatic Complexity Calculation:
It walks through an Abstract Syntax Tree in Java, counting control flow structures-if, for, while, and switch-which constitute the method complexity.
    	Every method starts with a base complexity of 1, to which all the different control structures then add up.
    
	Method Report:
The program will output a CSV report that enumerates every method, including its class, its parameters, and the calculated CC of it.
This report can be used to identify methods with high complexity that might require refactoring or additional testing.

Histogram Visualization:
It generates a histogram with JFreeChart, showing the distribution of CC across the project. This provides a very good feel for how complex the methods are at a project-wide level.

Observations:

Usability: The program asks the user to input a directory path and then processes all the .java files in the given directory.
Customizability: This can easily be extended for other control structures, too, and also for any other metric concerning code complexity.
Visualization: The histogram gives insight into where most of the methods lie regarding complication in the source code; hence, it creates a better way to find bottlenecks.
Possible Ways of Enhancement:

Full Method Signatures: It presently uses method parameters from toString(). It could show more detailed type information.
Advanced Complexity Metrics: The tool would be able to provide more advanced metrics such as Halstead Complexity or Maintainability Index. Multi-project Comparison: The tool may, in future versions, compare several project histograms side by side for better analysis. Generally, this is a very useful utility for gauging the complexity of the Java codebase and hence providing necessary areas that need simplification for good maintainability.
Results for the class Test:
![Screenshot from 2024-10-09 21-48-41](https://github.com/user-attachments/assets/addc3150-17ea-48c3-878f-d1665798db8f)
![test_table](https://github.com/user-attachments/assets/ac244527-5e3b-4fc3-9904-3d0515223633)

Results of the project Apache Commons Collections:
![Screenshot from 2024-10-09 21-55-11](https://github.com/user-attachments/assets/539fee48-9ed6-4627-939c-137e2b976710)

there’s 2617 method in this project
We can see the max CC is 20 
Results of the project Apache Commons Math:
![Screenshot from 2024-10-09 22-11-57](https://github.com/user-attachments/assets/17f855c9-1d9f-4bfb-9f37-29c998427882)
there’s 79 method for this project 
We can see the max CC is  7

If CC is low, then in general, the code is simpler; hence, more straightforward to understand and maintain and less difficult to test. On the other hand, a high CC indicates complex code that is harder to maintain and more bug-prone since there are more ways to go through the code.

Low Cyclomatic Complexity:

Easier to comprehend: Low CC normally means fewer branches, such as if statements, for statements, while statements, and switch statements; thus, it will be easier to trace. Easier to maintain: Fewer paths mean fewer decision points that the developers have to worry about while making modifications in code without creating further bugs.
Easier to test: Low complexity means fewer test cases are needed for good test coverage because there are fewer independent paths to consider.
Lower bug risk: Fewer decision points mean reduced likelihood of logical errors or bugs.

Low CC is generally considered good in that it means the code is well-structured, straightforward, and less liable to have bugs.

High Cyclomatic Complexity (Bad):

Harder to understand: High CC usually signifies that a method or function is doing too much, where there are many branches, loops, and decision points involved, which will be very hard to follow.
Harder to maintain: In general, the more complex the code, the harder it is to change or extend. For instance, there is much higher risk of introducing bugs when modifications are being made.
Harder to test: More paths mean more tests for adequate coverage. Normally, high CC means more test cases to cover all the possible execution paths and hence increasing the testing effort.
Higher risk of bugs: More branches and decision points mean more possibilities to introduce errors, especially when code is changed over time.


Ideal Cyclomatic Complexity Range:

Low CC (1-10): This is typically considered optimal for most methods. Code is simple, clear, and easily maintainable.
Moderate CC (11-20): This might be acceptable for slightly more complex methods but should still be reviewed to ensure it’s not overly complicated.
High CC (>20): This would indicate that a method is probably doing too much, and may be in serious need of refactoring into smaller methods or even classes to simplify and make them more manageable.



