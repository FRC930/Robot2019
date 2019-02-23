/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*
//===== OVERALL EXPLANATION =====||

This subsystem is controlling the Cargo Intake part of the robot.
    -- While the Cargo Intake system is up, the motors will be idle and nonmoving.
    -- While the Cargo Intake system is down, the motors will either intaking or outtaking the cargo.

*/

//===== Imports =====||

package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class CargoIntake {

    //===== Variables ======||

    private final static Solenoid handPiston = new Solenoid(Constants.CARGO_SOLENOID_PORT); //Declaring the Cargo Intake solenoid.
    //private final static VictorSPX cargoMotor = new VictorSPX(Constants.CARGO_VICTORSPX_PORT); //Motor control.

    //===== Cargo Positions =====||

    public static enum CargoPositionEnums{ // States with values of cargo intake.
        cargoIntake(Constants.CARGO_HAND_DOWN, Constants.CARGO_INTAKE_SPEED), // Taking in the ball/cargo.
        cargoOutTake(Constants.CARGO_HAND_UP, Constants.CARGO_OUTTAKE_SPEED), // Releasing the ball/cargo.
        cargoStop(Constants.CARGO_HAND_UP, Constants.CARGO_STOP_SPEED); // Setting the intake/outake to constant speed.

        private final boolean Cargo_Position; // Sets positional value for enum.
        private final double Cargo_Speed; // Sets speed value for enum.

        CargoPositionEnums(boolean CargoPosition, double CargoSpeed){ // Creates constructor for enums.
            this.Cargo_Position = CargoPosition;
            this.Cargo_Speed = CargoSpeed;
        }

        public boolean getCargoPosition(){ // Gets the cargo position of the enum called.
            return this.Cargo_Position;
        }

        public double getCargoSpeed(){ // Gets the cargo speed of the enum called.
            return this.Cargo_Speed;
        }
    }

    static {

        handPiston.set(Constants.CARGO_START_POSITION);    

    }

    public static void init() {

    }

    //===== 

    public static void run(CargoPositionEnums pos){
        
        //Cargo Intake system will be held up and idle
        handPiston.set(pos.getCargoPosition());

        //The VictorSPX will stop the motors to a speed of 0
        //cargoMotor.set(ControlMode.PercentOutput, pos.getCargoSpeed());

    }
    
}

//written by your boi josh