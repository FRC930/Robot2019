/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.*;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.Joystick;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;



/**
 * Lifts the robot onto the third platform during the Endgame
 */
public class Endgame {
  
  // Endgame Motor Controllers
  // private static final TalonSRX endgameLift = new TalonSRX(Constants.ENDGAME_ENDGAMELIFT_ID);
  // private static final VictorSPX endgameLiftFollow1 = new VictorSPX(Constants.ENDGAME_ENDGAMELIFTFOLLOW1_ID);
  // private static final VictorSPX endgameLiftFollow2 = new VictorSPX(Constants.ENDGAME_ENDGAMELIFTFOLLOW2_ID);
  private static final Encoder endgamecounter = new Encoder(Constants.ENDGAME_ENCODER_ID1, Constants.ENDGAME_ENCODER_ID2);
  private static final CANSparkMax endGameOne = new CANSparkMax(Constants.ENDGAME_SPARK1_ID, MotorType.kBrushless);
  //private static final CANSparkMax endGameTwo = new CANSparkMax(Constants.ENDGAME_SPARK2_ID, MotorType.kBrushless);
  private static final CANSparkMax endGameThree = new CANSparkMax(Constants.ENDGAME_SPARK3_ID, MotorType.kBrushless);
  

  //solenoid that controls the piston on the end game
  private static final Solenoid endGamePistonController = new Solenoid(Constants.ENGGAME_SOLENOID_ID);
  //Sets up Volts Variable for later
  private static double Volt = 0.0;

  //sets up a varable for the encoder ticks
  public static double ticks = 0.0;
  
  //sets up a cubed stick value
  public static double leftYStickCubed;
  
  private static double previousTicks = 0;
  private static int stopEndgame = 0;
  
  private static final Timer endgametimer = new Timer();
  private static final Timer backuptimer = new Timer();
  private static final Timer pistonTimer = new Timer();
  private static final Timer stopMotionTimer = new Timer();

  private static enum EndgameStates{

    BACK_PISTION_DOWN,
    BACK_PISTON_UP,
    FOOT_AND_WHEELS,
    STOP_MOTION,
    CONTINUE_FOOT_AND_WHEELS,
    STOP_FOOT,
    STOP_WHEELS,
    BACKUP_WHEELS,
    STOP;
  }

  static EndgameStates EndgameState;

  static {

    // Mirror primary motor controller
    // endgameLiftFollow1.follow(endgameLift);
    // endgameLiftFollow2.follow(endgameLift);

    //endGameTwo.follow(endGameOne);
    endGameThree.follow(endGameOne);

    //Volt = Utilities.Power.getVoltage(); 

  }

  public static void init() {
    EndgameState = EndgameStates.BACK_PISTION_DOWN;
    endgamecounter.reset();


  }

  public static void runAuto(double endgameLeftStick) {
    SmartDashboard.putNumber("EndgameEncoderPostion", endgamecounter.getRaw());
    SmartDashboard.putNumber("Encoder Postion", endgamecounter.getRaw());
    System.out.println(EndgameState);
    ticks = endgamecounter.getRaw();
    
    switch(EndgameState) {
      case BACK_PISTON_UP:
        endGamePistonController.set(Constants.ENDGAME_PISTON_UP);
        if(endgameLeftStick <= Constants.ENDGAME_AUTO_UP_DEADBAND){
          EndgameState = EndgameStates.BACK_PISTION_DOWN;
        }
      break;
    //this part only pushes the piston down
      case BACK_PISTION_DOWN: 
        endGamePistonController.set(Constants.ENDGAME_PISTON_DOWN);
        if(pistonTimer.get() == 0){
          pistonTimer.start();
        }
        
        System.out.println(pistonTimer.get());
        Utilities.compressorState(Constants.COMPRESSOR_OFF);
        if(endgameLeftStick >= Constants.ENDGAME_AUTO_DOWN_DEADBAND){
          EndgameState = EndgameStates.BACK_PISTION_DOWN;
        }
        if(pistonTimer.get() >= Constants.ENDGAME_PISTON_TIME_LIMIT){
          EndgameState = EndgameStates.FOOT_AND_WHEELS;
          pistonTimer.stop();
          pistonTimer.reset();
        }
        break;
      
      //drives foot up and drivetrain wheels forward at the same time
      case FOOT_AND_WHEELS:
        Drive.runAt(-Constants.ENDGAME_SPEED_LIMIT_WHEEL_FORWARD, Constants.ENDGAME_SPEED_LIMIT_WHEEL_FORWARD);
        
        endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_FOOT_DOWN);
        
      /*
        if(stopEndgame >= Constants.ENDGAME_DEACTIVATION_TIME * 50){
          EndgameState = EndgameStates.STOP;
        }
      */
        

        

        if(ticks <= Constants.ENDGAME_ENCODER_PISTON_UP){
          endGamePistonController.set(Constants.ENDGAME_PISTON_UP);
        }

        if (ticks >= Constants.ENDGAME_ENCODER_BACKWARDS_LIMIT && endgameLeftStick >= Constants.ENDGAME_AUTO_DOWN_DEADBAND && ticks >= Constants.ENDGAME_ENCODER_STOP_MOVING){
          endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_FOOT_UP);
        }
        else if(ticks >= Constants.ENDGAME_ENCODER_STOP_MOVING && endgameLeftStick >= Constants.ENDGAME_AUTO_DOWN_DEADBAND){
            endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_STOP);
            EndgameState = EndgameStates.BACK_PISTON_UP;          
        }
        if(ticks <= Constants.ENDGAME_ENCODER_BACKWARDS_LIMIT){
          EndgameState = EndgameStates.STOP_MOTION;
        }
        break;

        case STOP_MOTION:
          if(stopMotionTimer.get() == 0){
            stopMotionTimer.start();
          }
          endGameOne.set(Constants.ENDGAME_STOP_SPEED);
          if(stopMotionTimer.get() >= 0.5){
            EndgameState = EndgameStates.CONTINUE_FOOT_AND_WHEELS;
            stopMotionTimer.stop();
            stopMotionTimer.reset();
          }
        break;

        case CONTINUE_FOOT_AND_WHEELS:
          /*
          if(stopEndgame >= Constants.ENDGAME_DEACTIVATION_TIME * 50){
            EndgameState = EndgameStates.STOP;
          }
          */
          endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_FOOT_DOWN);
          System.out.println("ticks " + ticks);
         //when our endgame reaches its destination, move to next step (stop endgame)
          if(ticks <= Constants.ENDGAME_ENCODER_STOP_FOOT_LIMIT){
            EndgameState = EndgameStates.STOP_FOOT;
            System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
          }
        break;
        
      case STOP_FOOT:
        if(endgametimer.get() == 0){
          endgametimer.start();
        }
        endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_STOP);
        if(endgametimer.get() >= Constants.ENDGAME_WHEELS_TIME_LIMIT){
          EndgameState = EndgameStates.STOP_WHEELS;
          endgametimer.stop();
          endgametimer.reset();
        }
        break;

      case STOP_WHEELS:
        Drive.runAt(Constants.ENDGAME_SPEED_LIMIT_STOP, Constants.ENDGAME_SPEED_LIMIT_STOP);
        EndgameState = EndgameStates.BACKUP_WHEELS;
        break;

      case BACKUP_WHEELS:
        if(backuptimer.get() == 0){
          backuptimer.start();
        }
        Drive.run(-Constants.ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS, Constants.ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS);
        if(backuptimer.get() >= Constants.ENDGAME_BACKUP_TIME_LIMIT){
          backuptimer.stop();
          backuptimer.reset();
          EndgameState = EndgameStates.STOP;
        }
        break;

      case STOP:
        Drive.runAt(Constants.ENDGAME_SPEED_LIMIT_STOP, Constants.ENDGAME_SPEED_LIMIT_STOP);
        endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_STOP);
        endgametimer.reset();
        backuptimer.reset();
        pistonTimer.reset();
        stopMotionTimer.reset();
        break;    
      }
    }


  public static void pauseAuto(){
      pistonTimer.stop();
      endgametimer.stop();
      backuptimer.stop();
      stopMotionTimer.stop();
      endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_STOP);
      Drive.runAt(Constants.ENDGAME_SPEED_LIMIT_STOP, Constants.ENDGAME_SPEED_LIMIT_STOP);

  }

  public static void stopWheels(){
    endGameOne.set(Constants.ENDGAME_STOP_SPEED);
    Drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);

  }

  // public static void encoderCheck(){
  //   if(endgamecounter.getRaw() <= previousTicks - 1.5 || endgamecounter.get() >= previousTicks + 1.5 ){
  //     stopEndgame++;
  //   }
  //   else{
  //     stopEndgame = 0;
  //   }
  //   previousTicks = endgamecounter.getRaw();
  // }

  public static void runManual(double leftYStickCubed) {
      // The lift's speed will be set at the right joystick's input value
      endGameOne.set(-leftYStickCubed);
      SmartDashboard.putNumber("EndgameEncoderPostion", endgamecounter.getRaw());
      SmartDashboard.putNumber("EndgameFootSpeed", Math.cbrt(leftYStickCubed));
      SmartDashboard.putNumber("EndgameLeftWheelSpeed", Drive.getLeftSpeed());
      SmartDashboard.putNumber("EndgameRightWheelSpeed", Drive.getRightSpeed());
      
    //Drive.runAt(0.2, 0.2);
  }

  public static void setEndgamePiston(boolean coDriverBack) {
    endGamePistonController.set(coDriverBack);
  }
  
  public static void putSmartDashboardEndgame(boolean endgameToggleAuto){
    SmartDashboard.putBoolean("Manual Endgame", endgameToggleAuto);
  }
}
