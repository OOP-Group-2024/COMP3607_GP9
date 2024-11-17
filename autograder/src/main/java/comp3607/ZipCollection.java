package comp3607;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class ZipCollection implements ZipContainer{

    Path zipFolderPath = Paths.get(System.getProperty("user.dir"), "input_zip_here");

    String dependenciesPath;

    

    public ZipCollection(Path path){
        this.zipFolderPath=path;
    }

    @Override
    public ZipIterator createIterator(String mainZipPath) {
        return new ZipIterator(mainZipPath);
    }

    public void runTest (){
        ZipIterator iterator = createIterator(zipFolderPath.toString());
        System.out.println("Testing:\n");

        try{
            dependenciesPath = DirectoryUtils.getDependenciesPath();
        } catch (IOException e) {
            System.err.println("Error getting dependencies path: " + e.getMessage());
            dependenciesPath = "";
        }

        while(iterator.hasNext()){
            Map<String, File> classes = iterator.next();

            // Create the test objects
            // ChatBotTest chatBotTest = new ChatBotTest();
            // ChatBotPlatformTest chatBotPlatformTest = new ChatBotPlatformTest();
            // ChatBotGeneratorTest chatBotGeneratorTest = new ChatBotGeneratorTest();
            // SimulationTest simulationTest = new SimulationTest();

            // Create a context and report for each iteration
            FileContext context = new FileContext();
            Report report = new Report();

            List<String> classNamesInOrder = Arrays.asList("ChatBotGenerator", "ChatBot", "ChatBotPlatform", "ChatBotSimulation");

            for (String className : classNamesInOrder) {
                if(classes.containsKey(className)){
                    File file = classes.get(className);

                    dependenciesPath=Paths.get("src", "main", "resources", "dependencies").toString();
                
                    Class<?> compiledClass = DirectoryUtils.loadClass(className, file.getParent(), dependenciesPath);
                    if (compiledClass != null) {
                        // Run the test for this class if it compiles
                        context.setTest(getTestInstance(className));
                        context.testFile(report, compiledClass);
                    }
                }
                // else{
                //     File tempFile = new File(Paths.get("src", "main", "resources", "dependencies", className).toString());
                //     System.out.println(tempFile.getAbsoluteFile());
                //     DirectoryUtils.compileFile(tempFile);
                // }
            }

            // Print the report for this iteration
            System.out.println(report.generateReport());
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
