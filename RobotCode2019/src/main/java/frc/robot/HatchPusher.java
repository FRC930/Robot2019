/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

/*----------------------------------------------------------------------------*/
/*                  Travis --- TIMER OBJECT IS NOT IN USE!                    */
/*                Travis --- HATCHPUSHER CLASS IS NOT IN USE!                 */
/*----------------------------------------------------------------------------*/

package frc.robot;

import javax.sound.midi.SysexMessage;
import edu.wpi.first.wpilibj.Solenoid;
// import edu.wpi.first.wpilibj.Timer;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Code for the hatch pusher which sets the hatch pusher out or in based on the pressing of button LB.
 */
public class HatchPusher {

    //Class Constants
    private final int HATCH_PUSHER_PISTON_PORT = 2;

    //Class Variables
    private Solenoid hatchPusherPiston; //Declaring the Cargo Intake solenoid.
    private Timer pistonRetractTimer; //Times how long piston is out
    private boolean hatchPusherToggle; //Toggles the extended or retracted piston
    private static HatchPusher instance = null; //The one HatchPusher object used by other classes

    //Default Constructor
    private HatchPusher(){

        hatchPusherPiston = new Solenoid(HATCH_PUSHER_PISTON_PORT);
        pistonRetractTimer = new Timer();
        hatchPusherToggle = false;
    }

    //Creates one instance of the Elevator object
    public static HatchPusher getInstance(){

        //tests for an existence of an Elevator object
    if (instance == null){
        instance = new HatchPusher();
        return instance;
      }
      //if the Elevator object already exists, then return that Elevator object
      else {
        return instance;
      }
    }

    //Sets values to the HatchPusher motors and tells them what to do
    public void setHatchPusherMotorControllers(Solenoid hpp){
        hatchPusherPiston = hpp;
    }

    //Extends the HatchPusher arms and starts a timer for the arms to retract
    public void extendPusher(long t){
        hatchPusherToggle = true;
        hatchPusherPiston.set(hatchPusherToggle);
        OurTimerTask task = new OurTimerTask();
        pistonRetractTimer.schedule(task, t);
    }

    //Retracts the HatchPusher arms
    public void retractPusher(){
        hatchPusherToggle = false;
        hatchPusherPiston.set(hatchPusherToggle);
    }

    //Class that schedules the retractPusher to occur
    private class OurTimerTask extends TimerTask {
        @Override
        public void run(){
            HatchPusher piston = HatchPusher.getInstance();
            piston.retractPusher();
        }
    }

}
