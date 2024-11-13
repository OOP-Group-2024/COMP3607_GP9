package comp3607;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import org.junit.jupiter.api.Assertions;

public class VariableTest extends Test {
    private final VariableCriteria variableCriteria;
    private final String variableName;

    private final float totalMarks;
    
    private int checks = 0;
    private int checksPassed = 0;
    
    public VariableTest(String variableName, VariableCriteria variableCriteria, float totalMarks) {
        this.variableName = variableName;
        this.variableCriteria = variableCriteria;
        this.totalMarks = totalMarks;
    }


    //Catch assertion errors if check fails and add results to report

    public void checkAccessModifier(Field field, Report report) {
        checks++;
        String actualModifier = Modifier.toString(field.getModifiers());
        String expectedAccessModifier = variableCriteria.getExpectedAccessModifier();
        try{
            //Assertions.assertEquals(expectedAccessModifier, actualModifier);
            actualModifier = actualModifier.toLowerCase();
            //expectedAccessModifier = expectedAccessModifier.toLowerCase();
            Assertions.assertTrue(actualModifier.equals(expectedAccessModifier));
            report.addPassedTest(String.format("Variable: %-28s Correct access Modifier", variableName)); 
            checksPassed++;    
        } catch (AssertionError e) {
            report.addError(String.format("Variable: %-28s Incorrect access modifer. Expected - %s, Declared - %s", variableName ,expectedAccessModifier, actualModifier));
        }
    }

    public void checkType(Field field, Report report) {
        checks++;
        String actualType = field.getType().getSimpleName();
        String expectedType = variableCriteria.getExpectedType();
        try{
            Assertions.assertEquals(expectedType, actualType);
            report.addPassedTest(String.format("Variable: %-28s Correct type", variableName));  
            checksPassed++;  
        } catch (AssertionError e) {
            //report.addError("Variable: " + variableName + "\tIncorrect type. Expected - " + variableCriteria.getExpectedType() + ",   Declared - " + actualType);
            report.addError(String.format("Variable: %-28s Incorrect type. Expected - %s, Declared - %s", variableName, expectedType, actualType));
        }
    }

    //Once field exists, proceed with checks, if not then return 
    @Override
    public void executeTest(Class<?> clazz, Report report) {
        Field field=null;
        Field[]fields = clazz.getDeclaredFields();
        for(Field f : fields){
            if(variableName.toLowerCase().equals(f.getName().toLowerCase())){
                field = f;
                break;
            }
        }
        if(field == null){
            report.addError(String.format("Variable: %-28s Does not exist", variableName));
            return;
        }
        checkAccessModifier(field, report);
        checkType(field, report);

        float obtainedMarks = (checksPassed/checks) * totalMarks;
        report.addMarks(obtainedMarks);
        report.addSummary(String.format("Variable: %-28s Passed %d/%d Tests, Obtained %.2f marks", variableName, checksPassed, checks, obtainedMarks));
    }

    
}
