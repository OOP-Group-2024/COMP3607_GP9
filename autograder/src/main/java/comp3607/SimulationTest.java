package comp3607;


import java.util.List;

public class SimulationTest extends FileTest{
    
    private TestGroup chatBotSimulation;
    protected final Object[] none = List.of().toArray();


    public SimulationTest(){
        this.chatBotSimulation = new TestGroup();
    }

    public void setUp(Report report){
        addMethodTest("public static", "void", List.of(String[].class), "main", chatBotSimulation);
        addSimulator(chatBotSimulation);
        chatBotSimulation.executeTest(ChatBotSimulation.class, report);
    }


    
}
