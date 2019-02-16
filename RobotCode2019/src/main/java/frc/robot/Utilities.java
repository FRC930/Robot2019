/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Compressor;

/**
 * Code for components that don't fit in a subsystem, or can be used by multiple subsystems.
 * 
 * Ex: Compressor isn't related to a subsystem.
 *     PDP can be used by all subsystems.
 */
public class Utilities {
    //Sets up a compressor
    public static Compressor compress = new Compressor(Constants.COMPRESSOR_ID);
    
    static {
        //turns on the compressor
        compress.setClosedLoopControl(true);

    }

    public static void init() {

    }

    public static void run() {
        
    }
    
}
