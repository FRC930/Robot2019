package frc.robot;

import static org.mockito.ArgumentMatchers.doubleThat;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.withSettings;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentMatcher;

/**
 * DriveTest Tests interfaces in the Drive class
 */
public class DriveTest {
    private Drive drive;
    private CANSparkMax right1;
    private CANSparkMax right2;
    private CANSparkMax right3;
    private CANSparkMax left1;
    private CANSparkMax left2;
    private CANSparkMax left3;

    /**
     * This method sets the hardware to new mocks
     */
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

    /**
     * This method sets the hardware for the drive and gets the instance from the
     * Drive class again
     */
    @Before
    public void setup() {
        this.drive = Drive.getInstance();
        this.setHardware();
        this.drive.setMotorControllers(left1, left2, left3, right1, right2, right3);
    }

    /**
     * This test checks that the robot holds to the joystick deadband If the robot
     * sets the motor controlers to a non-zero value, the test will throw a custom
     * {@link SpeedException}
     */
    @Test
    public void rightJoystick() {
        doThrow(new SpeedException()).when(this.left1).set(doubleThat(new NonZeroDouble()));
        // No joystick movement
        this.drive.run(0, 0);
        // Very small joystick movement just below the deadband, still shouldn't be
        // enough to trigger movement
        this.drive.run(0.000123, 0);
        this.drive.run(-0.000123, 0);

        // Change the mock to do nothing
        doNothing().when(this.left1).set(doubleThat(new NonZeroDouble()));

        // Change when exceptions are thrown
        doThrow(new SpeedException()).when(this.left1).set(doubleThat(new ZeroDouble()));

        // Run at the deadband
        // The robot should set the motor controllers to a non-zero value
        this.drive.run(0.000124, 0);
        this.drive.run(-0.000124, 0);

        // Run at a value above the deadband
        this.drive.run(0.001, 0);
        this.drive.run(-0.001, 0);
    }

    /**
     * This test checks that the robot holds to the joystick deadband If the robot
     * sets the motor controlers to a non-zero value, the test will throw a custom
     * {@link SpeedException}
     */
    @Test
    public void leftJoystick() {
        doThrow(new SpeedException()).when(this.right1).set(doubleThat(new NonZeroDouble()));
        // No joystick movement
        this.drive.run(0, 0);
        // Very small joystick movement, still shouldn't be enough to trigger movement
        this.drive.run(0.000123, 0);
        this.drive.run(-0.000123, 0);

        // Change the mock to do nothing
        doNothing().when(this.right1).set(doubleThat(new NonZeroDouble()));

        // Change when exceptions are thrown
        doThrow(new SpeedException()).when(this.right1).set(doubleThat(new ZeroDouble()));

        // Run at the deadband
        // The robot should set the motor controllers to a non-zero value
        this.drive.run(0.000124, 0);
        this.drive.run(-0.000124, 0);

        // Run at a value above the deadband
        this.drive.run(0.001, 0);
        this.drive.run(-0.001, 0);
    }

    /**
     * Test both of the joysticks to make sure that the robot sets the values
     * correctly If the robot sets the motor controllers to a wrong value, it will
     * throw a {@link SpeedException}
     */
    @Test
    public void testBothJoysticks() {
        doThrow(new SpeedException()).when(this.left1).set(doubleThat(new NonZeroDouble()));
        doThrow(new SpeedException()).when(this.right1).set(doubleThat(new NonZeroDouble()));
        // No joystick movement
        this.drive.run(0, 0);
        // Very small joystick movement, still shouldn't be enough to trigger movement
        this.drive.run(0.000123, 0.000123);
        this.drive.run(-0.000123, -0.000123);

        // Change the mock to do nothing
        doNothing().when(this.left1).set(doubleThat(new NonZeroDouble()));
        doNothing().when(this.right1).set(doubleThat(new NonZeroDouble()));

        // Change when exceptions are thrown
        doThrow(new SpeedException()).when(this.left1).set(doubleThat(new ZeroDouble()));
        doThrow(new SpeedException()).when(this.right1).set(doubleThat(new ZeroDouble()));

        // Run at the deadband
        // The robot should set the motor controllers to a non-zero value
        this.drive.run(0.000124, 0.000124);
        this.drive.run(-0.000124, 0.000124);

        // Run at a value above the deadband
        this.drive.run(0.001, 0.001);
        this.drive.run(-0.001, 0.001);
    }

    /**
     * Test to make sure that the robot moves when it is supposed to. If the robot
     * sets the motor controllers to zero when the joystick it being pressed the
     * method will throw a custom {@link SpeedException}.
     */
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

/**
 * SpeedException
 * 
 * This class extends RuntimeException, and should be thrown when the speed sent
 * to the motor controllers is not what expected.
 */
class SpeedException extends RuntimeException {
    // Default generated serial version uid
    private static final long serialVersionUID = -1669598633579527024L;

    /**
     * Default constructor Creates a new RuntimeException with no arguments.
     */
    public SpeedException() {
        super();
    }

    public SpeedException(String s) {
        super(s);
    }
}

/**
 * Argument matcher to check for doubles that are zero. It uses an epsilon
 * generated by {@link Math.ulp} to check equality.
 */
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

class CustomDouble implements ArgumentMatcher<Double> {
    private double number;

    public CustomDouble(double d) {
        this.number = d;
    }

    @Override
    public boolean matches(Double argument) {
        if (Math.abs(argument - this.number) < Math.ulp(1.0)) {
            return true;
        } else {
            return false;
        }
    }

}