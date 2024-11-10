package comp3607;

import java.io.IOException;

public class App {
    public static void main(String[] args) {
        System.out.println("Testing:\n");
        try {
            RunAssignmentTest.runTests();
        } catch (IOException e) {
            
        }
    }
}
