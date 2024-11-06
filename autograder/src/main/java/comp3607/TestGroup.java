package comp3607;

import java.util.ArrayList;
import java.util.List;

public class TestGroup extends Test{

    private final List<Test> tests = new ArrayList<>();

    public void addTest(Test test) {
        tests.add(test);
    }
    @Override
    protected void executeTest(Class<?> clazz, Report report) {
        for(Test test : tests) {
            test.runTest(clazz, report);
        }
    }
    
}
