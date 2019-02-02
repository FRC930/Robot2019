/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;


public class TeleopHandler {

    // Driver joystick
    private static Joystick driver;
    // Codriver joystick
    private static Joystick coDriver;
    //Intake Arm Code
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
        Drive.run(driver.getRawAxis(4), driver.getRawAxis(1));
    // Drive Code--------------------------------

    // Intake Code-------------------------------
        HatchIntake.run(coDriver.getRawButton(Constants.INTAKE_CODRIVER_BUTTON));
    // Intake Code-------------------------------

    // Intake Arm Code---------------------------

    if(coDriver.getRawButton(5) == true && armStatus == false)
    {
      armStatus = true;
      /*
      If LB is pressed and the button control is false, set button control true.
      */
    }
    if(coDriver.getRawButton(5) == false && armStatus == true)
    {
      armStatus = false;
      armActivity = !armActivity;
      /*
      If LB is pressed and the button control is true, set button control false and set armActivity opposite to itself.
      */
    }
    IntakeArm.run(armActivity);


    // Endgame Code------------------------------
        Endgame.run(coDriver.getRawAxis(1));
    // Endgame Code------------------------------

    }

}
