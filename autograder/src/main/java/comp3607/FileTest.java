package comp3607;

import java.util.List;

public abstract class FileTest {
    /* 
    private TestGroup chatBotPlatform = new TestGroup();
    private TestGroup chatBot = new TestGroup();
    private TestGroup chatBotGenerator = new TestGroup();
    
    */
    protected Report report = new Report();
    
    public abstract void setUp(Report report, Class clazz); 


    //Method to add variable test
    protected void addVariableTest(String accessModifier, String type, String variableName, TestGroup testGroup, float totalMarks){
        VariableCriteria criteria = new VariableCriteria(accessModifier, type);
        VariableTest test = new VariableTest(variableName, criteria, totalMarks);
        testGroup.addTest(test);
    }

    //Method to add a method test
    protected void addMethodTest(String accessModifier, String returnType, 
                                List<Class<?>> parameters, String methodName, TestGroup testGroup, float totalMarks){
        MethodCriteria criteria = new MethodCriteria(accessModifier, returnType, parameters);
        MethodTest test = new MethodTest(methodName, criteria, totalMarks);
        testGroup.addTest(test);
    }

    //Method to add a constructor test
    protected void addConstructorTest(List<Class<?>> parameters, List<Object> inputs, TestGroup testGroup, float totalMarks){
        ConstructorTest test = new ConstructorTest(parameters, inputs, totalMarks);
        testGroup.addTest(test);
    }

    //Method to add an instantiation test
    protected void addInstantiationTest(String fieldName, List<Object> args, Object expectedValue, 
    List<Class<?>> parameters, TestGroup testGroup, float totalMarks){
        Class<?>[] params = parameters.toArray(new Class<?>[0]);
        InstantiationTest test = new InstantiationTest(fieldName, args.toArray(), expectedValue, params, totalMarks);
        testGroup.addTest(test);
    }


    //Method to add a behavioural test
    protected void addBehaviourTest(List<String> names, List<Object[]> input, List<Class<?>> parameters,
     Object expectedValue, Object expectedReturn, TestGroup testGroup, float totalMarks){
        Class<?>[] params = parameters.toArray(new Class<?>[0]);
        ValueTest test = new ValueTest(names, input, params, expectedValue, (totalMarks/2));
        testGroup.addTest(test);
        ReturnTest test2 = new ReturnTest(names, input, params, expectedReturn, (totalMarks/2));
        testGroup.addTest(test2);
    }
    
    protected void addSimulator(TestGroup testGroup){
        Simulator sim = new Simulator();
        testGroup.addTest(sim);
    }
    
    //Print the report
    public void printReport(){
        System.out.println(report.generateReport());
    }
}
