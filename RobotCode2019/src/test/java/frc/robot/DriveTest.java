package frc.robot;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.withSettings;

import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentMatcher;

/**
 * DriveTest
 */
public class DriveTest {
    @Test
    public void testDeadband() throws Exception {
        // CANSparkMax left1 = mock(CANSparkMax.class, withSettings()
        //         .useConstructor(Constants.DRIVE_LEFT1_ID, MotorType.kBrushless).defaultAnswer(Answers.RETURNS_MOCKS));
        // CANSparkMax right1 = mock(CANSparkMax.class, withSettings()
        //         .useConstructor(Constants.DRIVE_RIGHT1_ID, MotorType.kBrushless).defaultAnswer(Answers.RETURNS_MOCKS));
        // Drive drive = mock(Drive.class, withSettings().useConstructor(left1, left1, left1, right1, right1, right1));
        // drive.run(0.000123, 0);
    }
}

class NonZeroDouble implements ArgumentMatcher<Double> {

    @Override
    public boolean matches(Double argument) {
        return argument == 0;
    }

}