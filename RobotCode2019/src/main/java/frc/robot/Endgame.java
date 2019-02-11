/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Lifts the robot onto the third platform during the Endgame
 */
public class Endgame {

    // Endgame Motor Controllers
    private static final TalonSRX endgameLift = new TalonSRX(1);
    private static final VictorSPX endgameLiftFollow1 = new VictorSPX(2);
    private static final VictorSPX endgameLiftFollow2 = new VictorSPX(3);
    
    static {

        // Mirror primary motor controller
        endgameLiftFollow1.follow(endgameLift);
        endgameLiftFollow2.follow(endgameLift);

    }

    public static void init() {

    }

    public static void run(double stickY) {

        // Move end game lift up when right joystick is pushed up
        if (Math.abs(stickY) >= Constants.ENDGAME_JOYSTICK_DEADBAND) {

            // The lift's speed will be set at the right joystick's input value
            endgameLift.set(ControlMode.PercentOutput, -stickY);

        }

        else { 
            
            // If the joystick isn't being touched, don't move
            endgameLift.set(ControlMode.PercentOutput, 0.0);
        }
        
    }
    
}
