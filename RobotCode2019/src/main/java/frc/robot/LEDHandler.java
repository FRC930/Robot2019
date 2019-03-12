/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

// --------- Imports --------- \\

package frc.robot;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.I2C.Port;

public class LEDHandler {

    //---- Object Declarations ----\\
	private static I2C wire = new I2C(Port.kOnboard, Constants.arduinoAddress);;
    
    public static enum LEDStates {
        
    }

	// -- Variable Declarations --\\
	private static byte sendData = 0;
    
    static {

    }

    public static void init() {

    }

    public static void run() {

    }  
}  
