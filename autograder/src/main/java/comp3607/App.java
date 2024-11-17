package comp3607;

import java.io.File;
import java.util.Scanner;

public class App {
    public static void main(String[] args) {
        // System.out.println("Testing:\n");

            // FileTest[] tests = { new ChatBotTest(), new ChatBotPlatformTest(), new ChatBotGeneratorTest(), new SimulationTest()};
            // FileContext context = new FileContext();
            // Report report = new Report();
            // for (FileTest test : tests){
            //     context.setTest(test);
            //     context.testFile(report);
            // }
            // System.out.println(report.generateReport());


            // Step 1: Ensure the folder exists
  
        DirectoryUtils.createInputZipFolder();

        // Step 2: Check if the folder has items
        String baseDir = System.getProperty("user.dir");
        File inputZipFolder = new File(baseDir, "input_zip_here");

        if (!DirectoryUtils.hasItems()) {
            System.out.println("The folder 'input_zip_here' is empty. Place your zip files in the folder and type 'run' to continue.");
            
            // Wait for the user to type "run"
            Scanner scanner = new Scanner(System.in);
            String userInput;
            do {
                System.out.print("Type 'run' to proceed: ");
                userInput = scanner.nextLine().trim();
            } while (!"run".equalsIgnoreCase(userInput) || !DirectoryUtils.hasItems());
            
            System.out.println("Items found in 'input_zip_here'. Proceeding...");
        } else {
            System.out.println("Items found in 'input_zip_here'. Proceeding...");
        }

        // Step 3: Process each subfolder or zip file
        File[] files = inputZipFolder.listFiles();
        if (files != null) {
            for (File file : files) {
                System.out.println("processing files");
                if (file.isFile() && file.getName().endsWith(".zip")) {
                    ZipCollection test = new ZipCollection(file.toPath());
                    test.runTest();
                } else {
                    System.out.println("Skipping non-zip file: " + file.getName());
                }
            }
        }
    }
}
