package frc.robot;

import static org.mockito.ArgumentMatchers.anyDouble;
import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Answers;
import org.mockito.ArgumentMatcher;

/**
 * DriveTest
 */
public class DriveTest {
    static Drive drive;
    static NonZeroDouble nonZero;

    @BeforeClass
    public static void setup() {
        drive = mock(Drive.class, withSettings().defaultAnswer(Answers.CALLS_REAL_METHODS).useConstructor());
        nonZero = new NonZeroDouble();
    }

    @Test
    public void testDeadband() {
        doThrow(new NonZeroSpeedInputException()).when(drive).runAt(doubleThat(nonZero), doubleThat(nonZero));
        // No joystick movement
        drive.run(0, 0);
        // Very small joystick movement, still shouldn't be enough to trigger movement
        drive.run(0.000123, 0);
    }

    @Test
    public void testMovement() {
        doThrow(new NonZeroSpeedInputException()).when(drive).runAt(eq(0.0), eq(0.0));
        // Test with the left joystick at zero
        // Right joystick at a positive value
        drive.run(0, 0.5);
        // Right joystick at a negative value
        drive.run(0, -0.5);
        // Test with the left joystick positive
        // Right joystick at a positive value
        drive.run(0.5, 0.5);
        // Right joystick at a negative value
        drive.run(0.5, -0.5);
        // Test with the left joystick negative
        // Right joystick at a positive value
        drive.run(-0.5, 0.5);
        // Right joystick at a negative value
        drive.run(-0.5, -0.5);

    }
}

class NonZeroDouble implements ArgumentMatcher<Double> {

    @Override
    public boolean matches(Double argument) {
        return argument != 0;
    }

}

class NonZeroSpeedInputException extends RuntimeException {
    private static final long serialVersionUID = -1669598633579527024L;

    public NonZeroSpeedInputException() {
        super();
    }

    public NonZeroSpeedInputException(String s) {
        super(s);
    }
}