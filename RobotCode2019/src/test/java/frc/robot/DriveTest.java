package frc.robot;

import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentMatcher;

/**
 * DriveTest
 */
public class DriveTest {
    @Test
    public void testDeadband() throws Exception {
        CANSparkMax left1 = mock(CANSparkMax.class, withSettings()
                .useConstructor(Constants.DRIVE_LEFT1_ID, MotorType.kBrushless).defaultAnswer(Answers.RETURNS_MOCKS));
        CANSparkMax right1 = mock(CANSparkMax.class, withSettings()
                .useConstructor(Constants.DRIVE_RIGHT1_ID, MotorType.kBrushless).defaultAnswer(Answers.RETURNS_MOCKS));
        Drive drive = mock(Drive.class);
        NonZeroDouble nonZero = new NonZeroDouble();
        // doNothing().when(drive).runAt(argThat(nonZero), argThat(nonZero));
        drive.run(0.000123, 0);
    }
}

class NonZeroDouble implements ArgumentMatcher<Double> {

    @Override
    public boolean matches(Double argument) {
        return argument == 0;
    }

}