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



/**
 * Lifts the robot onto the third platform during the Endgame
 */
public class Endgame {

  // Endgame Motor Controllers
  // private static final TalonSRX endgameLift = new TalonSRX(Constants.ENDGAME_ENDGAMELIFT_ID);
  // private static final VictorSPX endgameLiftFollow1 = new VictorSPX(Constants.ENDGAME_ENDGAMELIFTFOLLOW1_ID);
  // private static final VictorSPX endgameLiftFollow2 = new VictorSPX(Constants.ENDGAME_ENDGAMELIFTFOLLOW2_ID);

  private static final CANSparkMax endGameOne = new CANSparkMax(Constants.ENDGAME_SPARK1_ID, MotorType.kBrushless);
  private static final CANSparkMax endGameTwo = new CANSparkMax(Constants.ENDGAME_SPARK1_ID, MotorType.kBrushless);
  private static final CANSparkMax endGameThree = new CANSparkMax(Constants.ENDGAME_SPARK1_ID, MotorType.kBrushless);
  private static final Encoder endGameEncoder = new Encoder(0, 1);
  //Sets up Volts Variable for later
  private static double Volt = 0.0;

  //sets up a varable for the encoder ticks
  public static double ticks = 0.0;
  
  //sets up a cubed stick value
  public static double leftYStickCubed;
    
    static {

      // Mirror primary motor controller
      // endgameLiftFollow1.follow(endgameLift);
      // endgameLiftFollow2.follow(endgameLift);

      endGameTwo.follow(endGameOne);
      endGameThree.follow(endGameOne);

      //Volt = Utilities.Power.getVoltage(); 

    }

    public static void init() {

    }

    public static void run(double leftYStickCubed) {

    //   // if the joystick cubed is above the dead band and ticks are not too high
    //   if(leftYStickCubed < 0 && ticks < Constants.MAXTICKS){
        
    //     // sets ticks to the encoder position
    //     ticks = encoder.getRaw();
        
    //     // sets the endgame motor to the value of the stick
    //     endGameOne.set(-leftYStickCubed);

    //     // sets the wheels to rotate 20% positive
    //     Drive.runAt(0.2,0.2);
    //   }
      
    //   // if the cubed joystick value is above dead band and ticks is not too low
    //   else if(leftYStickCubed > 0 && ticks >= Constants.ENDGAME_MINTICKS){

    //     // set the endgame motro to the left stick
    //     endGameOne.set(-leftYStickCubed);
        
    //     // sets ticks to the encoder position
    //     ticks = endgameLift.getSelectedSensorPosition(0);

    //     // sets wheels to rotate negtive 20%
    //     Drive.runAt(-0.2,-0.2);
    //   }

    //   // if driver doesn't push a direction on stick then do this
    //   else{

    //     // set the motor to do nothing
    //     endGameOne.set(0);
        
    //     // sets wheels to do nothing
    //     Drive.runAt(0.0, 0.0);
    //   }
         
        /*
        When the Y button is pressed (which is 4) and voltage is less then or equal to 30 
        then set motors to run Otherwise stop running
        */
        /*
        if(stick.getRawButton(4) == true && Volt <= VoltageLimit){
      
        //start the timer
        TimeCount.start();
        
        //set the  time to seconds
        Seconds = TimeCount.get();
        
        //setting the  lift motor to LiftSpeed
        endgameLift.set(ControlMode.PercentOutput, LiftSpeed); 
        
        //when the timer is  greater than or equal to 2 then activate wheels
        if(TimeCount.get() >= 2.0) {
          Drive.runat(Constants.ENDGAME_WHEELSPEED,Constants.ENDGAME_WHEELSPEED);
        }

      }
      
        //when button two is pressed(B) and voltage is less than or equal too 30 
        else if(stick.getRawButton(2) == true && Volt <= Constants.ENDGAME_VOLTAGELIMIT) {
        
        //start the  timer agian
        TimeCount.start();
        
        //if the seconds is greater then seconds times 2
        if(TimeCount.get() < Seconds*2) {
          //sets lift speed to the oppisite of liftspeed
          endgameLift.set(ControlMode.PercentOutput, -LiftSpeed);
          
          //sets wheels to 0
          Drive.runat(0,0); 
        }
        
        // when the time is greater then seconds *2 then stop and reset timer
        else {
          TimeCount.stop();
          TimeCount.reset();
        }

      }
      
        //if the if and else if is not true then stop time and set motor to 0
        else {
          endgameLift.set(ControlMode.PercentOutput, 0);
          Drive.runat(0,0);
          TimeCount.stop();
        }
        */
        // Move end game lift up when right joystick is pushed up
        

        // The lift's speed will be set at the right joystick's input value
        endGameOne.set(-leftYStickCubed);
        Drive.runAt(0.2, 0.2);
    }
    
}
