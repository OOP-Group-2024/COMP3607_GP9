package comp3607;

public class ChatBot {

    private String chatBotName;
    private int numResponsesGenerated;
    private final int messageLimit = 10;
    private int messageNumber = 0;

    public ChatBot() {
        this.chatBotName = ChatBotGenerator.generateChatBotLLM(0);
        this.numResponsesGenerated = 0;
        this.messageNumber = 0;
    }

    public ChatBot(int LLMCode) {
        this.chatBotName = ChatBotGenerator.generateChatBotLLM(LLMCode);
    }

    public String getChatBotName() {
        return chatBotName;
    }

    public int getNumResponsesGenerated() {
        return numResponsesGenerated;
    }

    public int getTotalNumResponsesGenerated() {
        return messageNumber;
    }

    public int getTotalNumResponsesRemaining() {
        return messageLimit - messageNumber;
    }

    public boolean limitReached() {
        return messageNumber >= messageLimit;
    }

    private String generateResponse() {
        if (limitReached()) {
            return "Daily Limit Reached. Wait 24 hours to resume chatbot usage.";
        }
        this.numResponsesGenerated++;
        this.messageNumber++;
        return "Message";
    }


    public String prompt(String requestMessage) {
        if (this.limitReached()) {
            return "Daily Limit Reached. Wait 24 hours to resume chatbot usage";
        }

        return generateResponse();
    }


    public String toString() {
        return "ChatBot Name: " + chatBotName + " Number Messages Used: " + messageNumber;
    }
}
