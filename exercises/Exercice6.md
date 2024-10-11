# Class cohesion with JavaParser

With the help of JavaParser implement a program that computes the Tight Class Cohesion (TCC) for each class in a given Java project. The program should take as input the path to the source code of the project. It should produce a report in the format of your choice (TXT, CSV, Markdown, HTML, etc.) containing a table showing for each class: the package, name and TCC value. 
Your application should also produce a histogram showing the distribution of CC values in the project. Compare the histogram of two or more projects.
Finally, your application should also produce the dependency graph of each class (cf. example [here](https://people.irisa.fr/Benoit.Combemale/pub/course/vv/vv-textbook-v0.1.pdf#cohesion-graph)). The graph should be written using the [GraphViz DOT format](https://www.graphviz.org/)

Ignore inherited members to compute TCC of a class.

Include in this repository the code of your application. Remove all unnecessary files like compiled binaries. Do include the reports and plots you obtained from different projects. See the [instructions](../sujet.md) for suggestions on the projects to use.

You may use [javaparser-starter](../code/javaparser-starter) as a starting point.
## Answer 

## TCC - Tight Class Cohesion - Definition :

TCC means Tight Class Cohesion and is a software metric. In object-oriented programming, it measures the cohesion of a class. The greater the value, the more cohesive the class, meaning that its methods tend to interact more with each other, all sharing a common purpose.

Or, in other words, TCC is a metric that measures the degree to which methods of a class are structurally bound together by references to its instance variables. If two methods in a class share many instance variables, then the class is said to have high cohesion.
![Screenshot from 2024-10-11 13-50-48](https://github.com/user-attachments/assets/91cec286-0a51-425d-aa06-15f906fb34e7)

## Collect Methods and Instance Variables:

For each class, identify the methods and the instance variables.
Instance variables are fields in the class that are non-static.
### Find Method Connections:
For each pair of methods, check if they share any instance variables. A method pair is considered connected if they both access at least one of the same instance variables.
### Calculate Connected Pairs:
Count the number of method pairs that are connected (i.e., share instance variables).
### Calculate Total Pairs:
The total number of method pairs in a class is given by the combination formula:
![Screenshot from 2024-10-11 13-52-16](https://github.com/user-attachments/assets/8017f06d-725c-42ae-8f7a-e1fdce513ac6)
Where n is the number of methods in the class.
### Compute TCC:
The TCC value is the ratio of connected pairs to total possible method pairs.
## Results of the project Apache Commons Collections:

![Screenshot from 2024-10-11 13-54-32](https://github.com/user-attachments/assets/06efd046-75d5-4b6b-852f-fe063439b4c5)

The TCC range is btw 0.002 and 0.004
## Results of the project Apache Commons Math:

![Screenshot from 2024-10-11 13-58-12](https://github.com/user-attachments/assets/c9dc5e9c-4533-4eb0-aefd-9f9aa80085dd)

the TCC range is near to 0


## High TCC [Better Design]:
 
 Cohesion in a class refers to the degree at which methods and variables of that class are related and cooperate toward fulfilling a single responsibility.
 
High TCC suggests that methods of the class often share the same pool of instance variable. Thus, the class is more likely to have:
### Encapsulation Improved: 
There is clearly defined a level of responsibility within the class, and methods work together to operate on shared data.
### Single Responsibility: 
A highly cohesive class can actually follow the SRP-which means, a class should have one reason to change, or just have one task it concerns itself with-of the SOLID principles.
### Easier Maintenance: 
Changes to the class tend to be confined to a few methods, and hence the code becomes more understandable and easier to modify.
### Improved Testability: 
High cohesion tends to imply that the methods and data of the class are related, and hence testing of the class as a whole becomes easier.

## Low TCC: Possible Design Issues:

Low TCC is indicative that methods of that class share few instance variables, which in turn suggests that :
This class probably does too many different things. In other words, it may not respect the Single Responsibility Principle.
 t could be a God Class or an Anemic Domain Model: a kind of design where it puts too much responsibility on a class or poorly encapsulates its behavior, leading to a bad separation of concerns.
It could result in higher coupling with other parts of the system because the class is not cohesive and will need help from elsewhere to be able to perform its job.

So we can see in the 2 histograms above the highest TCC it was in the Apache Commons Collections project  so that mean it has a higher cohesion compare to Apache Commons Math project.

