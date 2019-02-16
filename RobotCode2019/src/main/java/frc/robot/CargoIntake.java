/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
//===== OVERALL EXPLANATION =====||

GOAL(S):
    Be able to hold the hand up and have it hang.
    Have intake, outtake, and idle settings for the VictorSPX motor controller.

*/

//===== Imports =====||

package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class CargoIntake {

    private final static Solenoid handPiston = new Solenoid(Constants.CARGO_SOLENOID_PORT); //Declaring the arm piston
    private final static VictorSPX cargoMotor = new VictorSPX(Constants.CARGO_VICTORSPX_PORT); //Wheel control
    private static CargoPositionEnums stateEnum; //Actions the cargo hand of must complete.

    enum CargoPositionEnums{
        cargoIntake, //Taking in the ball/cargo.
        cargoOutTake, //Releasing the ball/cargo.
        cargoStop //Setting the intake/outtake to idle.
    }

    static {

        /*
          On startup, the hand will be up.
        */
        handPiston.set(Constants.CARGO_START_POSITION);
        
    }

    public static void init() {

    }

    public static void run(Enum pos){

        stateEnum = (CargoIntake.CargoPositionEnums) pos;
        
        switch(stateEnum){
            case cargoIntake:
                handPiston.set(Constants.CARGO_HAND_DOWN); //Hand will be set down and ready for intake.
                cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_INTAKE_SPEED); //The VictorSPX will begin spinning inwards, towards the robot, to pull the cargo in.
                break;

            case cargoOutTake:
                handPiston.set(Constants.CARGO_HAND_DOWN); //Hand will be set down and ready for outtake.
                cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_OUTTAKE_SPEED); //The VictorSPX will begin spinning outwards, away from the robot, to release the cargo.
                break;

            case cargoStop:
                handPiston.set(Constants.CARGO_HAND_UP); //Hand will be held up and idle.
                cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_STOP_SPEED); //The VictorSPX will stop the motors to a speed of 0.
                break;
            
        }

    }
   
    
}

//written by your boi josh