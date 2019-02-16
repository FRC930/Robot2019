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
    public static TalonSRX lift1 = new TalonSRX(1);
    public static TalonSRX lift2 = new TalonSRX(2);
    public static TalonSRX lift3 = new TalonSRX(3);
    //public static VictorSPX lift2 = new VictorSPX(2);
    //public static VictorSPX lift3 = new VictorSPX(3);
    public static double stickElev;
    public static double targetPosition = 0.0;
    public static double actualPosition = 0.0;
    
    static {

    }
    //Enum list with varibels set to them 
    public enum ElevatorStates {
      RocketLevelOneCargo(Constants.ROCKET_LEVEL_ONE_CARGO_VALUE),
      RocketLevelTwoCargo(Constants.ROCKET_LEVEL_TWO_CARGO_VALUE),
      RocketLevelThreeCargo(Constants.ROCKET_LEVEL_THREE_CARGO_VALUE),
      RocketLevelOneHatchAndPlayerStation(Constants.ROCKET_LEVEL_ONE_HATCH_VALUE),
      RocketLevelTwoHatch(Constants.ROCKET_LEVEL_TWO_HATCH_VALUE),
      RocketLevelThreeHatch(Constants.ROCKET_LEVEL_THREE_HATCH_VALUE ),
      ResetElevator(Constants.RESET_ELEVATOR_VALUE);
  
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
      lift1.setSelectedSensorPosition(0, 0, Constants.K_TIMEOUT_MS);
      lift1.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder, 0, Constants.K_TIMEOUT_MS);
  
      //sets limit of where it should go
      lift1.configForwardSoftLimitThreshold(-5500, Constants.K_TIMEOUT_MS);
      lift1.configReverseSoftLimitThreshold(-50, Constants.K_TIMEOUT_MS);
  
      //sets up the  fpid for pid functions
      lift1.selectProfileSlot(Constants.PID_SLOT_NUMBER, 0);
      lift1.config_kF(Constants.PID_SLOT_NUMBER, Constants.KF, Constants.K_TIMEOUT_MS);
      lift1.config_kP(Constants.PID_SLOT_NUMBER, Constants.KP, Constants.K_TIMEOUT_MS);
      lift1.config_kI(Constants.PID_SLOT_NUMBER, Constants.KI, Constants.K_TIMEOUT_MS);
      lift1.config_kD(Constants.PID_SLOT_NUMBER, Constants.KD, Constants.K_TIMEOUT_MS);
  
      //CruiseVelocity is the no exceleration part of trapizoid / top Acceleration is getting to top
      lift1.configMotionCruiseVelocity(Constants.VELOCITY, Constants.K_TIMEOUT_MS);
      lift1.configMotionAcceleration(Constants.ACCELERATION, Constants.K_TIMEOUT_MS);
  
      lift1.setSensorPhase(true);

      //Nominal out put is lowest limit and peak is highest    
      lift1.configNominalOutputReverse(0, Constants.K_TIMEOUT_MS);
      lift1.configNominalOutputForward(0, Constants.K_TIMEOUT_MS);
      lift1.configPeakOutputForward(1, Constants.K_TIMEOUT_MS);
      lift1.configPeakOutputReverse(-1, Constants.K_TIMEOUT_MS);
  
      //sets the sensor to the bootom/0
      lift1.setSelectedSensorPosition(0, 0, Constants.K_TIMEOUT_MS);
  
      lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_13_Base_PIDF0, 10, Constants.K_TIMEOUT_MS);
      lift1.setStatusFramePeriod(StatusFrameEnhanced.Status_10_MotionMagic, 10, Constants.K_TIMEOUT_MS);

    }

    public static void run(double leftYstick) {

      //Sets the motor to precent output with the leftstick
      lift1.set(ControlMode.PercentOutput, -leftYstick);

      
    
      
      SmartDashboard.putNumber("EncoderPosition", lift1.getSelectedSensorPosition());
      SmartDashboard.putNumber("CalcError", lift1.getSelectedSensorPosition() - targetPosition);
      SmartDashboard.putNumber("Joystick", -leftYstick);
      SmartDashboard.putNumber("TargetPosition", targetPosition);
      SmartDashboard.putNumber("TalonError", lift1.getClosedLoopError());
    }
 

  public static void setTargetPos(ElevatorStates pos1) {

    //Sets in motion magic when set target position was used
    lift1.set(ControlMode.MotionMagic, pos1.getElevatorPosition());

  }
  
  public static void manualMotionMagic(double leftYstick){
    
    //Sets motion magic to the left stick
    targetPosition = targetPosition + leftYstick * Constants.MANUAL_MOTION_MAGIC_MULTIPLIER;
        lift1.set(ControlMode.MotionMagic, targetPosition);

  }

  public static boolean atPosition() {
    actualPosition = lift1.getSelectedSensorPosition();
    if(actualPosition > (targetPosition-10) && actualPosition < (targetPosition+10)){
      
      return true;
    
    }
    
    else{
      
      return false;
    
    }
  }

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


  
