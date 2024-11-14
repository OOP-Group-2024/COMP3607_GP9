package comp3607;

public class FileContext {
    private FileTest fileTest;
    private Class clazz;

    public void setTest(FileTest fileTest){
        this.fileTest = fileTest;
    }

    public void setClass(Class clazz){
        this.clazz=clazz;
    }

    public void testFile(Report report){
        fileTest.setUp(report, clazz);
    }
}
