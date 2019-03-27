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
  
  
  private static final Timer endgametimer = new Timer();
  private static final Timer backuptimer = new Timer();
  private static final Timer pistonTimer = new Timer();
   

  private static enum EndgameStates{

    BACK_PISTION_DOWN,
    FOOT_AND_WHEELS,
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

    }

    public static void runAuto(double endgameLeftStick) {
      switch(EndgameState){
  
      case BACK_PISTION_DOWN:
        endGamePistonController.set(Constants.ENDGAME_PISTON_DOWN);
        pistonTimer.start();
        Utilities.compressorState(Constants.COMPRESSOR_OFF);
        if(endgameLeftStick >= Constants.ENDGAME_AUTO_DOWN_DEADBAND){
          endGamePistonController.set(Constants.ENDGAME_PISTON_UP);
          EndgameState = EndgameStates.BACK_PISTION_DOWN;
        }
        if(pistonTimer.get() >= Constants.ENDGAME_PISTON_TIME_LIMIT){
          EndgameState = EndgameStates.FOOT_AND_WHEELS;
          pistonTimer.stop();
        }
        break;
        
        case FOOT_AND_WHEELS:
          Drive.runAt(Constants.ENDGAME_SPEED_LIMIT_WHEEL_FORWARD, Constants.ENDGAME_SPEED_LIMIT_WHEEL_FORWARD);
          endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_FOOT_UP);
          ticks = endgamecounter.getRaw();
          if(endgamecounter.getRaw() >= Constants.ENDGAME_ENCODER_BACKWARDS_LIMIT){
            endGamePistonController.set(Constants.ENDGAME_PISTON_UP);
          }
          if(endgamecounter.getRaw() <= Constants.ENDGAME_ENCODER_BACKWARDS_LIMIT && endgameLeftStick >= Constants.ENDGAME_AUTO_DOWN_DEADBAND && ticks != ticks * 2){
            endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_FOOT_DOWN);
            if(endgamecounter.getRaw() <= Constants.ENDGAME_ENCODER_STOP_MOVING){
              endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_STOP);
              EndgameState = EndgameStates.BACK_PISTION_DOWN;
            }
          }
          if(endgamecounter.getRaw() >= Constants.ENDGAME_ENCODER_STOP_FOOT_LIMIT){
            EndgameState = EndgameStates.STOP_FOOT;
          }
          break;
          
        case STOP_FOOT:
          endgametimer.start();
          endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_STOP);
          if(endgametimer.get() >= Constants.ENDGAME_WHEELS_TIME_LIMIT){
            EndgameState = EndgameStates.STOP_WHEELS;
            endgametimer.stop();
          }
          break;
  
          case STOP_WHEELS:
            Drive.runAt(Constants.ENDGAME_SPEED_LIMIT_STOP, Constants.ENDGAME_SPEED_LIMIT_STOP);
            if(Drive.getLeftSpeed() == Constants.ENDGAME_SPEED_LIMIT_STOP && Drive.getRightSpeed() == Constants.ENDGAME_SPEED_LIMIT_STOP){
              EndgameState = EndgameStates.BACKUP_WHEELS;
            }
            break;
  
          case BACKUP_WHEELS:
            backuptimer.start();
            Drive.run(Constants.ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS, Constants.ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS);
            if(backuptimer.get() >= Constants.ENDGAME_BACKUP_TIME_LIMIT){
              backuptimer.stop();
              EndgameState = EndgameStates.STOP;
            }
            break;
  
          case STOP:
            Drive.runAt(Constants.ENDGAME_SPEED_LIMIT_STOP, Constants.ENDGAME_SPEED_LIMIT_STOP);
            break;    
        }
      }
  

    public static void pauseAuto(){
        pistonTimer.stop();
        endgametimer.stop();
        backuptimer.stop();
        endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_STOP);
        Drive.runAt(Constants.ENDGAME_SPEED_LIMIT_STOP, Constants.ENDGAME_SPEED_LIMIT_STOP);

    }

    public static void runManual(double leftYStickCubed) {
        // The lift's speed will be set at the right joystick's input value
        endGameOne.set(-leftYStickCubed);
        
      //Drive.runAt(0.2, 0.2);
    }

    public static void setEndgamePiston(boolean coDriverBack) {
      endGamePistonController.set(coDriverBack);
    }
    
    public static void putSmartDashboardEndgame(boolean endgameToggleAuto){
      SmartDashboard.putBoolean("Manual Endgame", endgameToggleAuto);
    }
}
