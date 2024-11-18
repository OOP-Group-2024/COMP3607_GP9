package comp3607;

public abstract class Test {
    //Template design pattern abstract class
    public void runTest(Class<?> clazz, Report report) {
        
        setup(clazz);
        executeTest(clazz, report);
        cleanUp(clazz);

    }

    protected void setup(Class<?> clazz) {
        System.out.println("");
    }

    protected abstract void executeTest(Class<?> clazz, Report report);

    protected void cleanUp(Class<?> clazz) {
    }
}
