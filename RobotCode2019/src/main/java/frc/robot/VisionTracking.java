/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**
 * Rotate the robot towards a vision target on the cargo ship or
 * rocket to properly line up the robot to place hatches and score cargo
 */
public class VisionTracking {

    // network table used to get data from the limelight
    private static NetworkTable limelightTable = NetworkTableInstance.getDefault().getTable("limelight");

    // get the angle of the horizontal offset between the camera's crosshair and the target's crosshair from a network table
    private static NetworkTableEntry tx = limelightTable.getEntry("tx");

    // turning speed of the robot
    private static double horizontalSpeed;

    // the horizontal angle offset between the camera's crosshair and the target's crosshair represented as a double
    private static double horizontalAngle;

    static {
        
    }

    public static void init() {

    }

    public static void run(boolean isButtonPressed) {

        // get the horizontal angle offest from the network table and store it as a double
        horizontalAngle = tx.getDouble(Constants.DEFAULT_LIMELIGHT_RETURN_VALUE);

        /** 
         * if the A button is currently pressed, turn on the limelight's LEDs,
         * and if the A button is not pressed, turn off the LEDs
         *
         * getEntry("ledMode") is used to get the LedMode property from the NetworkTable
         * setNumber() is used to set the state of the LedMode property (the integer 1 sets the LEDs off, 3 sets the LEDs on)
         * 
         * ?: is a terenary operator in Java, it is basically a one-line if-else statement
         * To use it, give it a boolean variable, the do ?. Then after that, you can use it as
         * an if-else statement. 
         *
         * stick.getRawButton(A_BUTTON) ? 1 : 0
         * In this example, we use stick.getRawButton(A_BUTTON) as our boolean variable
         * if stick.getRawButton(A_BUTTON) is down (value is true), then it will return 1
         * if stick.getRawButton(A_BUTTON) is up (value is false), then it will return 0
         * 
         * With the 0 and 1 then, we use it to do some math. 
         * When the stick.getRawButton(A_BUTTON) is down (true), it returns 1
         * 1 + (2 * 1) = 3. 3 is sent into .setNumber(), which turns the LEDs on
         */
        limelightTable.getEntry("ledMode").setNumber(1 + (2 * (isButtonPressed ? 1 : 0)));

        // code block will run if the driver's passed button is pressed
        if(isButtonPressed) {
            /** 
             * rotate the robot towards the target if horizontal angle is greater 
             * than the horizontal angle threshold on either side of the target
             */
            horizontalSpeed = rotate(horizontalAngle);
        }

        // sends the rotating speeds to the motors to rotate the robot
        Drive.runAt(-horizontalSpeed, -horizontalSpeed);
    }

    /**  
     * The rotate method returns a turning speed to rotate the robot based on the 
     * horizontal angle offset between the target's crosshair and the calibrated 
     * crosshair of the limelight.
     *   
     * The robot's turning speed is proportional to the angle offset, which means 
     * as the limelight gets closer to the target, the turning speed is much slower
     * and more precise.
     *
     * Once the angle offset is within a specified range, the robot will no longer
     * turn, meaning the robot will stop turning when the limelight's crosshair and
     * the target's crosshair are very near to each other.
     */
    private static double rotate(double xAngle) {

        // the rotational adjustment for the robt
        double horizontalAdjustment = 0;
   
        // only rotate the robot if the horizontal angle offset is bigger than a threshold
        if(Math.abs(xAngle) > Constants.HORIZONTAL_ANGLE_THRESHOLD) 
            horizontalAdjustment = Constants.DEFAULT_HORIZONTAL_SPEED * xAngle;
   
        // set a limit for the rotation speed
        if(horizontalAdjustment > Constants.HORIZONTAL_SPEED_THRESHOLD)
           horizontalAdjustment = Constants.HORIZONTAL_SPEED_THRESHOLD;
        else if(horizontalAdjustment < -Constants.HORIZONTAL_SPEED_THRESHOLD)
           horizontalAdjustment = -Constants.HORIZONTAL_SPEED_THRESHOLD;
   
        return horizontalAdjustment;
    }
} // end of class
