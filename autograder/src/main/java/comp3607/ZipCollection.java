package comp3607;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ZipCollection implements ZipContainer {
    private Path zipFolderPath;
    private String dependenciesPath;
    private String studentId;

    public ZipCollection(Path path) {
        this.zipFolderPath = path;
        this.studentId = DirectoryUtils.getStudentId(path.toString());
    }

    @Override
    public ZipIterator createIterator(String mainZipPath) {
        return new ZipIterator(mainZipPath);
    }

    public void runTest() {
        ZipIterator iterator = createIterator(zipFolderPath.toString());
        System.out.println("Testing:\n");
        System.out.println("Processing submission for Student ID: " + studentId);

        try {
            dependenciesPath = DirectoryUtils.getDependenciesPath();
        } catch (IOException e) {
            System.err.println("Error getting dependencies path: " + e.getMessage());
            dependenciesPath = "";
        }

        // Create reports directory if it doesn't exist
        Path reportsDir = Paths.get(zipFolderPath.getParent().toString(), "reports");
        try {
            if (!reportsDir.toFile().exists()) {
                reportsDir.toFile().mkdirs();
            }
        } catch (Exception e) {
            System.err.println("Error creating reports directory: " + e.getMessage());
        }

        while (iterator.hasNext()) {
            Map<String, File> classes = iterator.next();

            FileContext context = new FileContext();
            Report report = new Report();
            report.setStudentId(studentId);
            report.setOutputDirectory(reportsDir);  // Set the output directory for PDF generation

            List<String> classNamesInOrder = Arrays.asList(
                "ChatBotGenerator", 
                "ChatBot", 
                "ChatBotPlatform", 
                "ChatBotSimulation"
            );

            for (String className : classNamesInOrder) {
                if (classes.containsKey(className)) {
                    File file = classes.get(className);
                    dependenciesPath = Paths.get("src", "main", "resources", "dependencies").toString();
                
                    Class<?> compiledClass = DirectoryUtils.loadClass(className, file.getParent(), dependenciesPath);
                    if (compiledClass != null) {
                        context.setTest(getTestInstance(className));
                        context.testFile(report, compiledClass);
                    }
                }
            }

            // Generate the report (which will now create both text and PDF versions)
            String reportContent = report.generateReport();
            
            // Save text version of the report
            saveReport(reportContent);
            System.out.println(reportContent);
        }
    }

    private void saveReport(String reportContent) {
        try {
            // Create reports directory if it doesn't exist
            Path reportsDir = Paths.get(zipFolderPath.getParent().toString(), "reports");
            if (!reportsDir.toFile().exists()) {
                reportsDir.toFile().mkdirs();
            }

            // Create text report file with student ID in name
            String reportFileName = String.format("report_%s.txt", studentId != null ? studentId : "unknown");
            Path reportPath = reportsDir.resolve(reportFileName);
            
            // Write report content to file
            java.nio.file.Files.write(reportPath, reportContent.getBytes());
            System.out.println("Text report saved to: " + reportPath);

        } catch (IOException e) {
            System.err.println("Error saving text report: " + e.getMessage());
        }
    }

    private static FileTest getTestInstance(String className) {
        switch (className) {
            case "ChatBot":
                return new ChatBotTest();
            case "ChatBotPlatform":
                return new ChatBotPlatformTest();
            case "ChatBotGenerator":
                return new ChatBotGeneratorTest();
            case "ChatBotSimulation":
                return new SimulationTest();
            default:
                throw new IllegalArgumentException("No test available for " + className);
        }
    }
}