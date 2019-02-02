/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Lifts the robot onto the third platform during the Endgame
 */
public class Endgame {

    // Endgame Motor Controllers
    private static final CANSparkMax endgameLift = new CANSparkMax(1, MotorType.kBrushless);
    private static final CANSparkMax endgameLiftFollow1 = new CANSparkMax(2, MotorType.kBrushless);
    private static final CANSparkMax endgameLiftFollow2 = new CANSparkMax(3, MotorType.kBrushless);

    // Joystick Object
    private static Joystick stick = new Joystick(1);
    
    // Joystick deadband constant
    private static final double ENDGAME_JOYSTICK_DEADBAND = 0.1;

    static {

        // Mirror primary motor controller
        endgameLiftFollow1.follow(endgameLift);
        endgameLiftFollow2.follow(endgameLift);
    }

    public static void init() {
    }

    public static void run() {

         // Move end game lift up when right joystick is pushed up
        if (Math.abs(stick.getRawAxis(5)) >= ENDGAME_JOYSTICK_DEADBAND){  // getRawAxis(5) = right joystick
            // The lift's speed will be set at the right joystick's input value
            endgameLift.set(-stick.getRawAxis(5));
        }  
        else { // If the joystick isn't being touched, don't move
            endgameLift.set(0.0);
        }
    }
    
}
