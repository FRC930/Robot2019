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



/**
 * Lifts the robot onto the third platform during the Endgame
 */
public class Endgame {

    // Lift motor controllers
  private static final VictorSPX  endgameLift = new VictorSPX(Constants.ENDGAME_ENDGAMELIFT);
  private static final VictorSPX endgameLiftFollow1 = new VictorSPX(Constants.ENDGAME_ENDGAMELIFTFOLLOW1);
  private static final VictorSPX endgameLiftFollow2 = new VictorSPX(Constants.ENDGAME_ENDGAMELIFTFOLLOW2);
  
  //wheels
  private static final CANSparkMax wheelOne = new CANSparkMax(1, MotorType.kBrushless);
  private static final CANSparkMax wheelTwo = new CANSparkMax(2, MotorType.kBrushless);
  private static final CANSparkMax wheelThree = new CANSparkMax(3, MotorType.kBrushless);
  private static final CANSparkMax wheelFour = new CANSparkMax(4, MotorType.kBrushless);
  private static final CANSparkMax wheelFive = new CANSparkMax(5, MotorType.kBrushless);
  private static final CANSparkMax wheelSix = new CANSparkMax(6, MotorType.kBrushless);

  //sets up a pdp
  private static final PowerDistributionPanel Power = new PowerDistributionPanel(1);
  
  //Sets up Volts Variable for later
  private static double Volt = 0.0;
  
  //sets up consents
  private static final double VoltageLimit = 30.0;
  private static final double WheelSpeed = 0.1;
  private static final double LiftSpeed = 1.0;
  
  
  
  
  //sets up  a timer
  private static Timer TimeCount = new Timer();
  //sets up a seconds variable
  public static double Seconds = 0.0;
  
  //sets up a varable for the encoder ticks
  public static double ticks = 0.0;
  
  //sets up a cubed stick value
  public static double leftYStickCubed;

    }

    public static void init() {

    }

    public static void run(double rightStick) {

    //   // Cubes the left y joystick
    // // -- for smoother motion 
    // leftYStickCubed = Math.pow(stick.getRawAxis(1), 3);

    // // checks to see if RB is pressed
    // if(stick.getRawButton(6)){

    //   // if the joystick cubed is above the dead band and ticks are not too high
    //   if(leftYStickCubed < -Constants.ENDGAME_JOYSTICK_DEADBAND && ticks < Constants.MAXTICKS){
        
    //     // sets ticks to the encoder position
    //     ticks = endgameLift.getSelectedSensorPosition(0);
        
    //     // sets the endgame motor to the value of the stick
    //     endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);

    //     // sets the wheels to rotate 20% positive
    //     wheelOne.set(0.2);
    //   }
      
    //   // if the cubed joystick value is above dead band and ticks is not too low
    //   else if(leftYStickCubed > Constants.ENDGAME_JOYSTICK_DEADBAND && ticks >= Constants.ENDGAME_MINTICKS){

    //     // set the endgame motro to the left stick
    //     endgameLift.set(ControlMode.PercentOutput, -leftYStickCubed);
        
    //     // sets ticks to the encoder position
    //     ticks = endgameLift.getSelectedSensorPosition(0);

    //     // sets wheels to rotate negtive 20%
    //     wheelOne.set(-0.2);
    //   }

    //   // if driver doesn't push a direction on stick then do this
    //   else{

    //     // set the motor to do nothing
    //     endgameLift.set(ControlMode.PercentOutput, 0);
        
    //     // sets wheels to do nothing
    //     wheelOne.set(0.0);
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
        endgameLift.set(ControlMode.PercentOutput, -rightStick);
        
    }
    
}
