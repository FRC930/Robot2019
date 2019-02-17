/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*  objective 
    -- To make the elevator move and move efficently based on the encoder position with motion magic.
*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.StatusFrameEnhanced;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */

public class Elevator {
    
    //Motor Objects
    public static TalonSRX lift1 = new TalonSRX(Constants.ELEVATOR_LIFT1);
    public static TalonSRX lift2 = new TalonSRX(Constants.ELEVATOR_LIFT2);
    public static TalonSRX lift3 = new TalonSRX(Constants.ELEVATOR_LIFT3);
    //public static VictorSPX lift2 = new VictorSPX(2);
    //public static VictorSPX lift3 = new VictorSPX(3);
    
    //Constants used through out code
    public static double stickElev;
    public static double targetPosition = 0.0;
    public static double actualPosition = 0.0;
    
    static {

    }
    //Enum list that defines heights of the elevator
    public static enum ElevatorStates {
      RocketLevelOneCargo(Constants.ELEVATOR_ROCKET_LEVEL_ONE_CARGO_VALUE),
      RocketLevelTwoCargo(Constants.ELEVATOR_ROCKET_LEVEL_TWO_CARGO_VALUE),
      RocketLevelThreeCargo(Constants.ELEVATOR_ROCKET_LEVEL_THREE_CARGO_VALUE),
      RocketLevelOneHatchAndPlayerStation(Constants.ELEVATOR_ROCKET_LEVEL_ONE_HATCH_VALUE),
      RocketLevelTwoHatch(Constants.ELEVATOR_ROCKET_LEVEL_TWO_HATCH_VALUE),
      RocketLevelThreeHatch(Constants.ELEVATOR_ROCKET_LEVEL_THREE_HATCH_VALUE ),
      ResetElevator(Constants.ELEVATOR_RESET_ELEVATOR_VALUE);
  
      //Actual Value of each enum
      private final int ElevatorPosition;
      
      //consturcture for each enum value
      ElevatorStates(int ElevatorPosition){
        this.ElevatorPosition = ElevatorPosition;
      }
      
      //gets hight of the enum that is called
      public int getElevatorPosition(){
        return this.ElevatorPosition;
      }
  }


    public static void init() {
      //Sets the other talons to follow
      lift2.follow(lift1);
      lift3.follow(lift1);
      
      //Sets the begining position
      lift1.setSelectedSensorPosition(0, 0, Constants.ELEVATOR_K_TIMEOUT_MS);
      
      //gives feedback from the encoder on the elevator to the talon for the position
      lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      /*sets the max and minum height for the elevator  
        -- so it can not go to far in either direction
      */
      lift1.configForwardSoftLimitThreshold(4510, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configReverseSoftLimitThreshold(50, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      //sets up the  fpid for pid functions
      lift1.selectProfileSlot(Constants.ELEVATOR_PID_SLOT_NUMBER, 0);
      lift1.config_kF(Constants.ELEVATOR_PID_SLOT_NUMBER, Constants.ELEVATOR_KF, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.config_kP(Constants.ELEVATOR_PID_SLOT_NUMBER, Constants.ELEVATOR_KP, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.config_kI(Constants.ELEVATOR_PID_SLOT_NUMBER, Constants.ELEVATOR_KI, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.config_kD(Constants.ELEVATOR_PID_SLOT_NUMBER, Constants.ELEVATOR_KD, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      /*
        Used for the motion of the elevator
        CruiseVelocity is the no acceleration part of trapizoid / top Acceleration is getting to top
        -- So that we can get to the correct position, we do this by using trapizoidal movement.
        -- Where we use the acceleration to ramp up  to the max speed and acts as the slated side of the trapizoid.
        -- The cruise velocity is the flat top of the trapizoid and that would be our speed at a constant rate 
        -- to get to the de-acceleration part that is also the other slant of the trapizoid.
      */
      lift1.configMotionCruiseVelocity(Constants.ELEVATOR_VELOCITY, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configMotionAcceleration(Constants.ELEVATOR_ACCELERATION, Constants.ELEVATOR_K_TIMEOUT_MS);
      
      /* Inverts sensorPhase
        ask sam and descibe why and what is a senseor phase
      */
      lift1.setSensorPhase(true);

      // Setting the max and minum speed of the elveator
      lift1.configNominalOutputReverse(0, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configNominalOutputForward(0, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configPeakOutputForward(1, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configPeakOutputReverse(-1, Constants.ELEVATOR_K_TIMEOUT_MS);
      
      //Gives PID and MotionMagic time to initialize
      lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.ELEVATOR_K_TIMEOUT_MS);

    }
    
    /* The run method is used for the manual elevator
        -- Manual Elevator: using the stick to give it the pure value of the stick to control the elevator
    */
    public static void run(double leftYstick) {

      //Sets the motor speed to the stick value to contorl the elevator
      lift1.set(ControlMode.PercentOutput, -leftYstick);

     
    }
 
  //This will set the elevator position to the enum value
  public static void setTargetPos(ElevatorStates pos1) {
    
    targetPosition = pos1.getElevatorPosition();
    //Getting the enum value and sending it to the talon to move the elevator to that position
    lift1.set(ControlMode.MotionMagic, pos1.getElevatorPosition());

   }

 
  
  /*
  ManualMotionMagic method will send the leftstick and multiplie it by the motionMagicMultiplier 
  and then send it to the elevator in motion magic mode
  */
  public static void manualMotionMagic(double leftYstick){
    
    //Sends the value form the leftStick times the Motion Magic Multiplier to the motor
    targetPosition = targetPosition + leftYstick * Constants.ELEVATOR_MANUAL_MOTION_MAGIC_MULTIPLIER;
        
    lift1.set(ControlMode.MotionMagic, targetPosition);

  }

  //This will see if we are within a ten degree range of our target position and return true or false
  public static boolean atPosition() {
    boolean Rtn = false;
    
    actualPosition = lift1.getSelectedSensorPosition();
    
    if(actualPosition > (targetPosition-10) && actualPosition < (targetPosition+10)){
      
      Rtn = true;
    
    }
    
    return Rtn;
  
  }

  //This will see if we are within a ten degree range of our enum target postion and return true or false
  public static boolean atPosition(ElevatorStates pos3) {
    
    boolean Rtn = false;
    
    actualPosition = lift1.getSelectedSensorPosition();
    
    if(actualPosition > (pos3.getElevatorPosition()-10) && actualPosition < (pos3.getElevatorPosition()+10)){

        Rtn = true;

    }

    return Rtn;
  
  }

  public static boolean atIntakePostiion(){

    if(lift1.getSelectedSensorPosition() <= Constants.ELEVATOR_INTAKE_METHOD_VALUE){
      return true;
    }
    
    else{

      return false;

    }

  }

  public static  void getSmartDashboardElevator(double leftYstick, boolean manual){


     //Sends the encoder position to smartdashboard
     SmartDashboard.putNumber("EncoderPosition", lift1.getSelectedSensorPosition());

     //Sends the manual calculation of our error and output it to smartdashboard
     SmartDashboard.putNumber("CalcError", lift1.getSelectedSensorPosition() - targetPosition);

     //Sends the joystick position to smartdashboard
     SmartDashboard.putNumber("Joystick", -leftYstick);

     //Sends our target position to smartdashboard
     SmartDashboard.putNumber("TargetPosition", targetPosition);

     //Sends our talon calculated error to smart dashboard
     SmartDashboard.putNumber("TalonError", lift1.getClosedLoopError());

     SmartDashboard.putBoolean("Manual Elevator Toggle", manual);
    

  }

  // public static int getElevatorState() {
    
  //     ElevatorStates pos;

  //     int elevatorStatePosition = pos.getElevatorPosition();

  //     return elevatorStatePosition;

  //   }
  }


  
