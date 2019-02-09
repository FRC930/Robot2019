/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Constant values to be used in other areas of the code
 */
public class Constants {

/* NAMING CONVENTIONS FOR CONSTANTS
    Exceptions Must Be Talked About With Sam Or Devin*
	<Subsystem>_<Specific Robot Function>_<1-2 word description>

	Ex.
		TELEOPH_INTAKE_SLOWLIFT
		DRIVE_LEFTWHEEL_AUTOSPEED

*/

// DRIVER Controller Values-----------------------------

	public static final double TRIGGER_PRESSED_VALUE_THRESHOLD = 0.4;

	public static final int DRIVER_BUTTON_A = 1;
	public static final int DRIVER_BUTTON_B = 2;
	public static final int DRIVER_BUTTON_X = 3;
	public static final int DRIVER_BUTTON_Y = 4;
	public static final int DRIVER_BUTTON_LB = 5;
	public static final int DRIVER_BUTTON_RB = 6;
	public static final int DRIVER_BUTTON_BACK = 7;
	public static final int DRIVER_BUTTON_START = 8;
	public static final int DRIVER_BUTTON_LEFT_STICK = 9;
	public static final int DRIVER_BUTTON_RIGHT_STICK = 10;

	public static final int DRIVER_AXIS_LEFT_X = 0;
	public static final int DRIVER_AXIS_LEFT_Y = 1;
	public static final int DRIVER_AXIS_RIGHT_X = 4;
	public static final int DRIVER_AXIS_RIGHT_Y = 5;
	public static final int DRIVER_AXIS_LT = 2;
	public static final int DRIVER_AXIS_RT = 3;
// DRIVER Controller Values-----------------------------

// CoDRIVER Controller Values---------------------------
	public static final int CODRIVER_BUTTON_A = 1;
	public static final int CODRIVER_BUTTON_B = 2;
	public static final int CODRIVER_BUTTON_X = 3;
	public static final int CODRIVER_BUTTON_Y = 4;
	public static final int CODRIVER_BUTTON_LB = 5;
	public static final int CODRIVER_BUTTON_RB = 6;
	public static final int CODRIVER_BUTTON_BACK = 7;
	public static final int CODRIVER_BUTTON_START = 8;
	public static final int CODRIVER_BUTTON_LEFT_STICK = 9;
	public static final int CODRIVER_BUTTON_RIGHT_STICK = 10;

	public static final int CODRIVER_AXIS_LEFT_X = 0;
	public static final int CODRIVER_AXIS_LEFT_Y = 1;
	public static final int CODRIVER_AXIS_RIGHT_X = 4;
	public static final int CODRIVER_AXIS_RIGHT_Y = 5;
	public static final int CODRIVER_AXIS_LT = 2;
	public static final int CODRIVER_AXIS_RT = 3;
// CoDRIVER Controller Values---------------------------

// Drive Constants-----------------------------------
    public static final double DRIVE_THRESHOLD_JOYSTICK = 0.005;    
// Drive Constants-----------------------------------

// Elevator Constants-----------------------------------
   
// Elevator Constants-----------------------------------

// Endgame Constants------------------------------------
	public static final double ENDGAME_JOYSTICK_DEADBAND = 0.1;
// Endgame Constants------------------------------------

// Intake Constants-------------------------------------
	public static final int HATCH_SOLENOID_PORT = 0;
	public static final boolean HATCH_SOLENOID_START = false;
// Intake Constants------------------------------------

// Intake Arm Constants---------------------------------
	public static final boolean ARM_START_POSITION = true;
	public static final int ARM_SOLENOID_PORT = 0;
// Intake Arm Constants---------------------------------

}
//devin smells a little bit like poo