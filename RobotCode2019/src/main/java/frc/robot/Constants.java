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

	/* THINGS TO BE CHANGED BETWEEN ROBOTS

		COMPETITION:
			ELEVATOR:
				public static double ELEVATOR_KP_VALUE = 2.0;
				public static double ELEVATOR_KI_VALUE = 0.0;
				public static double ELEVATOR_KD_VALUE = 0.0;
				public static int ELEVATOR_KV_VALUE = 600;
				public static int ELEVATOR_KA_VALUE = 800;
				public static int ELEVATOR_REVERSE_MULTIPLIER = 1;
				talons on elevator should be commented and victors uncommented
				set sensor as falsed
				public static int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = -10;
				public static int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = -1485;
				public static int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = -3645;
				
				public static int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = -425;
				public static int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = -2745;
				public static int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = -4865;

				if works then switch endgame to sparks
				
		PRACTICE NUMBER 1:
			ELEVATOR:
				public static double ELEVATOR_KP_VALUE = 6;
				public static double ELEVATOR_KI_VALUE = 0;
				public static double ELEVATOR_KD_VALUE = 0;
				public static int ELEVATOR_KV_VALUE = 600;
				public static int ELEVATOR_KA_VALUE = 500;
				public static int ELEVATOR_REVERSE_MULTIPLIER = -1;
				victors on elevator should be commented and talons uncommented
				set sensor as false
				public static int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = 10;
				public static int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = 1485;
				public static int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = 3645;
				
				public static int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = 425;
				public static int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = 2745;
				public static int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = 4865;
	*/


	/* NAMING CONVENTIONS FOR CONSTANTS
		Exceptions Must Be Talked About With Sam Or Devin*
		<Subsystem>_<Specific Robot Function>_<1-2 word description>

		Ex.
			TELEOPH_INTAKE_SLOWLIFT
			DRIVE_LEFTWHEEL_AUTOSPEED

	*/

	// DRIVER Controller Values-----------------------------[
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
		
		public static final double TRIGGER_PRESSED_VALUE_THRESHOLD = 0.4;
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
		public static final int DRIVE_LEFT1_ID = 1;  
		public static final int DRIVE_LEFT2_ID = 2;  
		public static final int DRIVE_LEFT3_ID = 3;  
		
		public static final int DRIVE_RIGHT1_ID = 4;  
		public static final int DRIVE_RIGHT2_ID = 5; 
		public static final int DRIVE_RIGHT3_ID = 6;  

		public static final double DRIVE_DEADBAND_JOYSTICK = 0.000124; 

		public static final double DRIVE_TURNING_MULTIPLIER = 0.73;
	// Drive Constants--------------------------------------]

	// Elevator Constants-----------------------------------[ 
		public static int ELEVATOR_LIFT1_ID = 1;
		public static int ELEVATOR_LIFT2_ID = 2;
		public static int ELEVATOR_LIFT3_ID = 3;

		public static double ELEVATOR_MOTION_MAGIC_DEADBAND = 0.05;

		public static int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = 10;
		public static int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = 1485;
		public static int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = 3645;
				
		public static int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = 425;
		public static int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = 2745;
		public static int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = 4865;
		
		public static int ELEVATOR_INTAKE_METHOD_VALUE = 300;
		public static int ELEVATOR_RESET_ELEVATOR_VALUE = -10;
		
		public static int ELEVATOR_K_TIMEOUT_MS = 10;

		public static int ELEVATOR_PID_SLOT_NUMBER = 0;
		public static double ELEVATOR_KF_VALUE = 1.705;
		public static double ELEVATOR_KP_VALUE = 6;
		public static double ELEVATOR_KI_VALUE = 0;
		public static double ELEVATOR_KD_VALUE = 0;
		public static int ELEVATOR_KV_VALUE = 600;
		public static int ELEVATOR_KA_VALUE = 500;
		
		public static int ELEVATOR_REVERSE_MULTIPLIER = -1;
		public static int ELEVATOR_MANUAL_MOTION_MAGIC_MULTIPLIER = -50;
	// Elevator Constants-----------------------------------]

	// Compressor Constants---------------------------------]
		public static int UTILITIES_COMPRESSOR_PORT = 0;
	// Compressor Constants---------------------------------]

	// Endgame Constants------------------------------------[
		public static final int ENDGAME_ENDGAMELIFT_ID = 5;
		public static final int ENDGAME_ENDGAMELIFTFOLLOW1_ID = 6;
		public static final int ENDGAME_ENDGAMELIFTFOLLOW2_ID = 7;

		public static final int  ENDGAME_SPARK1_ID = 7;
		public static final int  ENDGAME_SPARK2_ID = 8;
		public static final int  ENDGAME_SPARK3_ID = 9;

		public static final double ENDGAME_LIFT_SPEED = 1.0;
		public static final double ENDGAME_WHEEL_SPEED = 0.1;
		public static final double ENDGAME_STOP_SPEED = 0.0;

		public static final double ENDGAME_MAX_TICKS = 9000;
		public static final double ENDGAME_MIN_TICKS = 0.0;
	// Endgame Constants------------------------------------]

	// Intake Constants-------------------------------------[
		public static final int HATCH_SOLENOID_PORT = 0;

		public static final boolean HATCH_SOLENOID_START = false;
		public static final	boolean HATCH_STATE_OPEN = true;
		public static final boolean HATCH_STATE_CLOSED = false;
	// Intake Constants------------------------------------]

	// Intake Arm Constants--------------------------------[
		public static final int ARM_SOLENOID_PORT = 3;

		public static final boolean ARM_STATE_DOWN = true;
		public static final boolean ARM_START_POSITION = false;
		public static final boolean ARM_STATE_UP = false;
	// Intake Arm Constants--------------------------------]

	// Intake Floor Hatch Constants--------------------------------[
		public static final int FLOOR_HATCH_SOLENOID_PORT = 1;
		public static final int FLOOR_HATCH_VICTOR_ID = 8;

		public static final int FLOOR_HATCH_PDP_VICTOR_PORT = 8;

		public static final double FLOOR_HATCH_INTAKE_SPEED = 1.0;
		public static final double FLOOR_HATCH_OUTTAKE_SPEED = -0.3;

		public static final double FLOOR_HATCH_CURRENT_LIMIT = 30.0;
		public static final double FLOOR_HATCH_RAISE_WAITTIME = 1.0;
		
		public static final boolean FLOOR_HATCH_INTAKE_UP = false;
		public static final boolean FLOOR_HATCH_INTAKE_DOWN = true;
	// Intake Floor Hatch Constants--------------------------------]

	// Cargo Intake Constants------------------------------[
		public static final int CARGO_SOLENOID_PORT = 4;
		public static final int CARGO_STOP_INTAKE_SOLENOID_PORT = 5;
		public static final int CARGO_VICTORSPX_ID = 4;
		
		public static final int CARGO_BRAKE_DELAY = 4;
		public static final double CARGO_UP_OUTTAKE_SPEED = 1;
		public static final double CARGO_OUTTAKE_SPEED = 0.5;
		public static final double CARGO_STOP_SPEED = 0.0;
		public static final int CARGO_BRAKE_TIMER_RESET = 0;
		public static final double CARGO_INTAKE_SPEED = -0.8;
		
		public static final boolean CARGO_HAND_DOWN = true;
		public static final boolean CARGO_BRAKE = false;
		public static final boolean CARGO_START_POSITION = false;
		public static final boolean CARGO_HAND_UP = false;
		public static final boolean CARGO_RELEASE = true;
	// Cargo Intake Constants------------------------------]

	// Camera-----------------------------------------------[
		public static final int UTIL_CAMERA_0_ID = 0;
		public static final int UTIL_CAMERA_1_ID = 1;

		public static final int CAMERA1_WIDTH = 160;
		public static final int CAMERA1_HEIGHT = 120;
		public static final int CAMERA1_FPS = 30;
		
		public static final int CAMERA2_WIDTH = 160;
		public static final int CAMERA2_HEIGHT = 120;
		public static final int CAMERA2_FPS = 30;
	// Camera-----------------------------------------------]

	// TeleopHandler----------------------------------------[
		public static final int TELEOPH_HATCH_BUTTON_SWITCH_R = 0;
		public static final int TELEOPH_HATCH_BUTTON_SWITCH_L = 1;
	// TeleopHandler----------------------------------------]

	// Vision Tracking--------------------------------------]
		public static final double VISION_DEFAULT_LIMELIGHT_RETURN_VALUE = 0.1234;
		public static final double VISION_HORIZONTAL_ANGLE_THRESHOLD = 1.0;
		public static final double VISION_HORIZONTAL_SPEED_THRESHOLD = 0.4;
		public static final double VISION_DEFAULT_HORIZONTAL_SPEED = 0.02;
		public static final double VISION_MAXIMUM_ANGLE = 27.0;
	// Vision Tracking--------------------------------------]

	//Sandstorm---------------------------------------------]
		public static final double SANDSTORM_TIMER_CARGO_OUT = 0.25;
		public static final double SANDSTORM_TIMER_CLOSED = 0.75;
	//Sandstorm---------------------------------------------]
		

}