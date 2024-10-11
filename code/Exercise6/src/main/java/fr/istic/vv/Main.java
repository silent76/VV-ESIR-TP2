package fr.istic.vv;

import com.github.javaparser.utils.SourceRoot;

import java.io.File;
import java.io.IOException;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            System.err.println("Provide the path to the source code");
            System.exit(1);
        }

        File file = new File(args[0]);
        if (!file.exists() || !file.isDirectory() || !file.canRead()) {
            System.err.println("Provide a valid readable directory");
            System.exit(2);
        }

        // Initialize the SourceRoot and TCCCalculator
        SourceRoot sourceRoot = new SourceRoot(file.toPath());
        TCCCalculator tccCalculator = new TCCCalculator();

        // Parse the directory and calculate TCC for each class
        sourceRoot.parse("", (localPath, absolutePath, result) -> {
            result.ifSuccessful(unit -> unit.accept(tccCalculator, null));
            return SourceRoot.Callback.Result.DONT_SAVE;
        });

        // Generate report files and output the results
        tccCalculator.generateReport();
        tccCalculator.generateGraphViz();
        tccCalculator.generateHistogram();
    }
}
