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

    private Solenoid handPiston = null; //Declaring the Cargo Intake solenoid.
    private Solenoid topPiston = null; //Declaring the Cargo Intake solenoid.
    private VictorSPX cargoMotor = null; //Motor control.

    //Checks if instace is already created
    private static CargoIntake lastInstance = null;

    //Class constructer for the robot
    private CargoIntake() {}

    //Call to get a single instance of CargoIntake
    static public CargoIntake getInstance(){
        if(lastInstance == null){
            lastInstance = new CargoIntake();
            return lastInstance;
        }
        else{
            return lastInstance;
        }
    }
    //===== Cargo Positions =====||
    // States with values of cargo intake.
    public static enum CargoPositionEnums{ 

        cargoIntake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_INTAKE_SPEED, Constants.CARGO_TOP_PISTON_EXTENDED), // Taking in the ball/cargo.
        cargoOutTake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_OUTTAKE_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED), // Releasing the ball/cargo.
        cargoStop(Constants.CARGO_HAND_EXTENDED, Constants.CARGO_STOP_SPEED, Constants.CARGO_TOP_PISTON_EXTENDED), // Setting the intake/outake to constant speed.
        cargoCarrying(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_STOP_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED),
        cargoCarryingIntake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_INTAKE_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED),
        cargoCarryingOuttake(Constants.CARGO_HAND_RETRACTED, Constants.CARGO_OUTTAKE_SPEED, Constants.CARGO_TOP_PISTON_RETRACTED);
        public static final int CARGO_SOLENOID_PORT = 4;
		public static final int CARGO_STOP_INTAKE_SOLENOID_PORT = 5;
		public static final int CARGO_VICTORSPX_ID = 4;
		
		public static final int CARGO_BRAKE_DELAY = 4;
		public static final double CARGO_UP_OUTTAKE_SPEED = 1;
		public static final double CARGO_OUTTAKE_SPEED = 0.75;//0.5;
		public static final double CARGO_STOP_SPEED = -0.2;//0.0;
		public static final int CARGO_BRAKE_TIMER_RESET = 0;
		public static final double CARGO_INTAKE_SPEED = -0.8;
		
		public static final boolean CARGO_HAND_RETRACTED = true;
		public static final boolean CARGO_BRAKE = false;
		public static final boolean CARGO_START_POSITION = false;
		public static final boolean CARGO_HAND_EXTENDED = false;
		public static final boolean CARGO_RELEASE = true;
		public static final boolean CARGO_TOP_PISTON_EXTENDED = false;
		public static final boolean CARGO_TOP_PISTON_RETRACTED = true;
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
    public void setSolenoids(){ 
        setSolenoids(new Solenoid(Constants.CARGO_SOLENOID_PORT),new Solenoid(1), new VictorSPX(Constants.CARGO_VICTORSPX_ID));
}
public void setSolenoids(Solenoid handPiston, Solenoid topPiston, VictorSPX cargoMotor){
    this.handPiston =handPiston; //Declaring the Cargo Intake solenoid.
    this.topPiston =topPiston; //Declaring the Cargo Intake solenoid.
    this.cargoMotor =cargoMotor; //Motor control.
    handPiston.set(Constants.CARGO_HAND_EXTENDED);
    topPiston.set(Constants.CARGO_TOP_PISTON_EXTENDED);
}
    //===== Main Iterative Method =====||
    //runs enums
    public void run(CargoPositionEnums pos){
        //For cargo outake we just want to run the wheels not the pistons
        if(pos != CargoPositionEnums.cargoOutTake){
            //sets the pistons to the value
            handPiston.set(pos.getCargoPosition());
            topPiston.set(pos.getTopPistionState());
        }
        //sets the wheels  
        cargoMotor.set(ControlMode.PercentOutput, pos.getCargoSpeed());

    }
    //runs the manual for wheels
    public void runManual(boolean check){
        // topPiston.set(Constants.CARGO_TOP_PISTON_EXTENDED);
        // handPiston.set(Constants.CARGO_HAND_EXTENDED);

        if(check){
            cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_INTAKE_SPEED);
        }

        else{
            cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_UP_OUTTAKE_SPEED);
        }
    }

}