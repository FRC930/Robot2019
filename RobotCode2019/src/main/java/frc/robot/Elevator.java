/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

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
    public static TalonSRX lift1 = new TalonSRX(Constants.ELEVATOR_LIFT1);
    public static TalonSRX lift2 = new TalonSRX(Constants.ELEVATOR_LIFT2);
    public static TalonSRX lift3 = new TalonSRX(Constants.ELEVATOR_LIFT3);
    //public static VictorSPX lift2 = new VictorSPX(2);
    //public static VictorSPX lift3 = new VictorSPX(3);
    public static double stickElev;
    public static double targetPosition = 0.0;
    public static double actualPosition = 0.0;
    
    static {

    }
    //Enum list with varibels set to them 
    public enum ElevatorStates {
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
      
      //gets feed back from encoder
      lift1.setSelectedSensorPosition(0, 0, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      //sets limit of where it should go
      lift1.configForwardSoftLimitThreshold(-5500, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configReverseSoftLimitThreshold(-50, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      //sets up the  fpid for pid functions
      lift1.selectProfileSlot(Constants.ELEVATOR_PID_SLOT_NUMBER, 0);
      lift1.config_kF(Constants.ELEVATOR_PID_SLOT_NUMBER, Constants.ELEVATOR_KF, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.config_kP(Constants.ELEVATOR_PID_SLOT_NUMBER, Constants.ELEVATOR_KP, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.config_kI(Constants.ELEVATOR_PID_SLOT_NUMBER, Constants.ELEVATOR_KI, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.config_kD(Constants.ELEVATOR_PID_SLOT_NUMBER, Constants.ELEVATOR_KD, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      //CruiseVelocity is the no exceleration part of trapizoid / top Acceleration is getting to top
      lift1.configMotionCruiseVelocity(Constants.ELEVATOR_VELOCITY, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configMotionAcceleration(Constants.ELEVATOR_ACCELERATION, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      lift1.setSensorPhase(true);

      //Nominal out put is lowest limit and peak is highest    
      lift1.configNominalOutputReverse(0, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configNominalOutputForward(0, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configPeakOutputForward(1, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.configPeakOutputReverse(-1, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      //sets the sensor to the bootom/0
      lift1.setSelectedSensorPosition(0, 0, Constants.ELEVATOR_K_TIMEOUT_MS);
  
      lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.ELEVATOR_K_TIMEOUT_MS);
      lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.ELEVATOR_K_TIMEOUT_MS);

    }

    public static void run(double leftYstick) {

      //Sets the motor to precent output with the leftstick
      lift1.set(ControlMode.PercentOutput, -leftYstick);

      
    
      //Puts the encoder position out to smart dash board
      SmartDashboard.putNumber("EncoderPosition", lift1.getSelectedSensorPosition());

      //We manual calculate our error and out put it to smart dash board
      SmartDashboard.putNumber("CalcError", lift1.getSelectedSensorPosition() - targetPosition);

      //Outputs the joystick position to smartdashboard
      SmartDashboard.putNumber("Joystick", -leftYstick);

      //Outputs our target position to smartdashboard
      SmartDashboard.putNumber("TargetPosition", targetPosition);

      //Outputs our talon calculated error to smart dashboard
      SmartDashboard.putNumber("TalonError", lift1.getClosedLoopError());
    }
 
  //SetTargetPos will set the elevator position to the enum state
  public static void setTargetPos(ElevatorStates pos1) {

    //Sets in motion magic when set target position was used
    lift1.set(ControlMode.MotionMagic, pos1.getElevatorPosition());

  }
  
  /*
  ManualMotionMagic method will send the  leftstick and multiplie it by the motionMagicMultiplier 
  and then send it to the elevator in motion magic mode
  */
  public static void manualMotionMagic(double leftYstick){
    
    //Sets motion magic to the left stick
    targetPosition = targetPosition + leftYstick * Constants.ELEVATOR_MANUAL_MOTION_MAGIC_MULTIPLIER;
        lift1.set(ControlMode.MotionMagic, targetPosition);

  }

  //The atPosition method will see if we are within a ten degree range of our target
  public static boolean atPosition() {
    actualPosition = lift1.getSelectedSensorPosition();
    if(actualPosition > (targetPosition-10) && actualPosition < (targetPosition+10)){
      
      return true;
    
    }
    
    else{
      
      return false;
    
    }
  }

  //This at position method will test to see if we are at the enum position
  public static boolean atPosition(ElevatorStates pos3) {
    actualPosition = lift1.getSelectedSensorPosition();
    if(actualPosition > (pos3.getElevatorPosition()-10) && actualPosition < (pos3.getElevatorPosition()+10)){
      
      return true;
    
    }
    
    else{
      
      return false;
    
    }
  }

  // public static int getElevatorState() {
    
  //     ElevatorStates pos;

  //     int elevatorStatePosition = pos.getElevatorPosition();

  //     return elevatorStatePosition;

  //   }
  }


  
