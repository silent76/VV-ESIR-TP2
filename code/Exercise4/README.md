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
