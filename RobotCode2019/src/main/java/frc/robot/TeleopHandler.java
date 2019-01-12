/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;


public class TeleopHandler {

    private static Joystick driver;
    private static Joystick coDriver;

    public static void init() {
        
        driver = new Joystick(0);
        coDriver = new Joystick(1);

    }

    public static void run() {
    
        
    //Drive Code--------------------------------    
        Drive.run(driver.getRawAxis(1), driver.getRawAxis(4));
    //Drive Code--------------------------------

    }
    
}
