
# Using PMD


Pick a Java project from Github (see the [instructions](../sujet.md) for suggestions). Run PMD on its source code using any ruleset (see the [pmd install instruction](./pmd-help.md)). Describe below an issue found by PMD that you think should be solved (true positive) and include below the changes you would add to the source code. Describe below an issue found by PMD that is not worth solving (false positive). Explain why you would not solve this issue.


## Answer

**Results of pmd:**

`False positive:`

ArrayStack.java:56:    UncommentedEmptyConstructor:    Document empty constructor

here we have an error in line 56 in the ArrayStack.java file 

This often indicates that the constructor doesn't perform any initialization or setup, which can be confusing for other developers reading the code.

It was noted that the developer has already added a comment that explains l’emptyConstructor 
 /**
 	* Constructs a new empty {@code ArrayStack}. The initial size
 	* is controlled by {@code ArrayList} and is currently 10.
 	*/
	public ArrayStack(){
	}

If the comment adequately explains the constructor's purpose and the warning persists, it's reasonable to treat it as a false positive in that specific context.
We can consider this error as a false positive since the comment is already written and we don't need to change anything.

`true positive:`

BagUtils.java:36:    ClassWithOnlyPrivateConstructorsShouldBeFinal:  This class has only private constructors and may be final

True Positive Warning: The PMD warning is applicable here since BagUtils is a utility class meant for static access, and marking it as final would clarify its intended use.

 private BagUtils() {
    	// empty
	}
 
**Solution:**

BagUtils contains only private constructors and does not need to be subclassed or instantiated, it should be marked as final 

public final class BagUtils{
	// Private constructor to prevent instantiation
	private BagUtils() {}
}





