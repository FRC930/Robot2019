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
import java.sql.Time;
import edu.wpi.first.wpilibj.*;
import com.revrobotics.*;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.Joystick;

/**
 * Lifts the robot onto the third platform during the Endgame
 */
public class Endgame {

    // Endgame Motor Controllers
    private static final TalonSRX endgameLift = new TalonSRX(1);
    private static final VictorSPX endgameLiftFollow1 = new VictorSPX(2);
    private static final VictorSPX endgameLiftFollow2 = new VictorSPX(3);
    //Wheels
    private static final CANSparkMax WheelOne = new CANSparkMax(1, MotorType.kBrushless);
    private static final CANSparkMax WheelTwo = new CANSparkMax(2, MotorType.kBrushless);
    private static final CANSparkMax WheelThree = new CANSparkMax(3, MotorType.kBrushless);
    private static final CANSparkMax WheelFour = new CANSparkMax(4, MotorType.kBrushless);
    private static final CANSparkMax WheelFive = new CANSparkMax(5, MotorType.kBrushless);
    private static final CANSparkMax WheelSix = new CANSparkMax(6, MotorType.kBrushless);
    
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
    //Joystick on CoDriver
    Joystick stick = new Joystick(1);
    static {

        // Mirror primary motor controller
        endgameLiftFollow1.follow(endgameLift);
        endgameLiftFollow2.follow(endgameLift);
        endgameLiftFollow1.follow(endgameLift);
        endgameLiftFollow2.follow(endgameLift);
        WheelTwo.follow(WheelOne);
        WheelThree.follow(WheelOne);
        WheelFour.follow(WheelOne);
        WheelFive.follow(WheelOne);
        WheelSix.follow(WheelOne);
        Volt = Power.getVoltage(); 
        TimeCount.reset();

    }

    public static void init() {

    }

    public static void run(double stickY) {
         
        /*
    When the A button is pressed (which is 1) and voltage is less then or equal to 30 
    then set motors to run Otherwise stop running
    */
    /*
    if(stick.getRawButton(1) == true && Volt <= VoltageLimit){
      
        //start the timer
        TimeCount.start();
        
        //set the  time to seconds
        Seconds = TimeCount.get();
        
        //setting the  lift motor to LiftSpeed
        endgameLift.set(ControlMode.PercentOutput, LiftSpeed); 
        
        //when the timer is  greater than or equal to 2 then activate wheels
        if(TimeCount.get() >= 2.0){
          WheelOne.set(WheelSpeed);
        }
      }
      
      //when button two is pressed(B) and voltage is bigger than 20 
      else if(stick.getRawButton(2) == true && Volt <= VoltageLimit){
        
        //start the  timer agian
        TimeCount.start();
        
        //if the seconds is greater then seconds times 2
        if(TimeCount.get() < Seconds*2){
          
          //sets lift speed to the oppisite of liftspeed
          endgameLift.set(ControlMode.PercentOutput, -LiftSpeed);
          
          //sets wheels to 0
          WheelOne.set(0); 
        }
        
        // when the time is greater then seconds *2 then stop and reset timer
        else{
          TimeCount.stop();
          TimeCount.reset();
        }
      }
      
      //if the if and else if is not true then stop time and set motor to 0
      else{
        endgameLift.set(ControlMode.PercentOutput, 0);
        WheelOne.set(0);
        TimeCount.stop();
      }
      */
        // Move end game lift up when right joystick is pushed up
        if (Math.abs(stickY) >= Constants.ENDGAME_JOYSTICK_DEADBAND) {

            // The lift's speed will be set at the right joystick's input value
            endgameLift.set(ControlMode.PercentOutput, -stickY);

        }

        else { 
            
            // If the joystick isn't being touched, don't move
            endgameLift.set(ControlMode.PercentOutput, 0.0);
        }
        
    }
    
}
