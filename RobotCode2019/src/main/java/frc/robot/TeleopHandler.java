/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;

import edu.wpi.first.wpilibj.*;


public class TeleopHandler {

    // Driver joystick
    private static Joystick driver;
    // Codriver joystick
    private static Joystick coDriver;
    // Intake Arm Code
    private static boolean armStatus = true;
    private static boolean armActivity = true;

    static {
        
    }

    // To be initialized at start of teleop period
    public static void init() {
        
        driver = new Joystick(0);
        coDriver = new Joystick(1);

    }

    // To be run during teleop periodic
    public static void run() {
        
        // Drive Code--------------------------------    
            Drive.run(driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_X), driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));
        // Drive Code--------------------------------

        // Intake Code-------------------------------
            HatchIntake.run(coDriver.getRawButton(Constants.CODRIVER_BUTTON_A));
        // Intake Code-------------------------------

        // Arm Intake Code---------------------------
            // If LB is pressed and the button control is false, set button control true
            if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB) == true && armStatus == false) {
                armStatus = true;
            }

            // If LB is pressed and the button control is true, set button control false and set armActivity opposite to itself
            if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_RB) == false && armStatus == true) {
                armStatus = false;
                armActivity = !armActivity;
            
            }

            IntakeArm.run(armActivity);
        // Arm Intake Code---------------------------


        // Endgame Code------------------------------
            Endgame.run(coDriver.getRawAxis(Constants.CODRIVER_AXIS_RIGHT_Y));
        // Endgame Code------------------------------

        // Cargo Intake Code-------------------------

            if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)) //Motor control sets speed for inttake. Hand is out.
            {
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoIntake);
            }
            else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_B)) //Motor control sets speed for outtake. Hand is out.
            {
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoOutTake);
            }
            else //Motor control sets speed to stop. Hand is held up.
            {
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoStop);
            }
            

    }
    
    

    private static boolean isTriggerPressed(double axisValue) {
        
        if (axisValue >= Constants.TRIGGER_PRESSED_VALUE_THRESHOLD)
            return true;
        else
            return false;

    }

}
