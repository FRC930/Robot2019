/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

//-------- Imports --------\\

package frc.robot;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * Add your docs here.
 */
public class HatchIntake {

    private static final DoubleSolenoid hatchPiston = new DoubleSolenoid(Constants.HATCH_SOLENOID_PORT,7);

    //used later in code to see if left button is pressed
    private static boolean pressedL = false;

    //used later in code to see if right button is pressed
    private static boolean pressedR = false;

    //sets up the button sensor for right
    private static DigitalInput bumperR = new DigitalInput(0);

    //sets up the button sensor for left
    private static DigitalInput bumperL = new DigitalInput(1);
    static {

        //Sets the solenoid to a default start position. 
        //This will be retracted (value is false)
        //hatchPiston.set(Constants.HATCH_SOLENOID_START);
        hatchPiston.set(Value.kForward);
    }

    public static void init() {

    }

    public static void run(boolean driverInput) {
       
        //sets up the booleans to see if the buttons are pressed
        pressedL = bumperL.get();
        pressedR = bumperR.get();

        SmartDashboard.putBoolean( "Left Bumper", pressedL);
        SmartDashboard.putBoolean("Right Bumper", pressedR);


        /*  
            coDriverInput will be a true or false value of the CoDriver's left trigger button.
            It is true when the left trigger button is down, and false if it is up.

            hatchPiston.set sets the beak piston's state. If it is true, the beak piston will be pushed out. If it is false, 
            the beak piston will be retracted. When the beak piston is pushed out, it will grab the hatch.

            LTPressed is a boolean used to make the system togglable. When you press LT once, the beak will close (piston true).
            When LT is pressed again, the beak will open (piston false, grabbed hatch).

            When the coDriver inputs and LTPressed is false, LT will be set true and the beak piston state will be flipped.
            Ex: The beak state will start false. When the driver inputs and LTPressed is false, LTPressed is set to true
            and the beak state is set to true (closed beak). Because LTPressed is set true, that if statement will not run,
            effectively making that block of code run once. LTPressed is set to false when the coDriver stops inputting. 

        */
            //tests the  drivers input
            //hatchPiston.set(driverInput);
            setHatchPiston(true);
        
    
        //if both buttons are pressed and it is closed it will open
        if((pressedL || pressedR) && driverInput == false){

            setHatchPiston(!driverInput);
            
      
          }
        
        //if both buttons are pressed and it is open it will close
        else if ((pressedL || pressedR) && driverInput){
      
            setHatchPiston(!driverInput);
           
      
          }
            
    } 
    public static void setHatchPiston(boolean state){
        
        if(state){
            hatchPiston.set(Value.kForward);
        }
        else{
            hatchPiston.set(Value.kReverse);
        }

    }

    public static boolean getHatchPistonStatus(){

        if(hatchPiston.get() == Value.kForward){
            return true;
        }
        else{
            return false;
        }

    }

}





//hi sam