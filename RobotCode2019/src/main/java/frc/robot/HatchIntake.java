/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
 
//-------- Imports --------\\
 
package frc.robot;
 
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
 
/**
* Add your docs here.
*/
 
public class HatchIntake {

   private final DoubleSolenoid hatchPiston;

   // static flag variable
    private static HatchIntake lastInstance = null;

   // = new DoubleSolenoid(Constants.HATCH_SOLENOID_PORT, 7)
   private final Timer hatchPusherTimer;
 
   // Used later in code to see if left button is pressed
   private  boolean pressedL;
 
   // Used later in code to see if right button is pressed
   private  boolean pressedR;
 
   // Beak toggle
   public  boolean beakToggle;
   private  boolean beakStatus;
 
   // Auto hatch
   private  boolean autoHatch;
   private  int autoHatchCounter;
  
   // construction of the variables
   private HatchIntake( ){
       hatchPiston = new DoubleSolenoid(0,7); // 0 was originially constants.hatch_Solenoid_PORT
       
        hatchPusherTimer = new Timer();

        pressedL = false;
        pressedR = false;
        beakToggle = false;
        beakStatus = true;
        autoHatch = false;
        autoHatchCounter = 0;

       hatchPiston.set(Value.kForward);
       hatchPusherTimer.reset();
       this.putSmartDashboardHatch(beakStatus);
   }

   //something that allows other files to grab instance
   public static HatchIntake getInstance(){
       if(lastInstance == null){
           lastInstance = new HatchIntake();
           return lastInstance;
       }
       else{
           return lastInstance;
       }

   }
 
   //Responsible for controlling the beak opening and closing and the hatch piston pushing and retracting.
   public void run(boolean isDriverButtonPressed, boolean isCoDriverButtonPressed) {
      
       if (!isDriverButtonPressed) {
           VisionTracking.getInstance().setAutoHatchGrabbed(false);
           TeleopHandler.getInstance().setRumble(Constants.DRIVER_CONTROLLER_ID, Constants.RUMBLE_STOP);
          
       }
      
       /*
           The below code controls the toggle for the beak.
 
           The beak will not go down when the cargo is down.
 
       */
 
        // Button toggle for the beak
        if ((isDriverButtonPressed) /* || pressedL || pressedR)*/ && beakToggle == false && !isCoDriverButtonPressed) {
           beakToggle = true;
       }
       else if ((!isDriverButtonPressed/* || pressedL || pressedR)*/ && beakToggle)) {
           beakToggle = false;
           if (!autoHatch) {
               beakStatus = !beakStatus;
               if(beakStatus == Constants.HATCH_STATE_CLOSED)
               {
 
                   hatchPusherTimer.start();
                   HatchPusher.getInstance().setHatchPusherToggleState(true);
               }
               //sets the beak to the beakstatus ;
               setHatchPiston(beakStatus);
           }
       }
 
       //stops and resets the hatchPusherTimer if its >= HATCH_PUSHER_PUSH_TIME
       if(hatchPusherTimer.get() >= Constants.HATCH_PUSHER_PUSH_TIME)
       {
           HatchPusher.setHatchPusherToggleState(false);
           //System.out.println("Timer reached");
           hatchPusherTimer.stop();
           hatchPusherTimer.reset();
          
       }
      
      
 
       // If LT is held for at least 1 sec, flag for using auto hatch pickup is set to true
       if (isDriverButtonPressed && beakToggle) {
           autoHatchCounter++;
           if (autoHatchCounter >= Constants.HATCH_LT_HOLD_THRESHOLD) {
               if (!VisionTracking.getInstance().getAutoHatchGrabbed()) {
                   setHatchPiston(Constants.HATCH_STATE_CLOSED);
               }
               autoHatch = true;
           }
       }
       else {
           // LT is released, set auto hatch variables back to default values
           autoHatch = false;
           autoHatchCounter = 0;
       }
   }
 
   // Returns status of auto hatch pickup toggle
   public boolean getAutoHatchPickup() {
       return autoHatch;
   }
 
   // Sets the hatch pistion to the boolean state it gives
   public void setHatchPiston(boolean state){
       beakStatus = state;
       if(state){
           hatchPiston.set(Value.kForward);
       }
       else{
           hatchPiston.set(Value.kReverse);
       }
       putSmartDashboardHatch(state);
   }
 
   // Will return the  status of the solinoid returns true or false
   public boolean getHatchPistonStatus(){
 
       if(hatchPiston.get() == Value.kForward){
           return true;
       }
       else{
           return false;
       }
      
   }
 
   // shows if the Beak State value is true or false on the shuffleboard
   public void putSmartDashboardHatch(boolean hatchStatus) {
 
       SmartDashboard.putBoolean("Beak State", hatchStatus);
 
   }
 
}
 
//hi sam
//bye sam
