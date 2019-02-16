/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

/**
 * Add your docs here.
 */
public class HatchFloorIntake {

    // ------------ Objects ------------- \\
    private static final Solenoid hatchFloorIntakePistonController = new Solenoid(Constants.HATCH_FLOOR_SOLENOID);
    private static final VictorSPX hatchFloorIntakeVictorController = new VictorSPX(Constants.HATCH_FLOOR_VICTOR);
    private static final Solenoid armPiston = new Solenoid(Constants.ARM_SOLENOID_PORT); // Beak arm for intake
    private static final Solenoid hatchPiston = new Solenoid(Constants.HATCH_SOLENOID_PORT); // piston that controls beak (open or closed)
    private static final PowerDistributionPanel PDP = new PowerDistributionPanel();   //power distribtion panel of robot
    private static Timer timeCount = new Timer(); //a timer, used to track time

    static {
        timeCount.reset();
    }

    public static void init() {

    }

    public static void run(boolean coDriverLT) {

        // Checks to see if Left Bumper button is pressed by codriver controller
        if (coDriverLT) {

            // Checks to see if the elevator and beak intake is NOT at floor position
            if (!Elevator.atPosition(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation)) {
                // Moves the elevator to floor position to be ready to intake 
                Elevator.setTargetPos(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation);
            }
    
            //Checks to see if beak arm is NOT down 
            if (!armPiston.get()) {
                // Brings hatch beak arm down to position
                armPiston.set(true);
            }
    
            // Checks to see if beak is NOT closed
            if (hatchPiston.get()){
                // Closes beak so the hatch can be placed over the beak
                hatchPiston.set(false);
            }
    
            /*
            If all are set for intake, wheels intake hatch intohand
            */
            hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, Constants.HATCH_FLOOR_INTAKE_SPEED);
    
            /* Brings floor intake hand to ground using piston
            The '.set' method is what tells the Solenoid whether the piston should be set
            to true or false. When set to false, the piston is collapsed and not
            extended. When set to true, the piston is extended, meaning the hatch intake
            "hand" is set to a perpendicular position from the ground, which in theory
            should be in position to be picked up by the bird beak hatch holder on the
            elevator.
            
            Inside the () of the '.set' function is a boolean value, which we have set to
            the boolean value that is recieved from the controller button, which is
            either pressed (true) or unpressed (false).
            */
            hatchFloorIntakePistonController.set(true);
    
            //If the elevator is down, the arm on the elevator is down, the beak is closed, and there is a hatch in our floor hatch intake
            if (Elevator.atPosition(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation) && armPiston.get() && !hatchPiston.get() && (PDP.getCurrent(Constants.HATCH_FLOOR_PDP_VICTOR) >= Constants.HATCH_FLOOR_CURRENT_LIMIT)) {
    
                //Raise the hatch floor intake to the beak
                hatchFloorIntakePistonController.set(false);
        
                //starts the timer
                timeCount.start();
    
                //Waits until the timer reaches a certain frame
                if (timeCount.get() >= Constants.HATCH_FLOOR_RAISE_WAITTIME) {
                    // Opens Beak, which grabs hatch object
                    hatchPiston.set(true);
                    //Outtake the hatch floor intake rollers
                    hatchFloorIntakeVictorController.set(ControlMode.PercentOutput, Constants.HATCH_FLOOR_OUTTAKE_SPEED);
                }
    
                //if the beak is open and the floor intake arm is up
                if (hatchPiston.get() && !hatchFloorIntakePistonController.get()) {
        
                    //move the floor hatch intake down t the ground as a reset for the next 
                    // hatchFloorIntakePistonController.set(true);
                    // Resets the timer to 0 to be ready for the next run of the hatch floor intake program
                    timeCount.stop();
                    timeCount.reset();
                }
            }
        }

    }  //end of .run() 
}
