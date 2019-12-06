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

//Cargo Intake class
public class CargoIntake{

    //===== Variables ======||
    //Declaring the Cargo Intake solenoid
    private Solenoid handPiston;
    //Declaring the Cargo Intake solenoid
    private Solenoid topPiston; 
    //Declaring the Cargo Intake motor
    private VictorSPX cargoMotor; 
    //Initialization the static flag for singleton
    private static CargoIntake lastInstance = null;

    //Private constructor for the class
    private CargoIntake(){
        //Sets objects to null
        handPiston = null;
        topPiston = null;
        cargoMotor = null;
    }

    //Call to get single instance of Cargo Intake
    public static CargoIntake getInstance(){
        if(lastInstance == null){
            lastInstance = new CargoIntake();
            return lastInstance;
        }
        else{
            return lastInstance;
        }
    }

    //Call to set hardware for robot
    public void setHardware(){
        //Chains to other setHardware method
        setHardware(new Solenoid(Constants.CARGO_SOLENOID_PORT), new Solenoid(1), new VictorSPX(Constants.CARGO_VICTORSPX_ID));
    }

    //Call to set hardware for unit tests
    public void setHardware(Solenoid HandPiston, Solenoid TopPiston, VictorSPX CargoMotor){
        //Copies into class variables
        handPiston = HandPiston;
        topPiston = TopPiston;
        cargoMotor = CargoMotor;
        //Sets pistons
        handPiston.set(Constants.CARGO_HAND_EXTENDED);
        topPiston.set(Constants.CARGO_TOP_PISTON_EXTENDED);
    }

    //===== Cargo Positions =====||
    //Creates enum states with values of cargo intake
    public static enum CargoPositionEnums{ 
        cargoIntake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_INTAKE_SPEED, Constants.CARGO_TOP_PISTON_EXTENDED), // Taking in the ball/cargo.
        cargoOutTake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_OUTTAKE_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED), // Releasing the ball/cargo.
        cargoStop(Constants.CARGO_HAND_EXTENDED, Constants.CARGO_STOP_SPEED, Constants.CARGO_TOP_PISTON_EXTENDED), // Setting the intake/outake to constant speed.
        cargoCarrying(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_STOP_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED),
        cargoCarryingIntake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_INTAKE_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED),
        cargoCarryingOuttake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_OUTTAKE_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED);

        //Sets positional value for enum
        private final boolean Cargo_Position; 
        //Sets speed value for enum
        private final double Cargo_Speed; 
        //Sets cargo state value for enum
        private final boolean topPistonPosition; 

        //Creates constructor for enums
        CargoPositionEnums(boolean CargoPosition, double CargoSpeed, boolean topPistionState){ 
            this.Cargo_Position = CargoPosition;
            this.Cargo_Speed = CargoSpeed;
            this.topPistonPosition = topPistionState;
        }

        //Gets the cargo position of the enum called
        public boolean getCargoPosition(){ 
            return this.Cargo_Position;
        }
        //Gets the cargo speed of the enum called
        public double getCargoSpeed(){ 
            return this.Cargo_Speed;
        }
        //Gets the top piston position of the enum called
        public boolean getTopPistionState(){
            return this.topPistonPosition;
        }
    }

    //Call for enum-specific positions
    public void run(CargoPositionEnums pos){
        //For cargo outake, we want to run the wheels not the pistons
        if(pos != CargoPositionEnums.cargoOutTake){
            //Sets the pistons to the value
            handPiston.set(pos.getCargoPosition());
            topPiston.set(pos.getTopPistionState());
        }
        //Sets the wheels  
        cargoMotor.set(ControlMode.PercentOutput, pos.getCargoSpeed());
    }

    //Call for manual control of intake motors
    public void runManual(boolean check){
        if(check){
            cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_INTAKE_SPEED);
        }
        else{
            cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_UP_OUTTAKE_SPEED);
        }
    }
}