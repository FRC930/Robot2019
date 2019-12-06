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
import edu.wpi.first.wpilibj.Timer;

/**
 * Code for the hatch pusher which sets the hatch pusher out or in based on the pressing of button LB.
 */
public class HatchPusher {

    //Class Constants
    private final int HATCH_PUSHER_PISTON_PORT = 2;

    //Class Variables
    private Solenoid hatchPusherPiston; //Declaring the Cargo Intake solenoid.
    private Timer pistonRetractTimer; 
    private boolean hatchPusherToggle;
    private static HatchPusher instance = null;

    //Default Constructor
    private HatchPusher(){

        hatchPusherPiston = null;
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

    //Sets values to the HatchPusher motors for the real robot
    public void setHatchPusherMotorControllers(){

        setHatchPusherMotorControllers(new Solenoid(HATCH_PUSHER_PISTON_PORT));
    }

    //Sets values to the HatchPusher motors and tells them what to do
    public void setHatchPusherMotorControllers(Solenoid hpp){

        hatchPusherPiston = hpp;
        init();
    }

    public void init() {

       

    }

    public void run() {


    }

    public void setHatchPusherToggleState(boolean state) {
        //System.out.println("setting hatch pusher pistons" + state);
        hatchPusherPiston.set(state);
    }

}
