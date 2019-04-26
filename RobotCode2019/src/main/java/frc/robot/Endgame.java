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
import frc.robot.CargoIntake.CargoPositionEnums;


//A flag is a boolean we change when we get to some point
//The foot is the lift on the bottom of the robot. It is a big four bar.
/** Goal/Process
  Automatically Lifts the robot onto the third platform during the Endgame. It takes 6 seconds.
  If we are in automatic mode
    This process is done by driver holding lb and up on the left Y joystick
      -- Our proccess for this is we start by lifting our back piston to give some room to the foot 
         And so it does not have to lift the robot by itself.
      -- Next we start moving the foot and driving the wheels, we do this to keep our self lined up.  
         We move the foot to move the robot up. 
         We also retract the  back piston.
      -- Next we stop moving the foot, so we can keep moving our wheels on the platfrom. 
      -- Then we continue moving the foot and wheels, so that the foot will now be all the  way up and 
         we will be on the platform securely.
      -- Next we stop moving the foot so it does not go too far.
      -- Then we stop the wheels because we should be up far enough.
      -- We then back up the robot so the foot is not touching the hab and we are not too close to player station.
      -- Then we stop all movement. We are done, and do not want to continue.
    If lb is still held but the left Y joystick is not up or down then
      -- We also have a pause state so we purposfully can stop moving at any time and continue when we go back up on left Y joystick.
    If lb is held and the left Yjoystick is down
      -- We go manually down in automatic mode
  If we are in manual mode and holding lb
  -- We send the endgame the left Y joystick value cubed for up and down
     We also reset our automatic state to our start state
     Driver also runs wheels on the right Y stick

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
    private static double ticks = 0.0;
    
    //used for our encoder check
    private static double previousTicks = 0.0;

    private static boolean previouslyPaused = false;

    private static boolean rearPistonState = false;

    private static  int stopEndgame = 0;
    
    //sets up timers for when we need to time movements
    private static final Timer EndgameTimer = new Timer();
    
    //enum states to keep track of our state for our end game process
    public static enum EndgameStates{
      
      START_TIMER_FIRST_TIME,
      BACK_PISTON_EXTENDED,
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
    EndgameState = EndgameStates.START_TIMER_FIRST_TIME;
    previousEndgameState = EndgameStates.START_TIMER_FIRST_TIME;
    //resets our encoder
    endgamecounter.reset();
    //resets our timers for saftey
    EndgameTimer.reset();
    //SmartDashboard.putString("Endgame state", EndgameState.toString());
    stopEndgame = 0;

  }
  //method we use to run our automatic endgame process
  public static void runAuto() {
    //Some outputs
    SmartDashboard.putNumber("EndgameEncoderPostion", endgamecounter.getRaw());
    //SmartDashboard.putString("Endgame state", EndgameState.toString());
    //encoderCheck();
    
    //keeps track of our encoder ticks
    ticks = endgamecounter.getRaw();
    
    //Checks to see if we have not been paused  
    if(!previouslyPaused){
      //If we have not then we set endgame state to our last known endgame state
      EndgameState = previousEndgameState;
    }
    //start of auto switch case
    switch(EndgameState) {
      //this is our first state it starts a timer for our next state and sets our next state
      case START_TIMER_FIRST_TIME:
        changeEndgameState(EndgameStates.BACK_PISTON_EXTENDED, true);
        break;
      
      //this part pushes the piston down
      case BACK_PISTON_EXTENDED:

        endGameRearPiston.set(Constants.ENDGAME_PISTON_EXTENDED);
        
        //System.out.println(EndgameTimer.get());
        
        //turns off the compressor
        Utilities.setCompressorState(Constants.COMPRESSOR_OFF);
        
        //when the timer gets to a certain point we move on to the next state and stop the timer
        if(EndgameTimer.get() >= Constants.ENDGAME_PISTON_EXTENSION_DELAY){
          CargoIntake.run(CargoPositionEnums.cargoCarrying);
          //changes to the next state and does not start a timer
          changeEndgameState(EndgameStates.START_FOOT_AND_WHEELS, false);
          
          //Stops the  timer
          EndgameTimer.stop();
        }
        break;
      
      //drives foot down and drivetrain wheels forward at the same time
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
          CargoIntake.run(CargoPositionEnums.cargoStop);
          endGameRearPiston.set(Constants.ENDGAME_PISTON_RETRACTED);
        }

        //once we get to a certain point we move on to the next state
        if(ticks <= Constants.ENDGAME_ENCODER_POINT_NO_RETURN){

          
          // changes to teh next state and starts a timer
          changeEndgameState(EndgameStates.PAUSE_FOOT, true);
          
        }
        break;

        //pauses just the foot and lets wheels run
        case PAUSE_FOOT:
          //stops our foot
          endGameOne.set(Constants.ENDGAME_STOP_SPEED);
          
          //once we get to a certain time we move on to the next state
          if(EndgameTimer.get() >= Constants.ENDGAME_STOPMOTION_TIME_DELAY){

            
            //Changes our state and does not set a new timer
            changeEndgameState(EndgameStates.CONTINUE_FOOT_AND_WHEELS, false);
            
            //Stops the timer
            EndgameTimer.stop();
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
          endGameOne.set(Constants.ENDGAME_SPEED_LIMIT_FOOT_DOWN_2);
          
          //System.out.println("ticks " + ticks);
         
          //when our endgame reaches its destination, move to next step (stop foot)
         if(ticks <= Constants.ENDGAME_ENCODER_STOP_FOOT_LIMIT){
            
            //sets our timer varibale to false so we can start our timer 
            changeEndgameState(EndgameStates.STOP_FOOT, true);
            
            //System.out.println("\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\");
          }
          break;
      
        //stop the foot while still running wheels
      case STOP_FOOT:
        //stops our foot
        endGameOne.set(Constants.ENDGAME_STOP_SPEED);
        
        //once we get to the right time we move on to the next step
        if(EndgameTimer.get() >= Constants.ENDGAME_WHEELS_TIME_LIMIT){
          //Changes our state and does not set a timer
          changeEndgameState(EndgameStates.STOP_WHEELS, false);
          
          EndgameTimer.stop();
        }
        break;
      
      //stops the  wheels
      case STOP_WHEELS:
        
        //stops the drive train
        Drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);
        //Changes our state and resets and starts a timer
        changeEndgameState(EndgameStates.BACKUP_ROBOT, true);
        break;
      
      //backs up the robot a bit to make sure the foot is not touching the hab
      case BACKUP_ROBOT:
        

        //backs up our robot slightly
        Drive.run(-Constants.ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS, Constants.ENDGAME_SPEED_LIMIT_WHEEL_BACKWARDS);
        
        //when we get to the right time we move on
        if(EndgameTimer.get() >= Constants.ENDGAME_BACKUP_TIME_LIMIT){
          EndgameTimer.stop();
          //Change the state and does not start a timer
          changeEndgameState(EndgameStates.STOP_ALL_MOVEMENT, false);
        }
          break;
      
      //Stops everything and resets timers
      case STOP_ALL_MOVEMENT:
        stopWheels();
        break;    
      
      //Used to pause the endgame
      case PAUSE_AUTO:
        stopWheels();
        break;
      }
    }

  //Stops our drive train and foot
  public static void stopWheels(){
    endGameOne.set(Constants.ENDGAME_STOP_SPEED);
    Drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);

  }
      //used to check if the encoder values we get back are good values or bad
  public static boolean encoderCheck(){
    
    //if our encoder is giving bad values increase counter
    if(EndgameState == EndgameStates.START_FOOT_AND_WHEELS || EndgameState == EndgameStates.CONTINUE_FOOT_AND_WHEELS)
      if(endgamecounter.getRaw() <= previousTicks - 1.5 || endgamecounter.getRaw() >= previousTicks + 1.5 || endgamecounter.getRaw() == previousTicks ){
        stopEndgame++;
      }
      else{
        stopEndgame = 0;
      }
      previousTicks = endgamecounter.getRaw();
      
    // if our counter is above a certain amount then return true
    if(stopEndgame >= Constants.ENDGAME_DEACTIVATION_TIME * 50){
      return true;
    }
    //return false because our encoder values are good
    else{
      return false;
    }
  }

  //used to run the endgame manually 
  public static void runManual(double leftYStickCubed) {
      //sets the speed to the left joystick cubed
      endGameOne.set(-leftYStickCubed);
      //some print statements
      // SmartDashboard.putNumber("EndgameEncoderPostion", endgamecounter.getRaw());
      // SmartDashboard.putNumber("EndgameFootSpeed", Math.cbrt(leftYStickCubed));
      // SmartDashboard.putNumber("EndgameLeftWheelSpeed", Drive.getLeftSpeed());
      // SmartDashboard.putNumber("EndgameRightWheelSpeed", Drive.getRightSpeed());
      //retracts piston
      if(getEndgamePiston()){
        setEndgamePiston(Constants.ENDGAME_PISTON_RETRACTED);
      }
      //once we get low enough we reset our endgame state
      if(endgamecounter.getRaw() <= Constants.ENDGAME_ENCODER_STOP_MOVING){
        //changes our state and does not start a timer
        changeEndgameState(EndgameStates.START_TIMER_FIRST_TIME, false); 
      }
  }
  //Used to change the state of our end game and reset the timer and start the timer
  private static void changeEndgameState(EndgameStates stateValue, boolean startTimer){
    //System.out.println("Changing state");
    //changes the endgame state
    EndgameState = stateValue;
    //Changes our previouse state so we can go back. Sets to everything except for pause
    if(stateValue != EndgameStates.PAUSE_AUTO){
      previousEndgameState = stateValue;
      //if we need to start a timer we can
      if(startTimer){
        //System.out.println("starting timer");
        EndgameTimer.reset();
        EndgameTimer.start();
      }
    }
  }

  //Used to unpause the endgame if it has been
  public static void unpauseEndgame() {
    if(previouslyPaused){
      //System.out.println("Unpausing endgame");
      //flag is set to pause
      previouslyPaused = false;
      //starts timer
      EndgameTimer.start();
    }
  }
  public static void setEndgamePiston(boolean coDriverBack) {
    endGameRearPiston.set(coDriverBack);
    rearPistonState = coDriverBack;
  }

  public static boolean getEndgamePiston(){
    return rearPistonState;
  }

  public static void putSmartDashboardEndgame(boolean endgameToggleAuto){
    SmartDashboard.putBoolean("Manual Endgame", endgameToggleAuto);
  }
  //sends our endgame to pause our code
  public static void pauseEndgame(){
    //System.out.println("Sending pause to Endgame");
    //Changes the state to pause and does not start timers
    changeEndgameState(EndgameStates.PAUSE_AUTO, false);
    //Changes flag to true
    previouslyPaused = true;
    //Stops the timer
    EndgameTimer.stop();
  }
}
