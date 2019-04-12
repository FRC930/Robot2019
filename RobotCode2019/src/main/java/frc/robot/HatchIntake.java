/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//-------- Imports --------\\

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class HatchIntake {

    private static final DoubleSolenoid hatchPiston = new DoubleSolenoid(Constants.HATCH_SOLENOID_PORT, 7);

    // Used later in code to see if left button is pressed
    private static boolean pressedL = false;

    // Used later in code to see if right button is pressed
    private static boolean pressedR = false;

    // Beak toggle
    public static boolean beakToggle = false;
    private static boolean beakStatus = true;

    // Auto hatch
    private static boolean autoHatch = false;
    private static int autoHatchCounter = 0;
    
    static {

        // Sets the solenoid to a default start position. 
        // This will be retracted (value is false)
        // hatchPiston.set(Constants.HATCH_SOLENOID_START);
        hatchPiston.set(Value.kForward);
        
    }

    public static void init() {

        HatchIntake.putSmartDashboardHatch(beakStatus);
    }

    public static void run(boolean isDriverButtonPressed, boolean isCoDriverButtonPressed) {
        
        if (!isDriverButtonPressed) {
            VisionTracking.setAutoHatchGrabbed(false);
            TeleopHandler.setRumble(Constants.DRIVER_CONTROLLER_ID, Constants.RUMBLE_STOP);
            
        }
        
        /*
            The below code controls the toggle for the beak.

            The beak will not go down when the cargo is down.

        */

         // Button toggle for the beak
         if ((isDriverButtonPressed) /* || pressedL || pressedR)*/ && beakToggle == false && !isCoDriverButtonPressed) {
            beakToggle = true;
        }
        else if ((!isDriverButtonPressed/* || pressedL || pressedR)*/ && beakToggle)) {
            beakToggle = false;
            if (!autoHatch) {
                beakStatus = !beakStatus;
                //sets the beak to the beakstatus ;
                setHatchPiston(beakStatus);
            }
        }

        // If LT is held for at least 1 sec, flag for using auto hatch pickup is set to true
        if (isDriverButtonPressed && beakToggle) {
            autoHatchCounter++;
            if (autoHatchCounter >= Constants.HATCH_LT_HOLD_THRESHOLD) {
                if (!VisionTracking.getAutoHatchGrabbed()) {
                    setHatchPiston(Constants.HATCH_STATE_CLOSED);
                }
                autoHatch = true;
            }
        }
        else {
            // LT is released, set auto hatch variables back to default values
            autoHatch = false;
            autoHatchCounter = 0;
        }
    }

    // Returns status of auto hatch pickup toggle
    public static boolean getAutoHatchPickup() {
        return autoHatch;
    }

    // Sets the hatch pistion to the boolean state it gives
    public static void setHatchPiston(boolean state){
        beakStatus = state;
        if(state){
            hatchPiston.set(Value.kForward);
        }
        else{
            hatchPiston.set(Value.kReverse);
        }
        putSmartDashboardHatch(state);
    }

    // Will return the  status of the solinoid returns true or false
    public static boolean getHatchPistonStatus(){

        if(hatchPiston.get() == Value.kForward){
            return true;
        }
        else{
            return false;
        }
        
    }

    public static void putSmartDashboardHatch(boolean hatchStatus) {

        SmartDashboard.putBoolean("Beak State", hatchStatus);

    }

}

//hi sam