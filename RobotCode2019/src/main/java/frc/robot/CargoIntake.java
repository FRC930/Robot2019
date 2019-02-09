/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//===== Imports =====||

package frc.robot;

import edu.wpi.first.wpilibj.Solenoid;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class CargoIntake {

    private final static Solenoid handPiston = new Solenoid(Constants.CARGO_SOLENOID_PORT); //Declaring the arm piston.
    private final static VictorSPX cargoMotor = new VictorSPX(1); //Wheel control.
    private static CargoPositionEnums stateEnum;

    enum CargoPositionEnums{
        cargoIntake,
        cargoOutTake,
        cargoStop
    }

    static {
        handPiston.set(Constants.CARGO_START_POSITION);
        /*
        On startup, the hand will be up.
        */
    }

    public static void init() {

    }

    public static void run(Enum pos){
        stateEnum = (CargoIntake.CargoPositionEnums) pos;
        
        switch(stateEnum){
            case cargoIntake:
                handPiston.set(Constants.CARGO_HAND_DOWN);
                cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_INTAKE_SPEED);
                break;

            case cargoOutTake:
                handPiston.set(Constants.CARGO_HAND_DOWN);
                cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_INTAKE_SPEED);
                break;

            case cargoStop:
                handPiston.set(Constants.CARGO_HAND_UP);
                cargoMotor.set(ControlMode.PercentOutput, Constants.CARGO_INTAKE_SPEED);
                break;
            
        }
    }
   
    
}

//written by your boi josh