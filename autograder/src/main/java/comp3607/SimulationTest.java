package comp3607;


import java.util.List;

public class SimulationTest extends FileTest{
    
    private final TestGroup chatBotSimulation;
    protected final Object[] none = List.of().toArray();


    public SimulationTest(){
        this.chatBotSimulation = new TestGroup();
    }

    @Override
    public void setUp(Report report){
        addMethodTest("public static", "void", List.of(String[].class), "main", chatBotSimulation, 1.0f);
        addSimulator(chatBotSimulation);
        chatBotSimulation.executeTest(ChatBotSimulation.class, report);
    }


    
}
