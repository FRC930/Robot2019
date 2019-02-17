/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.robot.Elevator.ElevatorStates;


public class TeleopHandler {

    // Driver joystick
    private static Joystick driver;
    // Codriver joystick
    private static Joystick coDriver;
    // Intake Arm Code
    private static boolean armStatus = true;
    private static boolean armActivity = true;

    // Elvevator Manual Toggle
    private static boolean manualElevatorToggle = false;
    private static boolean buttonManualToggle = false;
    private static double coDriverLeftY;

    //Beak toggle
    public static boolean beakToggle = false;
    private static boolean beakStatus = false;

    //sets up the button sensor for right
    private static DigitalInput bumperR = new DigitalInput(0);

    //sets up the button sensor for left
    private static DigitalInput bumperL = new DigitalInput(1);

    private static boolean pressedL = false;

    private static boolean pressedR = false;

    static {
        
    }

    // To be initialized at start of teleop period
    public static void init() {
        
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        Utilities.startCapture();

    }

    // To be run during teleop periodic
    public static void run() {
        
        // Drive Code--------------------------------    
            if(!driver.getRawButton(Constants.DRIVER_BUTTON_RB)){

                Drive.run(driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_X), driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));
        
            }
        // Drive Code--------------------------------

        // Beak Code-------------------------------
            pressedL = bumperL.get();
            pressedR = bumperR.get();   
        
            if((isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_LT)) /* || pressedL || pressedR)*/ && beakToggle == false)){
                
                beakToggle = true;
            
            }
            
            if((!isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_LT))/* || pressedL || pressedR)*/ && beakToggle == true)){
                
                beakToggle = false;
                
                beakStatus = !beakStatus;
            
            }
            
            HatchIntake.run(beakStatus);
            
        // Beak Code-------------------------------

        // Arm Intake Code---------------------------
            // If LB is pressed and the button control is false, set button control true
            if (isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LT))) {
                IntakeArm.run(false);
            }

            // If LB is pressed and the button control is true, set button control false and set armActivity opposite to itself
            else{

                IntakeArm.run(true);
            
            }
        // Arm Intake Code---------------------------


        // Endgame Code------------------------------
            if(driver.getRawButton(Constants.DRIVER_BUTTON_RB)){
        
            Endgame.run(driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));

            }
        // Endgame Code------------------------------

        // Cargo Intake Code-------------------------

            if(isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_RT))) //Motor control sets speed for inttake. Hand is out.
            {
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoIntake);
            }
            else if(isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_RT))) //Motor control sets speed for outtake. Hand is out.
            {
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoOutTake);
            }
            else //Motor control sets speed to stop. Hand is held up.
            {
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoStop);
            }
        // Cargo Intake Code-------------------------
        
        //Elevator Stuff----------------------------
        coDriverLeftY = Math.pow(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y),3);

        if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_START) && buttonManualToggle == false){
            
            buttonManualToggle = true;
        }
        else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_START) && buttonManualToggle == true){

            buttonManualToggle = false;
            manualElevatorToggle = !manualElevatorToggle;

        }
        if(manualElevatorToggle){
            if(coDriverLeftY > Constants.DRIVE_DEADBAND_JOYSTICK){
                
                Elevator.run(coDriverLeftY);
                
            }
            else{
                Elevator.run(0.0);
            }
        }
        else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_RB)){

            if(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y) > Constants.DRIVE_DEADBAND_JOYSTICK){
                
                Elevator.manualMotionMagic(coDriverLeftY);

            }
            //if button1(A) is pressed then go to the position 500 using motion magic
            else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)){
    
             Elevator.setTargetPos(ElevatorStates.RocketLevelOneCargo); 
    
            }
    
            //If button2(B) is pressed then go to the middle spot using motion magic
            else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_B)){
    
              Elevator.setTargetPos(ElevatorStates.RocketLevelTwoCargo); 
    
            }
    
            else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_Y)){
    
              Elevator.setTargetPos(ElevatorStates.RocketLevelThreeCargo); 
    
            }
    
          }
          else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_RIGHT_STICK)){
            
            Elevator.setTargetPos(ElevatorStates.ResetElevator);
          
        }
          else{

            if(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y) > Constants.DRIVE_DEADBAND_JOYSTICK){
                
                Elevator.manualMotionMagic(coDriverLeftY);

            }
            
            if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)){
              
                Elevator.setTargetPos(ElevatorStates.RocketLevelOneHatchAndPlayerStation);
            
            }
            
            else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_B)){
              
                Elevator.setTargetPos(ElevatorStates.RocketLevelTwoHatch);
            
            }
            
            else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_Y)){
              
                Elevator.setTargetPos(ElevatorStates.RocketLevelThreeHatch);
           
            }
    
          }
        // Elevator Stuff---------------------------------------------------------

        // Hatch Floor Intake-----------------------------------------------------

          HatchFloorIntake.run(coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB));
          
        // Hatch Floor Intake-----------------------------------------------------

    }
    
    

    private static boolean isTriggerPressed(double axisValue) {
        
        if (axisValue >= Constants.TRIGGER_PRESSED_VALUE_THRESHOLD)
            return true;
        else
            return false;

    }

}
