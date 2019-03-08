/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/*
 * Just running Teleop
 */
public class Sandstorm {

    static {

    }

    public static void init() {

        IntakeArm.setArmPiston(Constants.ARM_STATE_DOWN);
        HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);

    }

    public static void run() {
        
        //Sets it so that we can run the main code during sandstorm
        TeleopHandler.run();
        
    }
    
}
