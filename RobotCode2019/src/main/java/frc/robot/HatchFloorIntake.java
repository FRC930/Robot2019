/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// --------- Hatch Intake General Goal(s) --------- \\

/*
FUNCTION OF HATCH FLOOR INTAKE:
---------------------------------------------------------------------------
The hatch intake system we are using is a row of multiple rubber wheels
that spin inwards once over a hatch. The pressure between the rubber wheels and hatch
will hold the hatch plate snug under the holding wheels. The "hand" of
wheels holding the hatch will then rotate from the ground 90 degrees upwards 
so it is perpendicular to the ground and in position to be taken by the hatch intake beak
and placed at any of the three height positions.

CONTROLLER BUTTONS USED:       | BUTTON NAME:   | BUTTON MAPPING:
------------------------       | ------------   | ---------------
All controls :                 | Left Bumper    | (Button 5)
*/

// --------- Imports --------- \\

package frc.robot;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;

import com.ctre.phoenix.Util;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * 
 */
public class HatchFloorIntake {

    // ------------ Objects ------------- \\
    private static final Solenoid hatchFloorPiston = new Solenoid(Constants.FLOOR_HATCH_SOLENOID);  //piston controller that controls the hatch floor intake piston
    private static final VictorSPX hatchFloorIntakeVictorController = new VictorSPX(Constants.FLOOR_HATCH_VICTOR);  //motor controller that controls the mini CIM on the hatch floor intake piston
    private static Timer timeCount = new Timer(); //a timer object, used to track time

    //Runs code inside here once
    static {

        //Initializes the timer to 0 seconds
        timeCount.reset();
        
    }

    public static void init() {

    }

    /*
        Will run the main part of the program. This method is called by the teleop handler, and it takes a parameter
        of the coDriver's left trigger button state (true or false). With this, the method will run the steps needed to 
        bring the elevator to floor hatch level, close the beak, and set the beak's arm down. When all of these are true 
        and a hatch is caught in the hatch floor intake rollers, the hatch floor intake arm will raise the hatch onto
        the beak and the beak will grab the hatch.
    */
    public static void run(boolean coDriverLT) {

        // Checks to see if Left Bumper button is pressed by codriver controller
        if (coDriverLT) {

            // Checks to see if the elevator and beak intake is NOT at floor position
            if (!Elevator.atPosition(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation)) {
                // Moves the elevator to floor position to be ready to intake 
                Elevator.setTargetPos(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation);
            }
    
            //Checks to see if beak arm is NOT down 
            if (IntakeArm.getArmPistonStatus() == Constants.ARM_STATE_DOWN) {
                // Brings hatch beak arm down to position
                IntakeArm.setArmPiston(Constants.ARM_STATE_UP);
            }
    
            //Checks to see if beak is NOT closed
            if (HatchIntake.getHatchPistonStatus() == Constants.HATCH_STATE_OPEN){
                // Closes beak so the hatch can be placed over the beak
                HatchIntake.setHatchPiston(Constants.HATCH_STATE_CLOSED);
            }
    
            /*
            If all are set for intake, wheels intake hatch into hand
            */
            //hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, Constants.FLOOR_HATCH_INTAKE_SPEED);
    
            /* Brings floor intake hand to ground using piston
            The '.set' method is what tells the Solenoid whether the piston should be set
            to true or false. When set to false, the piston is collapsed and not
            extended. When set to true, the piston is extended, meaning the hatch intake
            "hand" is set to a perpendicular position from the ground, which in theory
            should be in position to be picked up by the bird beak hatch holder on the
            elevator.
            */
            hatchFloorPiston.set(true);
    
            //If the elevator is down, the arm on the elevator is down, the beak is closed, and there is a hatch in our floor hatch intake
            if (Elevator.atPosition(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation) && IntakeArm.getArmPistonStatus() && !HatchIntake.getHatchPistonStatus() && (Utilities.Power.getCurrent(Constants.FLOOR_HATCH_PDP_VICTOR) >= Constants.FLOOR_HATCH_CURRENT_LIMIT)) {
    
               //Raise the hatch floor intake to the beak
                hatchFloorPiston.set(Constants.FLOOR_HATCH_INTAKE_UP);
        
                //starts the timer
                 timeCount.start();
    
                 //Waits until the timer reaches a certain frame
                 if (timeCount.get() >= Constants.FLOOR_HATCH_RAISE_WAITTIME) {
                     // Opens Beak, which grabs hatch object
                     HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
                     //Outtake the hatch floor intake rollers
                     //hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, Constants.FLOOR_HATCH_OUTTAKE_SPEED);
                 }
    
                 //if the beak is open and the floor intake arm is up
                 if (HatchIntake.getHatchPistonStatus() && !hatchFloorPiston.get()) {
        
                     //move the floor hatch intake down t the ground as a reset for the next 
                     hatchFloorPiston.set(Constants.FLOOR_HATCH_INTAKE_DOWN);
                     // Resets the timer to 0 to be ready for the next run of the hatch floor intake program
                     timeCount.stop();
                    timeCount.reset();
                 }   
            }   //end of the 4 checks on elevator, beak, beak arm, and hatch intake
        }   //end of the check of the coDriver button

    }  //end of .run() 
}   //end of the class 
