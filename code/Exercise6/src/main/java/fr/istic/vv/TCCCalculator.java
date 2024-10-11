package fr.istic.vv;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;  // Corrected package

import javax.swing.JFrame;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.data.statistics.HistogramDataset;

import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;
import com.github.javaparser.ast.visitor.VoidVisitorAdapter;

public class TCCCalculator extends VoidVisitorAdapter<Void> {

    private final List<ClassTCCInfo> classTCCInfoList = new ArrayList<>();

    @Override
    public void visit(ClassOrInterfaceDeclaration classDeclaration, Void arg) {
        if (!classDeclaration.isInterface()) {
            // Collect instance variables and methods
            List<String> instanceVariables = new ArrayList<>();
            List<MethodDeclaration> methods = new ArrayList<>();

            classDeclaration.getFields().forEach(field -> {
                if (!field.isStatic()) {
                    field.getVariables().forEach(variable -> instanceVariables.add(variable.getNameAsString()));
                }
            });

            classDeclaration.getMethods().forEach(method -> {
                if (!method.isStatic() && method.isPublic()) {
                    methods.add(method);
                }
            });

            // Calculate TCC for the class
            double tccValue = calculateTCC(methods, instanceVariables);
            classTCCInfoList.add(new ClassTCCInfo(
                    classDeclaration.getFullyQualifiedName().orElse(""),
                    classDeclaration.getNameAsString(),
                    tccValue
            ));
        }
        super.visit(classDeclaration, arg);
    }

    private double calculateTCC(List<MethodDeclaration> methods, List<String> instanceVariables) {
        Map<MethodDeclaration, Set<String>> methodToVariables = new HashMap<>();

        // Track which variables are accessed by which methods
        for (MethodDeclaration method : methods) {
            Set<String> accessedVariables = new HashSet<>();
            method.findAll(com.github.javaparser.ast.expr.FieldAccessExpr.class).forEach(fieldAccess -> {
                String variableName = fieldAccess.getNameAsString();
                if (instanceVariables.contains(variableName)) {
                    accessedVariables.add(variableName);
                }
            });
            methodToVariables.put(method, accessedVariables);
        }

        // Count connected method pairs
        int connectedPairs = 0;
        int totalPairs = (methods.size() * (methods.size() - 1)) / 2;

        for (int i = 0; i < methods.size(); i++) {
            for (int j = i + 1; j < methods.size(); j++) {
                if (!Collections.disjoint(methodToVariables.get(methods.get(i)), methodToVariables.get(methods.get(j)))) {
                    connectedPairs++;
                }
            }
        }

        return totalPairs == 0 ? 0 : (double) connectedPairs / totalPairs;
    }

    // Generate report in CSV format
    public void generateReport() throws IOException {
        FileWriter writer = new FileWriter("tcc_report.csv");
        writer.write("Package,Class Name,TCC\n");

        for (ClassTCCInfo classInfo : classTCCInfoList) {
            writer.write(String.format("%s,%s,%.2f\n", classInfo.getPackageName(), classInfo.getClassName(), classInfo.getTCC()));
        }
        writer.close();
    }

    // Generate a GraphViz DOT file showing class dependencies
    public void generateGraphViz() throws IOException {
        FileWriter writer = new FileWriter("dependency_graph.dot");
        writer.write("digraph G {\n");

        for (ClassTCCInfo classInfo : classTCCInfoList) {
            writer.write(String.format("\"%s\";\n", classInfo.getClassName()));
        }

        writer.write("}\n");
        writer.close();
    }

    // Generate a histogram (could be a text-based or graphical representation)
    public void generateHistogram() {
        // Create the dataset for the histogram
        double[] tccValues = classTCCInfoList.stream()
                                             .mapToDouble(ClassTCCInfo::getTCC)
                                             .toArray();
        
        HistogramDataset dataset = new HistogramDataset();
        dataset.addSeries("TCC Histogram", tccValues, 10);  // 10 bins for the histogram
    
        // Create the chart using the dataset
        JFreeChart chart = ChartFactory.createHistogram(
                "TCC Histogram",        // Chart title
                "TCC Range",            // X-Axis label
                "Frequency",            // Y-Axis label
                dataset,                // Dataset
                PlotOrientation.VERTICAL, // Chart orientation
                true,                   // Legend
                true,                   // Tooltips
                false                   // URLs
        );
    
        // Customize the plot (optional)
        XYPlot plot = chart.getXYPlot();
        plot.setDomainPannable(true);  // Enable panning along the X-axis
        plot.setRangePannable(true);   // Enable panning along the Y-axis
    
        // Display the chart in a JFrame
        JFrame frame = new JFrame("TCC Histogram");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(new ChartPanel(chart));
        frame.pack();
        frame.setVisible(true);
    }
    

// Helper class to store TCC information about a class
class ClassTCCInfo {
    private final String packageName;
    private final String className;
    private final double tcc;

    public ClassTCCInfo(String packageName, String className, double tcc) {
        this.packageName = packageName;
        this.className = className;
        this.tcc = tcc;
    }
public void generateHistogram() {
    // Create the dataset for the histogram
    double[] tccValues = classTCCInfoList.stream()
                                        .mapToDouble(ClassTCCInfo::getTCC)
                                        .toArray();
    
    HistogramDataset dataset = new HistogramDataset();
    dataset.addSeries("TCC Histogram", tccValues, 10);  // 10 bins for the histogram

    // Create the chart using the dataset
    JFreeChart chart = ChartFactory.createHistogram(
            "TCC Histogram",        // Chart title
            "TCC Range",            // X-Axis label
            "Frequency",            // Y-Axis label
            dataset,                // Dataset
            PlotOrientation.VERTICAL, // Chart orientation
            true,                   // Legend
            true,                   // Tooltips
            false                   // URLs
    );

    // Customize the plot (optional)
    XYPlot plot = chart.getXYPlot();
    plot.setDomainPannable(true);  // Enable panning along the X-axis
    plot.setRangePannable(true);   // Enable panning along the Y-axis

    // Display the chart in a JFrame
    JFrame frame = new JFrame("TCC Histogram");
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    frame.getContentPane().add(new ChartPanel(chart));
    frame.pack();
    frame.setVisible(true);
}

    public String getPackageName() {
        return packageName;
    }

    public String getClassName() {
        return className;
    }

    public double getTCC() {
        return tcc;
    }
}
}