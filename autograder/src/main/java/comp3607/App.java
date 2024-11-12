package comp3607;


public class App {
    public static void main(String[] args) {
        System.out.println("Testing:\n");

            FileTest[] tests = { new ChatBotTest(), new ChatBotPlatformTest(), new ChatBotGeneratorTest()};
            FileContext context = new FileContext();
            Report report = new Report();
            for (FileTest test : tests){
                context.setTest(test);
                context.testFile(report);
            }
            System.out.println(report.generateReport());
    }
}