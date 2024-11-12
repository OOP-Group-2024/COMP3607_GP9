package comp3607;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import org.junit.jupiter.api.Assertions;

public class Simulator extends Test{

    public Simulator(){
        
    }

    public void simulate(Report report){
        
        ChatBotPlatform platform = buildPlatform();
        List<String> responses = buildResponses();

        //Testing the first getChatBotList
        try{
            boolean contains = checkString(platform.getChatBotList().toLowerCase(), "0 ChatGPT-3.5 1 LLama 2 Mistral7B 3 Bard 4 Claude 5 Solar 6 ChatGPT-3.5".toLowerCase());
            Assertions.assertTrue(contains);
            report.addPassedTest(String.format("Simulation: %-26s Returned the correct values, in correct order", "getChatBotList(1)"));
        }catch (AssertionError e){
            report.addError(String.format("Simulation: %-26s Failed to return correct list values", "getChatBotList(1)"));
        }

        //Interacting with Bot 15 times
        try{
            for(int i = 0; i < 15; i++){
                String message = platform.interactWithBot(3, "hi");
                boolean contains = checkString(message.toLowerCase(), responses.get(i).toLowerCase());
                Assertions.assertTrue(contains);
                report.addPassedTest(String.format("Simulation: %-26s Returned the correct values, in correct order", "interactWithBot("+ i +")"));
            }
        }catch (AssertionError e){
            report.addError(String.format("Simulation: %-26s Returned incorrect values, or incorrect order", "interactWithBot"));
        }

        //Testing the second getChatBotList
        try{
            boolean contains = checkString(platform.getChatBotList().toLowerCase(), "0 ChatGPT-3.5 1 LLama 2 Mistral7B 3 Bard 4 Claude 5 Solar 6 ChatGPT-3.5".toLowerCase());
            Assertions.assertTrue(contains);
            report.addPassedTest(String.format("Simulation: %-26s Returned the correct values, in correct order", "getChatBotList(2)"));
        }catch (AssertionError e){
            report.addError(String.format("Simulation: %-26s Failed to return correct list values", "getChatBotList(2)"));
        }

        //Checking message number consistency
        try{
            ChatBot c = new ChatBot();
            Field field = ChatBot.class.getDeclaredField("messageNumber");
            field.setAccessible(true);
            int val = (int) field.get(c);
            Assertions.assertEquals(val, 10);
            report.addPassedTest(String.format("Simulation: %-26s Accurately incremented messageNumber and stopped at limit", "MessageNumber"));
        } catch(AssertionError | NoSuchFieldException | IllegalAccessException e){
            report.addError(String.format("Simulation: %-26s Incorrect message number after iteration", "MessageNumber"));
        }

    }
    
    protected void executeTest(Class<?> clazz, Report report) {
        try{
            simulate(report);
        }catch (AssertionError e){
            report.addPassedTest(String.format("Simulation: %-26s Failed", "Error on main"));
        }
    }
 
    
    //Stricter Test - Tests for the correct order of specified values
    private boolean checkString(String actual, String expectedWords){
        String[] words = expectedWords.split(" ");
        String[] actualWords = actual.split(" ");
        Stack<String> s = new Stack<>();

        for(int i=words.length-1; i>=0; i--){
            s.push(words[i]);
        }
        for (String actualWord : actualWords) {
            if (s.isEmpty()) 
                return true;
            if(s.peek().equals(actualWord))
                s.pop();
        }
        return s.isEmpty();
    }


    //Build a list of expected responses
    private List<String> buildResponses(){
        List<String> responses = new ArrayList<>();
        for(int i=0;i<10;i++){
            responses.add("Response from Bard");
        }
        for(int i=10;i<15;i++){
            responses.add("Daily Limit Reached.");
        }
        return responses;
    }

    //Create a platform object and add 6 classes
    private ChatBotPlatform buildPlatform(){
        ChatBotPlatform platform = new ChatBotPlatform();
        platform.addChatBot(0);
        platform.addChatBot(1);
        platform.addChatBot(2);
        platform.addChatBot(3);
        platform.addChatBot(4);
        platform.addChatBot(5);
        platform.addChatBot(6);
        return platform;
    }
}
