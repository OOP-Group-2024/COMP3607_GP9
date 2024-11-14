package comp3607;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

public class ZipCollection implements ZipContainer{

    Path zipFolderPath = Paths.get(System.getProperty("user.dir"), "input_zip_here");

    @Override
    public ZipIterator createIterator(String mainZipPath) {
        return new ZipIterator(mainZipPath);
    }

    public void runTest (){
        ZipIterator iterator = createIterator(zipFolderPath.toString());
        System.out.println("Testing:\n");

        while(iterator.hasNext()){
            Map<String, Class<?>> classes = iterator.next();
            ChatBotTest chatBotTest = new ChatBotTest();
            ChatBotPlatformTest chatBotPlatformTest = new ChatBotPlatformTest();
            ChatBotGeneratorTest chatBotGeneratorTest = new ChatBotGeneratorTest();
            SimulationTest simulationTest = new SimulationTest();
            
            // Create a context and report for each iteration
            FileContext context = new FileContext();
            Report report = new Report();
            context.setTest(chatBotTest);
            context.testFile(report);

                    
            System.out.println(report.generateReport());
        }
    }
}
