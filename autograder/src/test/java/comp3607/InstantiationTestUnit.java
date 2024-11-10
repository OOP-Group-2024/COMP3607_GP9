package comp3607;

/*import org.junit.jupiter.api.Test;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

public class InstantiationTestUnit {

    @Test
    public void testInstantiationWithValidFieldValue() {
        InstantiationTest instantiationTest = new InstantiationTest(MyClass.class, "name", "John Doe",
            List.of(String.class, int.class), List.of("John Doe", 30));
        instantiationTest.checkInstantiation();
    }

    @Test
    public void testInstantiationWithIncorrectFieldValue() {
        InstantiationTest instantiationTest = new InstantiationTest(MyClass.class, "name", "Jane Doe",
            List.of(String.class, int.class), List.of("John Doe", 30));
        assertThrows(AssertionError.class, instantiationTest::checkInstantiation,
            "Expected AssertionError when field value does not match");
    }

    @Test
    public void testInstantiationWithNoParameters() {
        InstantiationTest instantiationTest = new InstantiationTest(MyClass.class, "name", null,
            List.of(), List.of());
        instantiationTest.checkInstantiation();
    }
}*/
//import comp3607.InstantiationTest;
//import comp3607.Report;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class InstantiationTestUnit {

    private InstantiationTest instantiationTest;
    private Report report;

    @BeforeEach
    public void setUp() {
        String fieldName = "someField";
        Object[] args = {42, "Sample"}; // Example constructor arguments
        Object expectedValue = 42; // Expected value of `someField`
        Class<?>[] parameterTypes = {int.class, String.class}; // Example parameter types
        instantiationTest = new InstantiationTest(fieldName, args, expectedValue, parameterTypes);
        report = new Report();
    }

    @Test
    public void testCheckInstantiation() {
        assertDoesNotThrow(() -> instantiationTest.CheckInstantiation(InstantiationTest.class, report));
    }
}

