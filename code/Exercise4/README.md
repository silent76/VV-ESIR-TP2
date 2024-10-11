package fr.istic.vv;


import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.*;
import com.github.javaparser.ast.visitor.VoidVisitorWithDefaults;


import java.util.*;
import java.util.stream.Collectors;
import java.io.FileWriter;
import java.io.IOException;


// This class visits a compilation unit and detects private fields in public classes
// that do not have public getters. It also generates a report listing those fields.
public class PublicElementsPrinter extends VoidVisitorWithDefaults<Void> {


    // List to store report entries
    private final List<String> report = new ArrayList<>();


    // This method visits the root of the CompilationUnit, which contains all types (classes, interfaces, etc.)
    @Override
    public void visit(CompilationUnit unit, Void arg) {
        for (TypeDeclaration<?> type : unit.getTypes()) {
            type.accept(this, null);  // Accept the visitor for each type in the unit
        }
    }


    // This method visits a TypeDeclaration (which can be a class or an interface)
    public void visitTypeDeclaration(TypeDeclaration<?> declaration, Void arg) {
        // Skip if the class/interface is not public
        if (!declaration.isPublic()) return;


        // Get the fully qualified name of the class
        String className = declaration.getFullyQualifiedName().orElse("[Anonymous]");
       
        // Get all private fields in the class
        List<FieldDeclaration> privateFields = declaration.getFields().stream()
            .filter(f -> f.isPrivate())  // Filter to keep only private fields
            .collect(Collectors.toList());


        // Get all public methods in the class
        List<MethodDeclaration> publicMethods = declaration.getMethods().stream()
            .filter(MethodDeclaration::isPublic)  // Filter to keep only public methods
            .collect(Collectors.toList());


        // Check each private field for the presence of a public getter
        for (FieldDeclaration field : privateFields) {
            for (VariableDeclarator var : field.getVariables()) {
                String fieldName = var.getNameAsString();
               
                // Check if there's a public getter method for this field
                boolean hasGetter = publicMethods.stream()
                    .anyMatch(m -> isGetter(m, fieldName));
               
                // If no getter is found, add an entry to the report
                if (!hasGetter) {
                    report.add("Class: " + className + ", Field: " + fieldName);
                }
            }
        }


        // Check nested types (inner classes or interfaces) recursively
        for (BodyDeclaration<?> member : declaration.getMembers()) {
            if (member instanceof TypeDeclaration) {
                member.accept(this, arg);  // Recursively visit nested classes
            }
        }
    }


    // Overriding visitor methods for Class or Interface declarations
    @Override
    public void visit(ClassOrInterfaceDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);  // Delegate to visitTypeDeclaration
    }


    // Overriding visitor methods for Enum declarations
    @Override
    public void visit(EnumDeclaration declaration, Void arg) {
        visitTypeDeclaration(declaration, arg);  // Delegate to visitTypeDeclaration
    }


    // Overriding visitor methods for Method declarations (not used directly for this task)
    @Override
    public void visit(MethodDeclaration declaration, Void arg) {
        if (!declaration.isPublic()) return;
        System.out.println("  " + declaration.getDeclarationAsString(true, true));
    }


    // Utility method to check if a method is a getter for a given field name
    private boolean isGetter(MethodDeclaration method, String fieldName) {
        String methodName = method.getNameAsString().toLowerCase();
        String expectedGetterName = "get" + fieldName.toLowerCase();  // Expected getter name format


        // Check if the method has no parameters, returns a value (not void), and its name matches the getter pattern
        return method.getParameters().isEmpty() &&
               method.getType().toString().equals("void") == false &&
               (methodName.equals(expectedGetterName) || methodName.equals("is" + fieldName.toLowerCase()));
    }


    // Method to generate a report and write it to a file
    public void generateReport() {
        try (FileWriter writer = new FileWriter("nogetter_report.txt")) {
            for (String line : report) {
                writer.write(line + "\n");  // Write each entry to the file
            }
            System.out.println("Report generated: nogetter_report.txt");
        } catch (IOException e) {
            e.printStackTrace();  // Handle any I/O exceptions
        }
    }
}

package fr.istic.vv;


import com.github.javaparser.utils.SourceRoot;
import java.io.File;
import java.io.IOException;


public class Main {


    public static void main(String[] args) throws IOException {
        // Check if the user provided a path to the source code as an argument
        if (args.length == 0) {
            System.err.println("Should provide the path to the source code");
            System.exit(1);  // Exit the program with an error code if no path is provided
        }


        // Create a File object from the provided path
        File file = new File(args[0]);
       
        // Validate the provided path: check if it exists, is a directory, and can be read
        if (!file.exists() || !file.isDirectory() || !file.canRead()) {
            System.err.println("Provide a path to an existing readable directory");
            System.exit(2);  // Exit the program with an error code if the path is invalid
        }


        // Create a SourceRoot object to represent the root directory of the source code
        SourceRoot root = new SourceRoot(file.toPath());
       
        // Instantiate PublicElementsPrinter to analyze the source code
        PublicElementsPrinter printer = new PublicElementsPrinter();
       
        // Parse all source files in the directory and apply the PublicElementsPrinter visitor
        root.parse("", (localPath, absolutePath, result) -> {
            result.ifSuccessful(unit -> unit.accept(printer, null));  // If parsing is successful, visit the CompilationUnit
            return SourceRoot.Callback.Result.DONT_SAVE;  // Do not save changes to the source files
        });


        // Generate the report after processing all files
        printer.generateReport();
    }
}
