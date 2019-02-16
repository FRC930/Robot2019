/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//-------- Imports --------\\

package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;

/**
 * Add your docs here.
 */
public class HatchIntake {

    private static final Solenoid hatchPiston = new Solenoid(Constants.HATCH_SOLENOID_PORT);

    static {

        //Sets the solenoid to a default start position. 
        //This will be retracted
        hatchPiston.set(Constants.HATCH_SOLENOID_START);
    }

    public static void init() {

    }

    public static void run(boolean coDriverInput) {
      
        /*  
            coDriverInput will be a true or false value of the CoDriver's A button.
            It is true when the A button is down, and false if it is up.

            hatchPiston.set sets the piston's state. If it is true, the piston will be pushed out. If it is false, 
            the piston will be retracted. When the piston is pushed out, the piston will grab the hatch.

            Overall, the piston will be set to the button's state. if the buttton is down, it is true, which then
            sets the piston to true, which will push out the piston. When the piston is true, we will grab the hatch.
        */
        hatchPiston.set(coDriverInput);
    }   
}
//hi sam