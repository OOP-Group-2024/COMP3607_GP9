package comp3607;

import java.io.IOException;
import java.util.List;

import com.itextpdf.text.DocumentException;

public class AssignmentTest {
    private TestGroup chatBotPlatform = new TestGroup();
    private TestGroup chatBot = new TestGroup();
    private Report report = new Report();

    public void setUp(Class<?> chatBotClass, Class<?> chatBotPlatformClass, String destinationDir, String fileName) {
        // Defining tests for ChatBotPlatform
        addVariableTest("private", "ArrayList", "bots", chatBotPlatform);
        addConstructorTest(List.of(), List.of(), chatBotPlatform);
        addMethodTest("public", "boolean", List.of(int.class), "addChatBot", chatBotPlatform);
        addMethodTest("public", "java.lang.String", List.of(), "getChatBotList", chatBotPlatform);
        addMethodTest("public", "java.lang.String", List.of(int.class, String.class), "InteractWithBot", chatBotPlatform);
        chatBotPlatform.executeTest(chatBotPlatformClass, report);

        // Defining tests for ChatBot
        addVariableTest("private", "String", "chatBotName", chatBot);
        addVariableTest("private", "int", "numResponsesGenerated", chatBot);
        addVariableTest("private", "int", "messageLimit", chatBot);
        addVariableTest("private", "int", "messageNumber", chatBot);
        addConstructorTest(List.of(), List.of(), chatBot);
        addConstructorTest(List.of(int.class), List.of(), chatBot);
        addMethodTest("public", "java.lang.String", List.of(), "getChatBotName", chatBot);
        addMethodTest("public", "int", List.of(), "getNumResponsesGenerated", chatBot);
        addMethodTest("public", "int", List.of(), "getTotalNumResponsesGenerated", chatBot);
        addMethodTest("public", "int", List.of(), "getTotalNumResponsesRemaining", chatBot);
        addMethodTest("public", "boolean", List.of(), "limitReached", chatBot);
        addMethodTest("public", "java.lang.String", List.of(String.class), "prompt", chatBot);
        addMethodTest("public", "java.lang.String", List.of(), "toString", chatBot);
        chatBot.executeTest(chatBotClass, report);

        // Generate PDF report after setting up and running tests
        generatePDFReport(destinationDir, fileName);
    }

    // Method to add variable test
    private void addVariableTest(String accessModifier, String type, String variableName, TestGroup testGroup){
        VariableCriteria criteria = new VariableCriteria(accessModifier, type);
        VariableTest test = new VariableTest(variableName, criteria);
        testGroup.addTest(test);
    }

    // Method to add a method test
    private void addMethodTest(String accessModifier, String returnType, 
                                List<Class<?>> parameters, String methodName, TestGroup testGroup){
        MethodCriteria criteria = new MethodCriteria(accessModifier, returnType, parameters);
        MethodTest test = new MethodTest(methodName, criteria);
        testGroup.addTest(test);
    }

    // Method to add a constructor test
    private void addConstructorTest(List<Class<?>> parameters, List<Object> inputs, TestGroup testGroup){
        ConstructorTest test = new ConstructorTest(parameters, inputs);
        testGroup.addTest(test);
    }

    // Generate and save the report to a PDF file
    public void generatePDFReport(String destinationDir, String fileName) {
        String reportContent = report.generateReport(); // Generate report as a String
        try {
            GeneratePDF.generatePDF(reportContent, destinationDir, fileName); // Call to GeneratePDF class
        } catch (DocumentException | IOException e) {
            System.err.println("Error generating PDF report: " + e.getMessage());
        }
    }

    // Optionally, print the report to the console as well
    public void printReport(){
        System.out.println(report.generateReport());
    }
}
