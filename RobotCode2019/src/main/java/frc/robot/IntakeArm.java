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
  

    private final static Solenoid armPiston = new Solenoid(Constants.ARM_SOLENOID_PORT); //Declaring the arm piston.

    static {

        /*
          On startup, the piston will extend, pulling the arm in.
        */
        armPiston.set(Constants.ARM_START_POSITION);

    }

    public static void init() {

    }

    public static void run(boolean armActivity) {
      
        /*
          When armActivity = false, piston retracts, pushing the arm out.
          When armActivity = true, piston extends, pulling the arm in.
        */
        armPiston.set(armActivity);
      
    }
    
    public static void setArmPiston(boolean state){

      armPiston.set(state);

    }
    public static boolean getArmPistonStatus(){

      return armPiston.get();

    }

}

//written by your boi josh