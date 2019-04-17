/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
//import com.sun.tools.javadoc.main.Start;

import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.CargoIntake.CargoPositionEnums;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
/*
 * Just running Teleop
 */
public class Sandstorm {

    static {

        // Sets up a boolean toggle in shuffleboard
        SmartDashboard.putBoolean("Autonomous Hatch Toggle", true);

    }

    public static void init() {

        // Detects what the toggle is set to, the true is default if there is no value.
        if(SmartDashboard.getBoolean("Autonomous Hatch Toggle", true))
        {
            HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
            IntakeArm.setArmPiston(Constants.ARM_STATE_DOWN);
        }
    }

    public static void run() {
        
        TeleopHandler.run();
        
    }
    
}
