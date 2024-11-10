package comp3607;

public class ChatBot {

    private String chatBotName;
    private int numResponsesGenerated;
    private int messageLimit;
    private int messageNumber;

    public ChatBot() {
        this.chatBotName = ChatBotGenerator.generateChatBotLLM(6) ; 
    }

    //public ChatBot(int messageLimit) {
    public ChatBot(int num) {
        this.chatBotName = ChatBotGenerator.generateChatBotLLM(num);
    }

    public String getChatBotName() {
        return chatBotName;
    }

    public int getNumResponsesGenerated() {
        return numResponsesGenerated;
    }

    public int getTotalNumResponsesGenerated() {
        numResponsesGenerated = numResponsesGenerated - messageNumber;
        return numResponsesGenerated;
    }

    public int getTotalNumResponsesRemaining() {
        return messageLimit - numResponsesGenerated;
    }

    public boolean limitReached() {
        return numResponsesGenerated >= messageLimit;
    }

    public String prompt(String userInput) {
        return "Response to: " + userInput;
    }

    public String toString() {
        return "ChatBot: " + chatBotName;
    }
}