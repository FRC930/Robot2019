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

// DRIVER Controller Values-----------------------------[
	public static final double TRIGGER_PRESSED_VALUE_THRESHOLD = 0.4;

	public static final int DRIVER_CONTROLLER_ID = 0;
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
// DRIVER Controller Values-----------------------------]

// CoDRIVER Controller Values---------------------------[
	public static final int CODRIVER_CONTROLLER_ID = 1;
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
// CoDRIVER Controller Values---------------------------]

// Drive Constants--------------------------------------[
    public static final double DRIVE_DEADBAND_JOYSTICK = 0.000124;    
// Drive Constants--------------------------------------]

// Elevator Constants-----------------------------------[
	public static int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = 0;
    public static int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = 1;
    public static int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = 2 ;
    public static int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = 265;
    public static int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = 2350;
    public static int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = 4410;
	public static int ELEVATOR_RESET_ELEVATOR_VALUE = 10;
	public static double ELEVATOR_KF = 1.4614;
    public static double ELEVATOR_KP = 18.0;
    public static double ELEVATOR_KI = 0.070;
    public static double ELEVATOR_KD = 51.0;
    public static int ELEVATOR_VELOCITY = 650;
    public static int ELEVATOR_ACCELERATION = 1200;
    public static int ELEVATOR_PID_SLOT_NUMBER = 0;
    public static int ELEVATOR_K_TIMEOUT_MS = 10;
	public static int ELEVATOR_MANUAL_MOTION_MAGIC_MULTIPLIER = -50;
	public static int ELEVATOR_LIFT1 = 1;
	public static int ELEVATOR_LIFT2 = 2;
	public static int ELEVATOR_LIFT3 = 3;
	public static int ELEVATOR_INTAKE_METHOD_VALUE = 300;
// Elevator Constants-----------------------------------]

// Compressor Constants---------------------------------]
	public static int UTILITIES_COMPRESSOR_ID = 0;
// Compressor Constants---------------------------------]

// Endgame Constants------------------------------------[
	public static final double ENDGAME_JOYSTICK_DEADBAND = 0.1;
	public static final int ENDGAME_ENDGAMELIFT = 4;
	public static final int ENDGAME_ENDGAMELIFTFOLLOW1 = 5;
	public static final int ENDGAME_ENDGAMELIFTFOLLOW2 = 6;
	public static final int ENDGAME_POWER_DISTRIBUTION_PANEL = 0;
	public static final double ENDGAME_VOLTAGELIMIT = 30.0;
    public static final double ENDGAME_WHEELSPEED = 0.1;
    public static final double ENDGAME_LIFTSPEED = 1.0;
// Endgame Constants------------------------------------]

// Intake Constants-------------------------------------[
	public static final int HATCH_SOLENOID_PORT = 0;
	public static final boolean HATCH_SOLENOID_START = false;
// Intake Constants------------------------------------]

// Intake Arm Constants--------------------------------[
	public static final boolean ARM_START_POSITION = true;
	public static final int ARM_SOLENOID_PORT = 3;
// Intake Arm Constants--------------------------------]

// Intake Floor Hatch Constants--------------------------------[
	public static final int HATCH_FLOOR_SOLENOID = 1
	;
	public static final int HATCH_FLOOR_VICTOR = 8;
	public static final int HATCH_FLOOR_PDP_VICTOR = 8;
	public static final double HATCH_FLOOR_INTAKE_SPEED = 1.0;
	public static final double HATCH_FLOOR_OUTTAKE_SPEED = -0.3;
	public static final double HATCH_FLOOR_CURRENT_LIMIT = 30.0;
	public static final double HATCH_FLOOR_RAISE_WAITTIME = 1.0;
// Intake Floor Hatch Constants--------------------------------]

// Cargo Intake Constants------------------------------[
	public static final int CARGO_SOLENOID_PORT = 4;
	public static final int CARGO_VICTORSPX_PORT = 4;
	public static final boolean CARGO_START_POSITION = false;
	public static final double CARGO_INTAKE_SPEED = -1;
	public static final double CARGO_OUTTAKE_SPEED = 1;
	public static final double CARGO_STOP_SPEED = -0.4;
	public static final boolean CARGO_HAND_DOWN = true;
	public static final boolean CARGO_HAND_UP = false;
// Cargo Intake Constants------------------------------]

//Camera-----------------------------------------------

	public static final int CAMERA1_WIDTH = 160;
    public static final int CAMERA1_HEIGHT = 120;
    public static final int CAMERA1_FPS = 30;
    public static final int CAMERA2_WIDTH = 160;
    public static final int CAMERA2_HEIGHT = 120;
    public static final int CAMERA2_FPS = 30;

}
//devin smells a little bit like poo