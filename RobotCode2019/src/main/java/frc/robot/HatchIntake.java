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

    private static boolean LTPressed = false;     //This boolean is used to keep track of the toggle
    private static boolean hatchPistionState = Constants.HATCH_SOLENOID_START;  //Used to set the state of the beak piston (starts closed)

    static {

        //Sets the solenoid to a default start position. 
        //This will be retracted (value is false)
        hatchPiston.set(Constants.HATCH_SOLENOID_START);
    }

    public static void init() {

    }

    public static void run(boolean coDriverInput) {
      
        /*  
            coDriverInput will be a true or false value of the CoDriver's left trigger button.
            It is true when the left trigger button is down, and false if it is up.

            hatchPiston.set sets the beak piston's state. If it is true, the beak piston will be pushed out. If it is false, 
            the beak piston will be retracted. When the beak piston is pushed out, it will grab the hatch.

            LTPressed is a boolean used to make the system togglable. When you press LT once, the beak will close (piston true).
            When LT is pressed again, the beak will open (piston false, grabbed hatch).

            When the coDriver inputs and LTPressed is false, LT will be set true and the beak piston state will be flipped.
            Ex: The beak state will start false. When the driver inputs and LTPressed is false, LTPressed is set to true
            and the beak state is set to true (closed beak). Because LTPressed is set true, that if statement will not run,
            effectively making that block of code run once. LTPressed is set to false when the coDriver stops inputting. 

        */

        if (coDriverInput && !LTPressed) {
            LTPressed = true;
            hatchPistionState = !hatchPistionState;
            hatchPiston.set(hatchPistionState);
        } else if (!coDriverInput && LTPressed) {
            LTPressed = false;
        }
    }   
}

//hi sam