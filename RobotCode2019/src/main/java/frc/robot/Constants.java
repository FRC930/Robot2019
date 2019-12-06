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

	/* 

	THESE ARE VALUES THAT ARE FOR THE COMPETITION ROBOT

	AT COMPETITION, PLEASE COPY THESE VALUES AND REPLACE THEM WITH THE
	CURRENT VALUES IN THE Elevator Constants SECTION

	COMPETITION:
	ELEVATOR:
	*/

	/*
	public  double ELEVATOR_KP_VALUE = 2.0;
	public  double ELEVATOR_KI_VALUE = 0.0;
	public  double ELEVATOR_KD_VALUE = 0.0;
	public  int ELEVATOR_KV_VALUE = 600;
	public  int ELEVATOR_KA_VALUE = 800;
	public  int ELEVATOR_REVERSE_MULTIPLIER = 1;
	//talons on elevator.java should be commented and victors uncommented (line 30 in elevator.java)
	//set sensor as false (line 109 in Elevator.java)
	old values
	public  int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = -10;
	public  int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = -1485;
	public  int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = -3645;
	
	public  int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = -612;
	public  int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = -2745;
	public  int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = -4865;

	new values 4/24/19
		public  int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = -675;//10;
		public  int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = -2745;//1485;
		public  int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = -4865;//3645;
		public  int ELEVATOR_CARGO_INTAKE_POSITION = -200;
		
		public  int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = -425;
		public  int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = -2745;
		public  int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = -4865;

	*/
	//if works then switch endgame to sparks
		
	/*
	PRACTICE NUMBER 1:
	ELEVATOR:

	WHEN USING THE PRACTICE ROBOT, PLEASE COPY THESE VALUES AND REPLACE THEM WITH THE
	CURRENT VALUES IN THE Elevator Constants SECTION

	public  double ELEVATOR_KP_VALUE = 6;
	public  double ELEVATOR_KI_VALUE = 0;
	public  double ELEVATOR_KD_VALUE = 0;
	public  int ELEVATOR_KV_VALUE = 600;
	public  int ELEVATOR_KA_VALUE = 500;
	public  int ELEVATOR_REVERSE_MULTIPLIER = -1;

	//victors on elevator should be commented and talons uncommented
	//set sensor as false
	 old values
	public  int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = 10;
	public  int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = 1485;
	public  int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = 3645;
	
	public  int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = 425;
	public  int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = 2745;
	public  int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = 4865;

	 new values
		public  int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = -675;//10;
		public  int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = -2745;//1485;
		public  int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = -4865;//3645;
		public  int ELEVATOR_CARGO_INTAKE_POSITION = -200;
		
		public  int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = -425;
		public  int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = -2745;
		public  int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = -4865;
	*/


	/* NAMING CONVENTIONS FOR CONSTANTS
		Exceptions Must Be Talked About With Sam Or Devin*
		<Subsystem>_<Specific Robot Function>_<1-2 word description>

		Ex.
			TELEOPH_INTAKE_SLOWLIFT
			DRIVE_LEFTWHEEL_AUTOSPEED

	*/

	// DRIVER Controller Values-----------------------------[
		public  final int DRIVER_CONTROLLER_ID = 0;

		public  final int DRIVER_BUTTON_A = 1;
		public  final int DRIVER_BUTTON_B = 2;
		public  final int DRIVER_BUTTON_X = 3;
		public  final int DRIVER_BUTTON_Y = 4;
		public  final int DRIVER_BUTTON_LB = 5;
		public  final int DRIVER_BUTTON_RB = 6;
		public  final int DRIVER_BUTTON_BACK = 7;
		public  final int DRIVER_BUTTON_START = 8;
		public  final int DRIVER_BUTTON_LEFT_STICK = 9;
		public  final int DRIVER_BUTTON_RIGHT_STICK = 10;

		public  final int DRIVER_AXIS_LEFT_X = 0;
		public  final int DRIVER_AXIS_LEFT_Y = 1;
		public  final int DRIVER_AXIS_RIGHT_X = 4;
		public  final int DRIVER_AXIS_RIGHT_Y = 5;
		
		public  final int DRIVER_AXIS_LT = 2;
		public  final int DRIVER_AXIS_RT = 3;
		
		public  final double TRIGGER_PRESSED_VALUE_THRESHOLD = 0.4;
	// DRIVER Controller Values-----------------------------]

	// CoDRIVER Controller Values---------------------------[
		public  final int CODRIVER_CONTROLLER_ID = 1;

		public  final int CODRIVER_BUTTON_A = 1;
		public  final int CODRIVER_BUTTON_B = 2;
		public  final int CODRIVER_BUTTON_X = 3;
		public  final int CODRIVER_BUTTON_Y = 4;
		public  final int CODRIVER_BUTTON_LB = 5;
		public  final int CODRIVER_BUTTON_RB = 6;
		public  final int CODRIVER_BUTTON_BACK = 7;
		public  final int CODRIVER_BUTTON_START = 8;
		public  final int CODRIVER_BUTTON_LEFT_STICK = 9;
		public  final int CODRIVER_BUTTON_RIGHT_STICK = 10;

		public  final int CODRIVER_AXIS_LEFT_X = 0;
		public  final int CODRIVER_AXIS_LEFT_Y = 1;
		public  final int CODRIVER_AXIS_RIGHT_X = 4;
		public  final int CODRIVER_AXIS_RIGHT_Y = 5;
		public  final int CODRIVER_AXIS_LT = 2;
		public  final int CODRIVER_AXIS_RT = 3;
	// CoDRIVER Controller Values---------------------------]

	// Drive Constants--------------------------------------[
		public  final int DRIVE_LEFT1_ID = 1;  
		public  final int DRIVE_LEFT2_ID = 2;  
		public  final int DRIVE_LEFT3_ID = 3;  
		
		public  final int DRIVE_RIGHT1_ID = 4;  
		public  final int DRIVE_RIGHT2_ID = 5; 
		public  final int DRIVE_RIGHT3_ID = 6;  

		public  final double DRIVE_DEADBAND_JOYSTICK = 0.000124;

		public  final double DRIVE_TURNING_MULTIPLIER = 0.73;
		public  final int DRIVE_CURRENT_LIMIT_MAX = 99999;
		public  final double DRIVE_RAMP_RATE_MAX = 0.0;
		
	// Drive Constants--------------------------------------]

	// Elevator Constants-----------------------------------[ 
		public  int ELEVATOR_LIFT1_ID = 1;
		public  int ELEVATOR_LIFT2_ID = 2;
		public  int ELEVATOR_LIFT3_ID = 3;
	
		public  double ELEVATOR_MOTION_MAGIC_DEADBAND = 0.05;

		//Comepetition Elevator Values
		/*
		public  int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = -10; //
		public  int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = -1485; //-1420  //-1485 old		//1742
		public  int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = -3645; //-3534  //-3645	old	//3874
		
		public  int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = -612;		//-428  //-612 old
		public  int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = -2745;	//-2480	//-2745 old	//2646
		public  int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = -4865;	//-4565 //-4865	old	//4740
		*/

		
		public  double ELEVATOR_KP_VALUE = 2;
		public  double ELEVATOR_KI_VALUE = 0;
		public  double ELEVATOR_KD_VALUE = 0;
		public  int ELEVATOR_KV_VALUE = 600;
		public  int ELEVATOR_KA_VALUE = 800;
		public  int ELEVATOR_REVERSE_MULTIPLIER = 1;

		public  int ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE = -639;//10; // -675
		public  int ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE = -2662;//1485; // -2745
		public  int ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE = -4828;//3645; // -4865
		public  int ELEVATOR_CARGO_INTAKE_POSITION = -110;//-170;
		
		public  int ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE = -425;//-625;//-648;//-597; // -425
		public  int ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE = -2538;//-2760;//-2649; //-2745
		public  int ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE = -4652;//-4753;//-4658; //-4865

		public  int ELEVATOR_INTAKE_METHOD_VALUE = -500;//300
		public  int ELEVATOR_RESET_ELEVATOR_VALUE = -10;
		
		public  int ELEVATOR_K_TIMEOUT_MS = 10;

		public  int ELEVATOR_PID_SLOT_NUMBER = 0;
		public  double ELEVATOR_KF_VALUE = 1.705;

		//Competition Elevator Values
		/*
		public  double ELEVATOR_KP_VALUE = 2.0;
		public  double ELEVATOR_KI_VALUE = 0.0;
		public  double ELEVATOR_KD_VALUE = 0.0;
		public  int ELEVATOR_KV_VALUE = 600;
		public  int ELEVATOR_KA_VALUE = 800;
		public  int ELEVATOR_REVERSE_MULTIPLIER = 1;
		*/

		public  int ELEVATOR_MANUAL_MOTION_MAGIC_MULTIPLIER = -50;
	// Elevator Constants-----------------------------------]

	// Compressor Constants---------------------------------]
		public  int UTILITIES_COMPRESSOR_PORT = 0;
		public  final boolean COMPRESSOR_ON = true;
		public  final boolean COMPRESSOR_OFF = false;
	// Compressor Constants---------------------------------]

	// Endgame Constants------------------------------------[
		//public  final int ENDGAME_ENDGAMELIFT_ID = 7;  //5
		//public  final int ENDGAME_ENDGAMELIFTFOLLOW1_ID = 8;  //6
		//public  final int ENDGAME_ENDGAMELIFTFOLLOW2_ID = 9;  //7
		public  final int ENDGAME_ENCODER_ID1 = 3;
		public  final int ENDGAME_ENCODER_ID2 = 4;

		public  final double ENDGAME_PISTON_EXTENSION_DELAY = 0.5;
		public  final double ENDGAME_BACKUP_TIME_LIMIT = 0.1;
		public  final double ENDGAME_WHEELS_TIME_LIMIT = 0.25;

		public  final int ENDGAME_DEACTIVATION_TIME = 1;

		public  final boolean ENDGAME_PISTON_EXTENDED = true;
		public  final boolean ENDGAME_PISTON_RETRACTED = false;
		//bottom limit -25
		//-486
		// up all the way to get up-767
		// absoulute max -807
		public  final double ENDGAME_ENCODER_PISTON_UP = -280;
		public  final double ENDGAME_ENCODER_POINT_NO_RETURN = -486;
		public  final double ENDGAME_ENCODER_STOP_MOVING = -25;
		public  final double ENDGAME_ENCODER_STOP_FOOT_LIMIT = -767;
		
		public  final double ENDGAME_SPEED_LIMIT_WHEEL_FORWARD = 0.2;
		public  final double ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS = -0.2;
		public  final double ENDGAME_SPEED_LIMIT_FOOT_DOWN_2 = 1.0;
		public  final double ENDGAME_SPEED_LIMIT_FOOT_DOWN = 0.5;//0.4;

		public  final double ENDGAME_AUTO_UP_DEADBAND = -0.5;
		public  final double ENDGAME_AUTO_DOWN_DEADBAND = 0.5;
		public  final int  ENDGAME_SPARK1_ID = 7;
		public  final int  ENDGAME_SPARK2_ID = 9;
		public  final int  ENGGAME_SOLENOID_ID = 6;

		public  final double ENDGAME_STOPMOTION_TIME_DELAY = 0.0;
		public  final double ENDGAME_LIFT_SPEED = 1.0;
		public  final double ENDGAME_WHEEL_SPEED = 0.1;
		public  final double ENDGAME_STOP_SPEED = 0.0;

		public  final double ENDGAME_MAX_TICKS = 9000;
		public  final double ENDGAME_MIN_TICKS = 0.0;
	// Endgame Constants------------------------------------]

	// Hatch Intake Constants-------------------------------------[
		public  final int HATCH_SOLENOID_PORT = 0;

		public  final boolean HATCH_SOLENOID_START = false;
		public  final	boolean HATCH_STATE_OPEN = false;
		public  final boolean HATCH_STATE_CLOSED = true;

		public  final int HATCH_LT_HOLD_THRESHOLD = 20;

		public  final double HATCH_PUSHER_PUSH_TIME = 0.5;//0.75;//1.0;
	// Hatch Intake Constants------------------------------------]

	// Intake Arm Constants--------------------------------[
		public  final int ARM_SOLENOID_PORT = 3;

		public  final boolean ARM_STATE_DOWN = true;
		public  final boolean ARM_START_POSITION = false;
		public  final boolean ARM_STATE_UP = false;
	// Intake Arm Constants--------------------------------]

	// Intake Floor Hatch Constants--------------------------------[
		//public  final int FLOOR_HATCH_SOLENOID_PORT = 1;
		public  final int FLOOR_HATCH_VICTOR_ID = 8;

		public  final int FLOOR_HATCH_PDP_VICTOR_PORT = 8;

		public  final double FLOOR_HATCH_INTAKE_SPEED = 1.0;
		public  final double FLOOR_HATCH_OUTTAKE_SPEED = -0.3;

		public  final double FLOOR_HATCH_CURRENT_LIMIT = 30.0;
		public  final double FLOOR_HATCH_RAISE_WAITTIME = 1.0;
		
		public  final boolean FLOOR_HATCH_INTAKE_UP = false;
		public  final boolean FLOOR_HATCH_INTAKE_DOWN = true;
	// Intake Floor Hatch Constants--------------------------------]

	// Cargo Intake Constants------------------------------[
		public  final int CARGO_SOLENOID_PORT = 4;
		public  final int CARGO_STOP_INTAKE_SOLENOID_PORT = 5;
		public  final int CARGO_VICTORSPX_ID = 4;
		
		public  final int CARGO_BRAKE_DELAY = 4;
		public  final double CARGO_UP_OUTTAKE_SPEED = 1;
		public  final double CARGO_OUTTAKE_SPEED = 0.75;//0.5;
		public  final double CARGO_STOP_SPEED = -0.2;//0.0;
		public  final int CARGO_BRAKE_TIMER_RESET = 0;
		public  final double CARGO_INTAKE_SPEED = -0.8;
		
		public  final boolean CARGO_HAND_RETRACTED = true;
		public  final boolean CARGO_BRAKE = false;
		public  final boolean CARGO_START_POSITION = false;
		public  final boolean CARGO_HAND_EXTENDED = false;
		public  final boolean CARGO_RELEASE = true;
		public  final boolean CARGO_TOP_PISTON_EXTENDED = false;
		public  final boolean CARGO_TOP_PISTON_RETRACTED = true;
	// Cargo Intake Constants------------------------------]

	// Camera-----------------------------------------------[
		public  final int UTIL_CAMERA_0_ID = 0;
		public  final int UTIL_CAMERA_1_ID = 1;

		public  final int CAMERA1_WIDTH = 160;
		public  final int CAMERA1_HEIGHT = 120;
		public  final int CAMERA1_FPS = 30;
		
		public  final int CAMERA2_WIDTH = 160;
		public  final int CAMERA2_HEIGHT = 120;
		public  final int CAMERA2_FPS = 30;
	// Camera-----------------------------------------------]

	// TeleopHandler----------------------------------------]
		public  final int TELEOPH_HATCH_BUTTON_SWITCH_R = 0;
		public  final int TELEOPH_HATCH_BUTTON_SWITCH_L = 1;
	// TeleopHandler----------------------------------------]

	// Vision Tracking--------------------------------------]
		public  final double VISION_DEFAULT_LIMELIGHT_RETURN_VALUE = 0.1234;
		public  final double VISION_HORIZONTAL_ANGLE_THRESHOLD = 0.6; //we want this to be tight!
		public  final double VISION_HORIZONTAL_SPEED_THRESHOLD = 0.4;
		public  final double VISION_DEFAULT_HORIZONTAL_SPEED = 0.4;
		public  final double VISION_MAXIMUM_ANGLE = 27.0;
		public  final double VISION_TARGET_AREA_UPPER_THRESHOLD = 16.5;//18.0;
		public  final double VISION_TARGET_AREA_LOWER_THRESHOLD = 14.0;
		public  final int VISION_FRAME_LIMIT = 1;
		public  final double VISION_AREA_FOR_ELEVATOR = 2;
		public  final int VISION_ELEVATOR_LOOP_LIMIT = 2;
	// Vision Tracking--------------------------------------]

	//Sandstorm---------------------------------------------]
		public  final double SANDSTORM_TIMER_CARGO_OUT = 0.25;
		public  final double SANDSTORM_TIMER_CLOSED = 0.75;
	//Sandstorm---------------------------------------------]

	//HatchPusher-------------------------------------------]
		public  final int HATCH_PUSHER_PISTON_PORT = 2;
	//HatchPusher-------------------------------------------]

	//Rumble-------------------------------------------------]
		public  final double RUMBLE_STOP = 0.0;
		public  final double RUMBLE_FULL_INTENSITY = 1.0;
	//Rumble-------------------------------------------------]

}