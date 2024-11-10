package comp3607;

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

