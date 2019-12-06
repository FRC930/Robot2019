/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*

//===== OVERALL EXPLANATION =====||

GOAL(S):
  Hatch Beak Intake Arm is retracted on start.
  One button is used to extend and retract the arm.

BUTTON(S) USED:
  Button 5: Left Bumper (LB)

*/


//===== Imports =====||

package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;


public class IntakeArm {
  
    // Declaring the arm piston
    private final Solenoid armPiston = new Solenoid(Constants.ARM_SOLENOID_PORT);

     {

        /*
          On startup, the piston will extend, pulling the arm in
        */
        armPiston.set(Constants.ARM_START_POSITION);

    }

    public  void init() {

    }

    public void run(boolean armActivity) {
      
        /*
          When armActivity = false, piston retracts, pushing the arm out.
          When armActivity = true, piston extends, pulling the arm in.
        */
        armPiston.set(armActivity);
      
    }
    
    //sets the pistion to the boolean state it gets
    public void setArmPiston(boolean state){

      armPiston.set(state);

    }

    // It will return the status of the solinoid
    public boolean getArmPistonStatus(){

      return armPiston.get();

    }
    private static IntakeArm lastInstance = null;
// class constructor for the robot    
private IntakeArm() {}

static public IntakeArm getInstance(){
  if (lastInstance == null){
    lastInstance = new IntakeArm();
    return lastInstance;
  }
  else {
    return lastInstance;
  }
}
}

//written by your boi josh