package comp3607;


import java.util.List;

public class SimulationTest extends FileTest{
    
    private final TestGroup chatBotSimulation;
    protected final Object[] none = List.of().toArray();


    public SimulationTest(){
        this.chatBotSimulation = new TestGroup();
    }

    @Override
    public void setUp(Report report, Class<?> clazz){
        addMethodTest("public static", "void", List.of(String[].class), "main", chatBotSimulation, 1.0f);
        try {
            clazz.getDeclaredMethod("main", String[].class);
            report.setMainExists(true);
        } catch (NoSuchMethodException | SecurityException e) {
            report.setMainExists(false);
            report.addError("No main method in Simulation, cannot run tests");
        }
        addSimulator(chatBotSimulation);
        chatBotSimulation.executeTest(clazz, report);
    }


    
}
