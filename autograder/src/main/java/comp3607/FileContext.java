package comp3607;

public class FileContext {
    private FileTest fileTest;

    public void setTest(FileTest fileTest){
        this.fileTest = fileTest;
    }

    public void testFile(Report report){
        fileTest.setUp(report);
    }
}
