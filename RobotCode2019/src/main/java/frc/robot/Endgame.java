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
import com.revrobotics.*;
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
  private static final CANSparkMax endGameTwo = new CANSparkMax(Constants.ENDGAME_SPARK2_ID, MotorType.kBrushless);
  

  //solenoid that controls the piston on the end game
  private static final Solenoid endGameRearPiston = new Solenoid(Constants.ENGGAME_SOLENOID_ID);

  //sets up a varable for the encoder ticks
  public static double ticks = 0.0;
  
  //sets up a cubed stick value
  public static double leftYStickCubed;
  
  //sets up timers for when we need to time movements
  private static final Timer stopFootTimer = new Timer();
  private static final Timer backuptimer = new Timer();
  private static final Timer pistonTimer = new Timer();
  private static final Timer stopMotionTimer = new Timer();

  //enum states for our end game process
  public static enum EndgameStates{

    BACK_PISTON_EXTENDED,
    BACK_PISTON_RETRACTED,
    START_FOOT_AND_WHEELS,
    PAUSE_FOOT,
    CONTINUE_FOOT_AND_WHEELS,
    STOP_FOOT,
    STOP_WHEELS,
    BACKUP_ROBOT,
    PAUSE_AUTO,
    STOP_ALL_MOVEMENT;
  }

  //creats variables to set and check states of our endgame
  private static EndgameStates EndgameState;
  private static EndgameStates previousEndgameState;
  static {

    // Mirror primary motor controller
    // endgameLiftFollow1.follow(endgameLift);
    // endgameLiftFollow2.follow(endgameLift);
    endGameTwo.follow(endGameOne); 

  }

  public static void init() {
    //makes it so we start out in our first state of the process
    EndgameState = EndgameStates.BACK_PISTON_EXTENDED;
    previousEndgameState = EndgameStates.BACK_PISTON_EXTENDED;
    //resets our encoder
    endgamecounter.reset();
    //resets all our timers for saftey
    pistonTimer.reset();
    backuptimer.reset();
    stopMotionTimer.reset();
    stopFootTimer.reset();

  }
  //method we use to run our automatic endgame process
  public static void runAuto() {
    SmartDashboard.putNumber("EndgameEncoderPostion", endgamecounter.getRaw());
    SmartDashboard.putNumber("Encoder Postion", endgamecounter.getRaw());
    System.out.println(EndgameState);
    
    //keeps track of our encoder ticks
    ticks = endgamecounter.getRaw();
    
    //sets our state to the previous state we were in
    EndgameState = previousEndgameState;
    
    //start of auto switch case
    switch(EndgameState) {
      
      //used to rectact the piston
      case BACK_PISTON_RETRACTED:
        previousEndgameState = EndgameStates.BACK_PISTON_RETRACTED;

        endGameRearPiston.set(Constants.ENDGAME_PISTON_RETRACTED);
      break;
      
      //this part only pushes the piston down
      case BACK_PISTON_EXTENDED: 
        previousEndgameState = EndgameStates.BACK_PISTON_EXTENDED;

        endGameRearPiston.set(Constants.ENDGAME_PISTON_EXTENDED);
        
        System.out.println(pistonTimer.get());
        
        //turns off the compressor
        Utilities.compressorState(Constants.COMPRESSOR_OFF);
        
        //when the timer gets to a certain point we move on to the next state and stop the timer
        if(pistonTimer.get() >= Constants.ENDGAME_PISTON_EXTENSION_DELAY){
          previousEndgameState = EndgameStates.START_FOOT_AND_WHEELS;
          EndgameState = EndgameStates.START_FOOT_AND_WHEELS;
          
          pistonTimer.stop();
          
          //resets timer so it is ready for reuse
          pistonTimer.reset();
        }
        
        break;
      
      //drives foot up and drivetrain wheels forward at the same time
      case START_FOOT_AND_WHEELS:
        
        //runs the wheels forward
        Drive.runAt(-Constants.ENDGAME_SPEED_LIMIT_WHEEL_FORWARD, Constants.ENDGAME_SPEED_LIMIT_WHEEL_FORWARD);
        
        //brings the foot down to start moving the robot up
        endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_FOOT_DOWN);
      /*
        if(stopEndgame >= Constants.ENDGAME_DEACTIVATION_TIME * 50){
          EndgameState = EndgameStates.STOP;
        }
      */
        //when we pass a certain point we bring the piston back up
        if(ticks <= Constants.ENDGAME_ENCODER_PISTON_UP){
          endGameRearPiston.set(Constants.ENDGAME_PISTON_RETRACTED);
        }

        //once we get to a certain point we move on to the next state
        if(ticks <= Constants.ENDGAME_ENCODER_POINT_NO_RETURN){
          
          //sets the timer verable false so it will start the next timer
          TeleopHandler.setStartTimerFalse();
          previousEndgameState = EndgameStates.PAUSE_FOOT;
          EndgameState = EndgameStates.PAUSE_FOOT;
          
        }
        break;

        //pauses just the foot but lets wheels run
        case PAUSE_FOOT:
          
          
          //stops our foot
          endGameOne.set(Constants.ENDGAME_STOP_SPEED);
          
          //once we get to a certain time we move on to the next state
          if(stopMotionTimer.get() >= 0.5){
            previousEndgameState = EndgameStates.CONTINUE_FOOT_AND_WHEELS;
            EndgameState = EndgameStates.CONTINUE_FOOT_AND_WHEELS;
            
            stopMotionTimer.stop();
            
            //resets timer for reuse
            stopMotionTimer.reset();
          }
        break;
        
        //starts moving the foot yet again
        case CONTINUE_FOOT_AND_WHEELS:
          
          /*
          if(stopEndgame >= Constants.ENDGAME_DEACTIVATION_TIME * 50){
            EndgameState = EndgameStates.STOP;
          }
          */

          //sets the foot to continue going down
          endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_FOOT_DOWN);
          
          System.out.println("ticks " + ticks);
         
          //when our endgame reaches its destination, move to next step (stop foot)
         if(ticks <= Constants.ENDGAME_ENCODER_STOP_FOOT_LIMIT){
            
            //sets our timer varibale to false so we can start our timer
            TeleopHandler.setStartTimerFalse();  
            previousEndgameState = EndgameStates.STOP_FOOT;
            EndgameState = EndgameStates.STOP_FOOT;
            
            System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
          }
        break;
      
        //stop the foot while still running wheels
      case STOP_FOOT:
        
        //stops our foot
        endGameOne.set(Constants.ENDGAME_STOP_SPEED);
        
        //once we get to the right time we move on to the next step
        if(stopFootTimer.get() >= Constants.ENDGAME_WHEELS_TIME_LIMIT){
          previousEndgameState = EndgameStates.STOP_WHEELS;
          EndgameState = EndgameStates.STOP_WHEELS;
          
          stopFootTimer.stop();

          //resets the timer for reuse
          stopFootTimer.reset();
        }
        break;
      
      //stops the  wheels
      case STOP_WHEELS:
        
        //stops the drive train to stop
        Drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);
        //resets our time varible to start a timer
        TeleopHandler.setStartTimerFalse();
        previousEndgameState = EndgameStates.BACKUP_ROBOT;
        EndgameState = EndgameStates.BACKUP_ROBOT;
        break;
      
      //backs up the robot a bit to make sure the foot is not touching
      case BACKUP_ROBOT:
        

        //backs up our robot slightly
        Drive.run(-Constants.ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS, Constants.ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS);
        
        //when we get to the right time we move on
        if(backuptimer.get() >= Constants.ENDGAME_BACKUP_TIME_LIMIT){
          backuptimer.stop();
          //resets timer for reuse
          backuptimer.reset();
          previousEndgameState = EndgameStates.STOP_ALL_MOVEMENT;
          EndgameState = EndgameStates.STOP_ALL_MOVEMENT;
        }
        break;
      
      //Stops everything and resets timers
      case STOP_ALL_MOVEMENT:
        

        Drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);
        
        endGameOne.set(Constants.ENDGAME_STOP_SPEED);
        
        stopFootTimer.reset();
        
        backuptimer.reset();
        
        pistonTimer.reset();
        
        stopMotionTimer.reset();
        break;    
      
      //Used to pause the endgame
      case PAUSE_AUTO:
        pistonTimer.stop();
        
        stopFootTimer.stop();
        
        backuptimer.stop();
        
        stopMotionTimer.stop();
        
        endGameOne.set(Constants.ENDGAME_STOP_SPEED);
        
        Drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);
      break;
      }
    }


  public static void stopWheels(){
    endGameOne.set(Constants.ENDGAME_STOP_SPEED);
    Drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);

  }
      //used to check if the encoder values we get back are good values or bad
  // public static void encoderCheck(){
  //   if(endgamecounter.getRaw() <= previousTicks - 1.5 || endgamecounter.get() >= previousTicks + 1.5 ){
  //     stopEndgame++;
  //   }
  //   else{
  //     stopEndgame = 0;
  //   }
  //   previousTicks = endgamecounter.getRaw();
  // }

  //used to run the endgame manually 
  public static void runManual(double leftYStickCubed) {
      // The lift's speed will be set at the right joystick's input value
      endGameOne.set(-leftYStickCubed);
      SmartDashboard.putNumber("EndgameEncoderPostion", endgamecounter.getRaw());
      SmartDashboard.putNumber("EndgameFootSpeed", Math.cbrt(leftYStickCubed));
      SmartDashboard.putNumber("EndgameLeftWheelSpeed", Drive.getLeftSpeed());
      SmartDashboard.putNumber("EndgameRightWheelSpeed", Drive.getRightSpeed());
      //retracts piston
      setEndgamePiston(Constants.ENDGAME_PISTON_RETRACTED);
      //once we get low enough we reset our endgame state
      if(endgamecounter.getRaw() <= Constants.ENDGAME_ENCODER_STOP_MOVING){
        previousEndgameState = EndgameStates.BACK_PISTON_EXTENDED;
      }
  }

  public static void setEndgamePiston(boolean coDriverBack) {
    endGameRearPiston.set(coDriverBack);
  }
  
  public static void putSmartDashboardEndgame(boolean endgameToggleAuto){
    SmartDashboard.putBoolean("Manual Endgame", endgameToggleAuto);
  }
  //returns our current state
  public static EndgameStates getEndgameState(){
    return EndgameState;
  }
  //sends our endgame to pause our code
  public static void endgameSendPauseAuto(){
    EndgameState = EndgameStates.PAUSE_AUTO;
  }
  //used to start our first timer
  public static void endgameStartPistonTimer(){
    pistonTimer.start();
  }
  //used to start our next timer
  public static void endgameStartPauseFootTimer(){
    stopMotionTimer.start();
  }
  //used to start our next timer
  public static void endgameStartStopFootTimer(){
    stopFootTimer.start();
  }
  //used to start our next timer
  public static void endgameStartBackUpRobotTimer(){
    backuptimer.start();
  }
  //used to set our endgame state to the previous 
  public static void endgameSetState(){
    EndgameState = previousEndgameState;
  }
}
