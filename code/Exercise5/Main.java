package fr.istic.vv;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Map;

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import com.github.javaparser.utils.SourceRoot;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length < 1) {
            System.out.println("Please provide the path to the source code");
            return;
        }

        File projectDir = new File(args[0]);
        if (!projectDir.exists() || !projectDir.isDirectory()) {
            System.out.println("Invalid directory: " + projectDir);
            return;
        }

        System.out.println("Parsing project at: " + projectDir.getAbsolutePath());

        SourceRoot sourceRoot = new SourceRoot(Paths.get(projectDir.getAbsolutePath()));

        // Create a single instance of CyclomaticComplexityCalculator
        CyclomaticComplexityCalculator calculator = new CyclomaticComplexityCalculator();

        // Parse all Java files in the project
        sourceRoot.parse("", (localPath, absolutePath, result) -> {
            System.out.println("Parsing file: " + absolutePath);  // Debugging output for each file parsed
            result.ifSuccessful(cu -> {
                System.out.println("Parsed successfully: " + cu.getPackageDeclaration().map(pd -> pd.getNameAsString()).orElse("[No package]"));
                cu.accept(calculator, null);  // Use the same calculator instance
            });
            return SourceRoot.Callback.Result.DONT_SAVE;
        });

        // Output report
        Map<String, Integer> complexityMap = calculator.getMethodCyclomaticComplexity();
        if (!complexityMap.isEmpty()) {
            writeReport(complexityMap);
            createHistogram(complexityMap);
        } else {
            System.out.println("No methods found or complexity could not be calculated.");
        }
    }

    private static void writeReport(Map<String, Integer> methodComplexityMap) throws IOException {
        FileWriter writer = new FileWriter("cc_report.csv");
        writer.write("Method, Cyclomatic Complexity\n");

        for (Map.Entry<String, Integer> entry : methodComplexityMap.entrySet()) {
            writer.write(entry.getKey() + ", " + entry.getValue() + "\n");
        }
        writer.close();
    }

    private static void createHistogram(Map<String, Integer> methodComplexityMap) {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();

        for (Map.Entry<String, Integer> entry : methodComplexityMap.entrySet()) {
            dataset.addValue(entry.getValue(), "Cyclomatic Complexity", entry.getKey());
        }

        JFreeChart chart = ChartFactory.createBarChart(
                "Cyclomatic Complexity Histogram",
                "Method",
                "Complexity",
                dataset,
                PlotOrientation.VERTICAL,
                true, true, false);

        JFrame frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
}
