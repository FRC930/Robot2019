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
    //private final static Solenoid stopIntakePiston = new Solenoid(Constants.CARGO_STOP_INTAKE_SOLENOID_PORT); //Declaring the Cargo Stop Intake solenoid.
    private final static Solenoid topPiston = new Solenoid(6); //Declaring the Cargo Intake solenoid.
    private final static VictorSPX cargoMotor = new VictorSPX(Constants.CARGO_VICTORSPX_ID); //Motor control.
    private static int delayTimeCounter = 0;
    private static int delayTimeCounterManual = 0;

    //===== Cargo Positions =====||

    public static enum CargoPositionEnums{ // States with values of cargo intake.

        cargoIntake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_INTAKE_SPEED, Constants.CARGO_TOP_PISTON_EXTENDED), // Taking in the ball/cargo.
        cargoOutTake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_OUTTAKE_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED), // Releasing the ball/cargo.
        cargoStop(Constants.CARGO_HAND_EXTENDED, Constants.CARGO_STOP_SPEED, Constants.CARGO_TOP_PISTON_EXTENDED), // Setting the intake/outake to constant speed.
        cargoCarring(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_STOP_SPEED, Constants.CARGO_TOP_PISTON_EXTENDED);

        private final boolean Cargo_Position; // Sets positional value for enum.
        private final double Cargo_Speed; // Sets speed value for enum.
        private final boolean topPistonPosition; // Sets cargo state value for enum.


        CargoPositionEnums(boolean CargoPosition, double CargoSpeed, boolean topPistionState){ // Creates constructor for enums.
            this.Cargo_Position = CargoPosition;
            this.Cargo_Speed = CargoSpeed;
            this.topPistonPosition = topPistionState;
        }

        public boolean getCargoPosition(){ // Gets the cargo position of the enum called.
            return this.Cargo_Position;
        }

        public double getCargoSpeed(){ // Gets the cargo speed of the enum called.
            return this.Cargo_Speed;
        }

        public boolean getTopPistionState(){
            return this.topPistonPosition;
        }

    }

    static {
        handPiston.set(Constants.CARGO_HAND_EXTENDED);
        topPiston.set(Constants.CARGO_TOP_PISTON_EXTENDED);
    }

    public static void init() {

    }

    //===== Main Iterative Method =====||
    public static void run(CargoPositionEnums pos){
        handPiston.set(pos.getCargoPosition());
        topPiston.set(pos.getTopPistionState());    
        cargoMotor.set(ControlMode.PercentOutput, pos.getCargoSpeed());

    }

    public static void runManual(boolean check){
        topPiston.set(Constants.CARGO_TOP_PISTON_EXTENDED);
        handPiston.set(Constants.CARGO_HAND_EXTENDED);

        if(check){
            cargoMotor.set(ControlMode.PercentOutput, CargoPositionEnums.cargoIntake.getCargoSpeed());
        }

        else{
            cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_UP_OUTTAKE_SPEED);
        }
    }

}

