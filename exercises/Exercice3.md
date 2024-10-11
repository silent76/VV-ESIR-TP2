# Extending PMD

Use XPath to define a new rule for PMD to prevent complex code. The rule should detect the use of three or more nested `if` statements in Java programs so it can detect patterns like the following:

```Java
if (...) {
    ...
    if (...) {
        ...
        if (...) {
            ....
        }
    }

}
```
Notice that the nested `if`s may not be direct children of the outer `if`s. They may be written, for example, inside a `for` loop or any other statement.
Write below the XML definition of your rule.

You can find more information on extending PMD in the following link: https://pmd.github.io/latest/pmd_userdocs_extending_writing_rules_intro.html, as well as help for using `pmd-designer` [here](./designer-help.md).

Use your rule with different projects and describe you findings below. See the [instructions](../sujet.md) for suggestions on the projects to use.

## Answer
Explanation : 
To extend PMD with a new rule that detects complex code by identifying instances of three or more nested if statements in Java programs, we can create a custom rule using XPath. Below is the XML definition for this rule.

Explanation of the Rule:
Rule Reference: This rule extends the existing NestedIf rule from PMD's design ruleset.
Description: A brief explanation of the purpose of the rule.
Properties: The maximumNesting property is set to 3 to identify three or more nested if statements.

XPath Expression:
The XPath expression //IfStatement[count(ancestor::IfStatement) >= 2] identifies if statements that have two or more ancestors that are also if statements, thus detecting the third nested level.
Message: A message indicating to the developer that three or more nested if statements should be avoided for better readability.
We'll use **JavaParser** to parse Java source code, inspect each public class, and identify its private fields. For each private field, we'll check if there's a public getter method with the name `get<FieldName>()` that returns the value of that field. If not, we'll log the details of the field (its name, class, and package) in a report.

The final output can be written to a report in a format like **CSV** for easy readability.
Results:
![if](https://github.com/user-attachments/assets/197127ef-00f1-46d6-8233-42a35e0f4d1d)








