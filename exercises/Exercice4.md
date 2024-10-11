# No getter!

With the help of JavaParser implement a program that obtains the private fields of public classes that have no public getter in a Java project. 

A field has a public getter if, in the same class, there is a public method that simply returns the value of the field and whose name is `get<name-of-the-field>`.

For example, in the following class:

```Java

class Person {
    private int age;
    private String name;
    
    public String getName() { return name; }

    public boolean isAdult() {
        return age > 17;
    }
}
```

`name` has a public getter, while `age` doesn't.

The program should take as input the path to the source code of the project. It should produce a report in the format of your choice (TXT, CSV, Markdown, HTML, etc.) that lists for each detected field: its name, the name of the declaring class and the package of the declaring class.

Include in this repository the code of your application. Remove all unnecessary files like compiled binaries. See the [instructions](../sujet.md) for suggestions on the projects to use.

*Disclaimer* In a real project not all fields need to be accessed with a public getter.




**Answers : **

We'll use JavaParser in order to parse the Java source code, analyze every public class, and find its private fields. For each private field, we check if there is a public getter method with the name get<FieldName>() returning that field's value. If not, we log details of the field-name, class, and package-to a report.

Now we will only modify the **PublicElementsPrinter** class so that it can detects private fields without a public getter in some certain public class

And for the **Main class** we will only add a feature that will generate for us a report in TXT that lists for each detected field: its name, the name of the declaring class and the package of the declaring class.

now when we execute the main class, we can see that the implementation works perfectly so it specified that report is a private field that doesn’t have a public getter and to test more if our code works we added a simple class Person that was provided for us in the exercice : 

![Class Person](https://github.com/user-attachments/assets/43782afa-12e1-4836-ad48-5407c662723b)


and normally we should get in the report file the attribute age because it is indeed private and it doesn’t have a public getter. and this what we got 


![Report file](https://github.com/user-attachments/assets/749b9e21-0de4-42d7-9dc4-91dbf1170a22)





