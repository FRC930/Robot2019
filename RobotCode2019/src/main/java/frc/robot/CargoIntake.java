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
    private final static Solenoid stopIntakePiston = new Solenoid(Constants.CARGO_STOP_INTAKE_SOLENOID_PORT); //Declaring the Cargo Stop Intake solenoid.
    private final static VictorSPX cargoMotor = new VictorSPX(Constants.CARGO_VICTORSPX_PORT); //Motor control.
    private static int delayTimeCounter = 0;
    private static int delayTimeCounterManual = 0;
    //===== Cargo Positions =====||

    public static enum CargoPositionEnums{ // States with values of cargo intake.
        cargoIntake(Constants.CARGO_HAND_DOWN, Constants.CARGO_INTAKE_SPEED, Constants.CARGO_RELEASE), // Taking in the ball/cargo.
        cargoOutTake(Constants.CARGO_HAND_DOWN, Constants.CARGO_OUTTAKE_SPEED, Constants.CARGO_RELEASE), // Releasing the ball/cargo.
        cargoStop(Constants.CARGO_HAND_UP, Constants.CARGO_STOP_SPEED, Constants.CARGO_BRAKE); // Setting the intake/outake to constant speed.
        

        private final boolean Cargo_Position; // Sets positional value for enum.
        private final double Cargo_Speed; // Sets speed value for enum.
        private final boolean Cargo_Brake; // Sets cargo state value for enum.

        CargoPositionEnums(boolean CargoPosition, double CargoSpeed, boolean CargoBrake){ // Creates constructor for enums.
            this.Cargo_Position = CargoPosition;
            this.Cargo_Speed = CargoSpeed;
            this.Cargo_Brake = CargoBrake;
        }

        public boolean getCargoPosition(){ // Gets the cargo position of the enum called.
            return this.Cargo_Position;
        }

        public double getCargoSpeed(){ // Gets the cargo speed of the enum called.
            return this.Cargo_Speed;
        }

        public boolean getCargoBrake(){
            return this.Cargo_Brake;
        }
    }

    static {

        handPiston.set(Constants.CARGO_START_POSITION);    

    }

    public static void init() {

    }

    //===== 

    public static void run(CargoPositionEnums pos){
        delayTimeCounter++;
        //Cargo Intake system will be held up and idle
        handPiston.set(pos.getCargoPosition());
        if(pos == CargoPositionEnums.cargoIntake || pos == CargoPositionEnums.cargoOutTake){
            //Brakes the cargo intake or releases the cargo from the cargo intake
            stopIntakePiston.set(pos.getCargoBrake());
        
            if(delayTimeCounter >= Constants.CARGO_BRAKE_DELAY){
                cargoMotor.set(ControlMode.PercentOutput, pos.getCargoSpeed());
                delayTimeCounter = Constants.CARGO_BRAKE_TIMER_DELAY;
            }
        }

       else if(pos == CargoPositionEnums.cargoStop){
            cargoMotor.set(ControlMode.PercentOutput, pos.getCargoSpeed());
        
            if(delayTimeCounter >= Constants.CARGO_BRAKE_DELAY){
                //Brakes the cargo intake or releases the cargo from the cargo intake
                stopIntakePiston.set(pos.getCargoBrake());
                delayTimeCounter = Constants.CARGO_BRAKE_TIMER_DELAY;
            }
        }

        //The VictorSPX will stop the motors to a speed of 0
        cargoMotor.set(ControlMode.PercentOutput, pos.getCargoSpeed());

    }

    public static void runManual(boolean check){
        delayTimeCounterManual++;
        if(check){
            //Brakes the cargo intake or releases the cargo from the cargo intake
            stopIntakePiston.set(CargoPositionEnums.cargoIntake.getCargoBrake());
        
            if(delayTimeCounterManual >= Constants.CARGO_BRAKE_DELAY){
                cargoMotor.set(ControlMode.PercentOutput, CargoPositionEnums.cargoIntake.getCargoSpeed());
                delayTimeCounterManual = Constants.CARGO_BRAKE_TIMER_DELAY;
            }
        }

        else{
            stopIntakePiston.set(CargoPositionEnums.cargoOutTake.getCargoBrake());
        
            if(delayTimeCounterManual >= Constants.CARGO_BRAKE_DELAY){
                //Brakes the cargo intake or releases the cargo from the cargo intake
                cargoMotor.set(ControlMode.PercentOutput, CargoPositionEnums.cargoOutTake.getCargoSpeed());
                delayTimeCounterManual = Constants.CARGO_BRAKE_TIMER_DELAY;
            }
        }
    }

}

//written by your boi joj