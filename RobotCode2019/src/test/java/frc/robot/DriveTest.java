package frc.robot;

import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.withSettings;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.mockito.ArgumentMatcher;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

/**
 * DriveTest
 */
public class DriveTest {
    private Drive drive;
    private CANSparkMax right1;
    private CANSparkMax right2;
    private CANSparkMax right3;
    private CANSparkMax left1;
    private CANSparkMax left2;
    private CANSparkMax left3;

    public void setHardware() {
        this.right1 = mock(CANSparkMax.class,
                withSettings().useConstructor(Constants.DRIVE_RIGHT1_ID, MotorType.kBrushless));
        this.right2 = mock(CANSparkMax.class,
                withSettings().useConstructor(Constants.DRIVE_RIGHT2_ID, MotorType.kBrushless));
        this.right3 = mock(CANSparkMax.class,
                withSettings().useConstructor(Constants.DRIVE_RIGHT3_ID, MotorType.kBrushless));
        this.left1 = mock(CANSparkMax.class,
                withSettings().useConstructor(Constants.DRIVE_LEFT1_ID, MotorType.kBrushless));
        this.left2 = mock(CANSparkMax.class,
                withSettings().useConstructor(Constants.DRIVE_LEFT2_ID, MotorType.kBrushless));
        this.left3 = mock(CANSparkMax.class,
                withSettings().useConstructor(Constants.DRIVE_LEFT3_ID, MotorType.kBrushless));
    }

    @Before
    public void setup() {
        this.drive = Drive.getInstance();
        this.setHardware();
        this.drive.setMotorControllers(left1, left2, left3, right1, right2, right3);
    }

    @Test
    public void testDeadband() {
        doThrow(new SpeedException()).when(this.left1).set(doubleThat(new NonZeroDouble()));
        doThrow(new SpeedException()).when(this.right1).set(doubleThat(new NonZeroDouble()));
        // No joystick movement
        this.drive.run(0, 0);
        // Very small joystick movement, still shouldn't be enough to trigger movement
        this.drive.run(0.000123, 0);
    }

    @Test
    public void testMovement() {
        doThrow(new SpeedException()).when(this.left1).set(doubleThat(new ZeroDouble()));
        doThrow(new SpeedException()).when(this.right1).set(doubleThat(new ZeroDouble()));
        // Test with the left joystick at zero
        // Right joystick at a positive value
        this.drive.run(0, 0.5);
        // Right joystick at a negative value
        this.drive.run(0, -0.5);
        // Test with the left joystick positive
        // Right joystick at a positive value
        this.drive.run(0.5, 0.5);
        // Right joystick at a negative value
        this.drive.run(0.5, -0.5);
        // Test with the left joystick negative
        // Right joystick at a positive value
        this.drive.run(-0.5, 0.5);
        // Right joystick at a negative value
        this.drive.run(-0.5, -0.5);

    }
}

class SpeedException extends RuntimeException {
    private static final long serialVersionUID = -1669598633579527024L;

    public SpeedException() {
        super();
    }

    public SpeedException(String s) {
        super(s);
    }
}

class ZeroDouble implements ArgumentMatcher<Double> {
    @Override
    public boolean matches(Double argument) {
        if (Math.abs(argument) <= Math.ulp(0)) {
            return true;
        } else {
            return false;
        }
    }
}

class NonZeroDouble implements ArgumentMatcher<Double> {
    @Override
    public boolean matches(Double argument) {
        if (Math.abs(argument) >= Math.ulp(0)) {
            return true;
        } else {
            return false;
        }
    }
}