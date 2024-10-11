package fr.istic.vv;

import java.util.HashMap;
import java.util.Map;

import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.stmt.ForStmt;
import com.github.javaparser.ast.stmt.IfStmt;
import com.github.javaparser.ast.stmt.SwitchStmt;
import com.github.javaparser.ast.stmt.WhileStmt;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class CyclomaticComplexityCalculator extends VoidVisitorAdapter<Void> {

    private int cyclomaticComplexity = 1;  // Base value for every method
    private final Map<String, Integer> methodCyclomaticComplexityMap = new HashMap<>();

    @Override
    public void visit(IfStmt n, Void arg) {
        super.visit(n, arg);
        cyclomaticComplexity++;
    }

    @Override
    public void visit(ForStmt n, Void arg) {
        super.visit(n, arg);
        cyclomaticComplexity++;
    }

    @Override
    public void visit(WhileStmt n, Void arg) {
        super.visit(n, arg);
        cyclomaticComplexity++;
    }

    @Override
    public void visit(SwitchStmt n, Void arg) {
        super.visit(n, arg);
        cyclomaticComplexity += n.getEntries().size();
    }

    @Override
    public void visit(MethodDeclaration method, Void arg) {
        // Print the method details to debug
        System.out.println("Visiting method: " + method.getNameAsString());

        // Reset complexity for each method
        cyclomaticComplexity = 1;  // Reset at the start of each method
        super.visit(method, arg);

        // Store method name and calculated cyclomatic complexity
        String methodName = method.getNameAsString();
        methodCyclomaticComplexityMap.put(methodName, cyclomaticComplexity);
    }

    // Getter for accessing the cyclomatic complexities of methods
    public Map<String, Integer> getMethodCyclomaticComplexity() {
        return methodCyclomaticComplexityMap;
    }
}
