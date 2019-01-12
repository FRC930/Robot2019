/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Add your docs here.
 */
public class Drive {

    private static CANSparkMax left1;
    private static CANSparkMax left2;
    private static CANSparkMax left3;
    private static CANSparkMax right1;
    private static CANSparkMax right2;
    private static CANSparkMax right3;


    public static void init() {

        left1 = new CANSparkMax(1, MotorType.kBrushless);
        left2 = new CANSparkMax(2, MotorType.kBrushless);
        left3 = new CANSparkMax(3, MotorType.kBrushless);
        right1 = new CANSparkMax(4, MotorType.kBrushless);
        right2 = new CANSparkMax(5, MotorType.kBrushless);
        right3 = new CANSparkMax(6, MotorType.kBrushless);
        
        left2.follow(left1);
        left3.follow(left1);
        
        right2.follow(right1);
        right3.follow(right2);

    }

    public static void run(double stickX, double stickY) {
        
        stickX = Math.pow(stickX,3);
        stickY = Math.pow(stickY,3);

        if(Math.abs(stickX) < Constants.DRIVE_THRESHOLD_JOYSTICK){
            stickX = 0;
        }
        if(Math.abs(stickY) < Constants.DRIVE_THRESHOLD_JOYSTICK){
            stickY = 0;
        }

        runAt((stickY+stickX), (stickY-stickX));

    }
    public static void runAt(double leftSpeed, double rightSpeed) {
        
        left1.set(leftSpeed);
        right2.set(rightSpeed);

    }
    
}
