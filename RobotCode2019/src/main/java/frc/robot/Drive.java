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

/**
 * Controlling drivetrain during driver control
 */
public class Drive {

    // Drivetrain motor controllers
    private static final CANSparkMax left1 = new CANSparkMax(1, MotorType.kBrushless);
    private static final CANSparkMax left2 = new CANSparkMax(2, MotorType.kBrushless);
    private static final CANSparkMax left3 = new CANSparkMax(3, MotorType.kBrushless);
    private static final CANSparkMax right1 = new CANSparkMax(4, MotorType.kBrushless);
    private static final CANSparkMax right2 = new CANSparkMax(5, MotorType.kBrushless);
    private static final CANSparkMax right3 = new CANSparkMax(6, MotorType.kBrushless);
   
    private static double rampRate = 0;
    private static int currentLimit = 0;

    static {

        // Mirror primary motor controllers on each side
        left2.follow(left1);
        left3.follow(left1);
        
        right2.follow(right1);
        right3.follow(right2);
    }

    // To be initialized at robot startup
    public static void init() {
        
        SmartDashboard.putNumber("rampRate", 0);

        (int)SmartDashboard.putNumber("currentLimit", 0);
    }

    /**
     * To be run during teleop periodic.
     * Gets driver joystick values as parameters.
     */
    public static void run(double stickX, double stickY) {
        
        // Cubing values to create smoother function
        stickX = -Math.pow(stickX,3);
        stickY = Math.pow(stickY,3);

        // Joystick deadband
        if(Math.abs(stickX) < Constants.DRIVE_THRESHOLD_JOYSTICK){
            stickX = 0;
        }
        if(Math.abs(stickY) < Constants.DRIVE_THRESHOLD_JOYSTICK){
            stickY = 0;
        }

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
  
        rampRate = SmartDashboard.getNumber("rampRate", 0);

        currentLimit = (int)SmartDashboard.getNumber("currentLimit", 0);

        // Arcade drive
        runAt((stickY+stickX), -(stickY-stickX));

    }

    /**
     * Sets speed of motors to specific values.
     * Gets velocities as parameters.
     */
    public static void runAt(double leftSpeed, double rightSpeed) {
        
        left1.set(leftSpeed);
        right1.set(rightSpeed);

    }
    
}
