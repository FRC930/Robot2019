/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/* This class is tasked with controlling the motor controllers on the drive train. *You need to create Drive object with the constructor.
    Methods:
        *run(double stickX, double stickY) - input joystick values, converts and sends to runAt() for Arcade drive
        *runAt(double leftSpeed, double rightSpeed) - input Arcade drive values to control motor controllers
        *getLeftSpeed() - obtain value for left motor controllers
        *getRightSpeed() - obtain value for right motor controllers
        *driveTrainLimiting(boolean buttonToggle) - toggle different values for for max amperage
            and max ramp speed(time minimum from 0 to max speed)
*/

public class Drive {
    // Variables for each Spark Max
    private CANSparkMax left1;
    private CANSparkMax left2;
    private CANSparkMax left3;

    private CANSparkMax right1;
    private CANSparkMax right2;
    private CANSparkMax right3;
    // Sets variables for motor controller limits
    private double rampRate;
    private int currentLimit;
    // Static flags for checking if instance was already created
    private static Drive lastInstance = null;

    // Class constructor for the robot
    private Drive() {
    }

    // Call to get a single instance of Drive
    static public Drive getInstance() {
        if (lastInstance == null) {
            lastInstance = new Drive();
            return lastInstance;
        } else {
            return lastInstance;
        }
    }

    // Call to set default Spark Max values
    public void setMotorControllers() {
        setMotorControllers(new CANSparkMax(Constants.DRIVE_LEFT1_ID, MotorType.kBrushless),
                new CANSparkMax(Constants.DRIVE_LEFT2_ID, MotorType.kBrushless),
                new CANSparkMax(Constants.DRIVE_LEFT3_ID, MotorType.kBrushless),
                new CANSparkMax(Constants.DRIVE_RIGHT1_ID, MotorType.kBrushless),
                new CANSparkMax(Constants.DRIVE_RIGHT2_ID, MotorType.kBrushless),
                new CANSparkMax(Constants.DRIVE_RIGHT3_ID, MotorType.kBrushless));
    }

    // Set values for Spark Max's
    public void setMotorControllers(CANSparkMax Left1, CANSparkMax Left2, CANSparkMax Left3, CANSparkMax Right1,
            CANSparkMax Right2, CANSparkMax Right3) {
        // Gives each Spark Max their proper values
        left1 = Left1;
        left2 = Left2;
        left3 = Left3;

        right1 = Right1;
        right2 = Right2;
        right3 = Right3;
        // Sets values for motor controller limits
        rampRate = Constants.DRIVE_RAMP_RATE_MAX;
        currentLimit = Constants.DRIVE_CURRENT_LIMIT_MAX;
        // Mirror primary motor controllers on each side
        left2.follow(left1);
        left3.follow(left1);

        right2.follow(right1);
        right3.follow(right1);
        // Sends motor controller limits to Dashboard for later reference
        SmartDashboard.putNumber("rampRate", 0.1);
        SmartDashboard.putNumber("currentLimit", 35);
    }

    // Use to calculate Arcade drive values given stick values
    public void run(double stickX, double stickY) {
        // Joystick deadband
        if (Math.abs(stickX) < Constants.DRIVE_DEADBAND_JOYSTICK) {
            stickX = 0;
        }
        if (Math.abs(stickY) < Constants.DRIVE_DEADBAND_JOYSTICK) {
            stickY = 0;
        }
        // Cubing values to create smoother function
        stickX = -Math.pow(stickX, 3);
        stickY = Math.pow(stickY, 3);
        stickX *= Constants.DRIVE_TURNING_MULTIPLIER;
        // Sets for use in motor controllers as Arcade values
        runAt((stickY + stickX), -(stickY - stickX));
    }

    // Given Arcade value arguments and sends to motor controllers
    public void runAt(double leftSpeed, double rightSpeed) {
        left1.set(leftSpeed);
        right1.set(rightSpeed);
    }

    // Returns left speed
    public double getLeftSpeed() {
        return left1.get();
    }

    // Returns right speed
    public double getRightSpeed() {
        return right1.get();
    }

    // Sets max current and ramp rate(min. time from 0 to max speed) given button
    // input
    public void driveTrainLimiting(boolean buttonToggle) {
        // Obtains values from Dashboard
        rampRate = SmartDashboard.getNumber("rampRate", Constants.DRIVE_RAMP_RATE_MAX);
        currentLimit = (int) SmartDashboard.getNumber("currentLimit", Constants.DRIVE_CURRENT_LIMIT_MAX);
        // Sets limits for motor controllers given button value
        if (buttonToggle) {
            left1.setSmartCurrentLimit(currentLimit);
            left2.setSmartCurrentLimit(currentLimit);
            left3.setSmartCurrentLimit(currentLimit);
            right1.setSmartCurrentLimit(currentLimit);
            right2.setSmartCurrentLimit(currentLimit);
            right3.setSmartCurrentLimit(currentLimit);

            left1.setOpenLoopRampRate(rampRate);
            left2.setOpenLoopRampRate(rampRate);
            left3.setOpenLoopRampRate(rampRate);
            right1.setOpenLoopRampRate(rampRate);
            right2.setOpenLoopRampRate(rampRate);
            right3.setOpenLoopRampRate(rampRate);
        } else {
            left1.setSmartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT_MAX);
            left2.setSmartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT_MAX);
            left3.setSmartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT_MAX);
            right1.setSmartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT_MAX);
            right2.setSmartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT_MAX);
            right3.setSmartCurrentLimit(Constants.DRIVE_CURRENT_LIMIT_MAX);

            left1.setOpenLoopRampRate(Constants.DRIVE_RAMP_RATE_MAX);
            left2.setOpenLoopRampRate(Constants.DRIVE_RAMP_RATE_MAX);
            left3.setOpenLoopRampRate(Constants.DRIVE_RAMP_RATE_MAX);
            right1.setOpenLoopRampRate(Constants.DRIVE_RAMP_RATE_MAX);
            right2.setOpenLoopRampRate(Constants.DRIVE_RAMP_RATE_MAX);
            right3.setOpenLoopRampRate(Constants.DRIVE_RAMP_RATE_MAX);
        }
    }
}
