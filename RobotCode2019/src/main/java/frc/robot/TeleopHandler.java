/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import frc.robot.CargoIntake.CargoPositionEnums;
import frc.robot.Elevator.ElevatorStates;



public class TeleopHandler {

    // Driver joystick
    private static Joystick driver;
    // Codriver joystick
    private static Joystick coDriver;

    // Elvevator Manual Toggle
    private static boolean manualElevatorToggle = false;
    private static boolean buttonManualToggle = false;
    private static double coDriverLeftY;

    //Beak toggle
    public static boolean beakToggle = false;
    private static boolean beakStatus = true;

    //sets up the button sensor for right
    private static DigitalInput bumperR = new DigitalInput(Constants.TELEOPH_HATCH_BUTTON_SWITCH_R);

    //sets up the button sensor for left
    private static DigitalInput bumperL = new DigitalInput(Constants.TELEOPH_HATCH_BUTTON_SWITCH_L);

    private static boolean pressedL = false;

    private static boolean pressedR = false;
    
    private static double endgameCubedJoyStick;

    private static boolean endgameToggleAuto = true;
    private static boolean endgameButtonToggle = false;
    private static boolean endgameStartTimer = false;

    private static boolean driverlimitingtoggle = false;
    private static boolean driverlimitingbutton = false;

    private static boolean sandstormCheck = false;
        static {
        
    }

    // To be initialized at start of teleop period
    public static void init() {
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        Elevator.putSmartDashboardElevator(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y), manualElevatorToggle);
        HatchIntake.putSmartDashboardHatch(beakStatus);
        Utilities.startCapture();
        HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
        Endgame.putSmartDashboardEndgame(endgameToggleAuto);
        CargoIntake.run(CargoPositionEnums.cargoStop);

    }

    // To be run during teleop periodic
    public static void run() {

        Elevator.putSmartDashboardElevator(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y), manualElevatorToggle);
        
        // Drive Code--------------------------------    
            if(driver.getRawButton(Constants.DRIVER_BUTTON_LB)){    
                if(!endgameToggleAuto){
                    Drive.run(0, driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_Y));
                }
            }
            else {
                if (!driver.getRawButton(Constants.DRIVER_BUTTON_A)) {
                    Drive.run(driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_X), driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));
                } 
                else {
                    VisionTracking.run(driver.getRawButton(Constants.DRIVER_BUTTON_A), driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_X), driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));
                }
            }
            


            if(driver.getRawButton(Constants.DRIVER_BUTTON_START) && !driverlimitingbutton){
                driverlimitingbutton = true;
            }
            if(!driver.getRawButton(Constants.DRIVER_BUTTON_START) && driverlimitingbutton){
                driverlimitingbutton = false;
                driverlimitingtoggle = !driverlimitingtoggle;
                Drive.driveTrainLimiting(driverlimitingtoggle);
            }

            
        // Drive Code--------------------------------

        // Beak Code-------------------------------
            //pressedL = bumperL.get();
            //pressedR = bumperR.get();   
            
            // Button toggle for the beak
            if((isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_LT)) /* || pressedL || pressedR)*/ && beakToggle == false && !isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_RT)))) {
                beakToggle = true;
               
            }
            else if((!isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_LT))/* || pressedL || pressedR)*/ && beakToggle)) {
                beakToggle = false;
                beakStatus = !beakStatus;
                //sets the beak to the beakstatus 
                HatchIntake.run(beakStatus);
            }
        // Beak Code-------------------------------

        // Arm Intake Code---------------------------

                // If coDriver LT is pressed, while Driver & coDriver RT isn't, and the button control is false, set button control true
                if (isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LT)) && !isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_RT)) && !isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_RT))) {
                    IntakeArm.run(Constants.ARM_STATE_DOWN);
                    sandstormCheck = true;
                }
                // If LB is pressed and the button control is true, set button control false and set armActivity opposite to itself
                else if(sandstormCheck){

                    IntakeArm.run(Constants.ARM_STATE_UP);
                }

        // Arm Intake Code---------------------------

        //Hatch Pusher-------------------------------
 
                    HatchPusher.run(driver.getRawButton(Constants.DRIVER_BUTTON_RB));
                
        //Hatch Pusher-------------------------------      

        // Endgame Code------------------------------
            
            // cubes joystick for smoother motion during the manual code
            endgameCubedJoyStick = Math.pow(driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y),3);
            
            //button toggle process
            if(driver.getRawButton(Constants.DRIVER_BUTTON_BACK) && !endgameButtonToggle ){
                endgameButtonToggle = true;
            }
            //button toggle process
            if(!driver.getRawButton(Constants.DRIVER_BUTTON_BACK) && endgameButtonToggle){
                endgameButtonToggle = false;
                endgameToggleAuto = !endgameToggleAuto;
                //out puts the state of our endgame(either auto or manual) to shuffle board
                Endgame.putSmartDashboardEndgame(endgameToggleAuto);
            }

            // when the driver is holding LB
            if(driver.getRawButton(Constants.DRIVER_BUTTON_LB)) {
                
                //sets our elevator all the way down 
                Elevator.setTargetPos(ElevatorStates.ResetElevator);
                
                //checks to see if we are in auto or manual
                if(endgameToggleAuto){
                    
                    //if the left joystick is all the way up
                    if(driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y) <= Constants.ENDGAME_AUTO_UP_DEADBAND){
                        
                        //sets our endgame state to the previous state
                        Endgame.endgameSetState();
                        
                        //decides if it should start a timer or run the endgame
                        if(endgameStartTimer){
                            Endgame.runAuto();
                        }
                        //starts a timer if we are in the right state and the timer is false
                        else if(Endgame.getEndgameState() == Endgame.EndgameStates.BACK_PISTON_EXTENDED){
                            Endgame.endgameStartPistonTimer();
                            endgameStartTimer = true;
                        }
                        else if(Endgame.getEndgameState() == Endgame.EndgameStates.PAUSE_FOOT){
                            Endgame.endgameStartPauseFootTimer();
                            endgameStartTimer = true;
                        }
                        else if(Endgame.getEndgameState() == Endgame.EndgameStates.STOP_FOOT){
                            Endgame.endgameStartStopFootTimer();
                            endgameStartTimer = true;
                        }
                        else if(Endgame.getEndgameState() == Endgame.EndgameStates.BACKUP_ROBOT){
                            Endgame.endgameStartBackUpRobotTimer();
                            endgameStartTimer = true;
                        }
                        else if(Endgame.getEndgameState() == Endgame.EndgameStates.START_FOOT_AND_WHEELS || Endgame.getEndgameState() == Endgame.EndgameStates.CONTINUE_FOOT_AND_WHEELS || Endgame.getEndgameState() == Endgame.EndgameStates.STOP_WHEELS){
                            endgameStartTimer = true;
                        }
                    }
                    
                    //if the left joystick is down run the endgame manually down and stop the wheels
                    else if(driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y) >= 0.5){
                        Endgame.runManual(endgameCubedJoyStick);
                        Drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);
                    }
                    
                    //if the left joystick is not up or down then pause the endgame foot and drive train
                    else{
                        Endgame.endgameSendPauseAuto();   
                        Endgame.runAuto();
                        endgameStartTimer = false;
                    }
                
                }
                // this is our joystick controlled endgame code
                else{
                    if(Math.abs(endgameCubedJoyStick) >= Constants.DRIVE_DEADBAND_JOYSTICK){
                        Endgame.runManual(endgameCubedJoyStick);
                    }
                    
                    //if the left joystick is not up or down then stop the endgame foot and wheels
                    else {
                        Endgame.runManual(Constants.ENDGAME_STOP_SPEED);
                        Endgame.setEndgamePiston(Constants.ENDGAME_PISTON_RETRACTED);
                    }
                }
            }

        
            // if LB is not held then run stop so it does not move
            else {
                Endgame.runManual(Constants.ENDGAME_STOP_SPEED);
                Endgame.setEndgamePiston(Constants.ENDGAME_PISTON_RETRACTED);
            }
        // Endgame Code------------------------------

        // Cargo Intake Code-------------------------

            //Motor control sets speed for intake. Hand is out.
            if(isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_RT)) && !isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LT))) {
                Elevator.setTargetPos(ElevatorStates.RocketLevelOneCargo);
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoIntake);
            }
            //Motor control sets speed for outtake. Hand is out.
            else if(isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_RT)) && !isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LT))) {
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoOutTake);
            }
            else if(-coDriver.getRawAxis(Constants.CODRIVER_AXIS_RIGHT_Y) > 0.5) {
                CargoIntake.runManual(true);
            }
            else if(-coDriver.getRawAxis(Constants.CODRIVER_AXIS_RIGHT_Y) < -0.5) {
                CargoIntake.runManual(false);
            }
            else { //Motor control sets speed to stop. Hand is up.
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoStop);
            }
        // Cargo Intake Code-------------------------
        
        //Elevator Stuff----------------------------
            
            //Cubing left joystick for manual
            coDriverLeftY = Math.pow(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y),3);

            // If Button Press for manual mode of elevator
            if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_START) && buttonManualToggle == false) {
                buttonManualToggle = true;
            }
            else if(!coDriver.getRawButton(Constants.CODRIVER_BUTTON_START) && buttonManualToggle == true) {
                buttonManualToggle = false;
                manualElevatorToggle = !manualElevatorToggle;
            }

            // If button press is true then will run manaul
            if(manualElevatorToggle) {
                // If the left Y stick is bigger than dead band then send it to motion magic or dont run
                if(Math.abs(coDriverLeftY) > Constants.DRIVE_DEADBAND_JOYSTICK) {
                    Elevator.run(coDriverLeftY);
                }
                else {
                    Elevator.run(0.0);
                }

            }
            // If codriver is holding rb then motion magic will run for the cargo position
            else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_RB)) {
                // If the Y stick is above deadband run manual motion magic mode
                System.out.println("PRESSING RB");
                
                if(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y) > Constants.ELEVATOR_MOTION_MAGIC_DEADBAND) {
                    Elevator.manualMotionMagic(coDriverLeftY);
                    System.out.println("Moving Joystick");
                }
                // If button1(A) is pressed then go to the position 500 using motion magic
                else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)) {
                    System.out.println("PRESs A BEFORE");
                    Elevator.setTargetPos(ElevatorStates.RocketLevelOneCargo);
                    System.out.println("PRESS A AFTER"); 
                }
                // If button2(B) is pressed then go to the middle spot using motion magic
                else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_B)) {
                    Elevator.setTargetPos(ElevatorStates.RocketLevelTwoCargo); 
                }
                // If the y button is pressed go to level three cargo
                else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_Y)) {
                    Elevator.setTargetPos(ElevatorStates.RocketLevelThreeCargo); 
                }
        
            }
            // If the right  stick is pressed go to the lowest position
            else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_RIGHT_STICK)){
                Elevator.setTargetPos(ElevatorStates.ResetElevator);
            }
            // If none are true run hatch positions for motion magic
            else {
                // If the  left stick is above dead band then run manual motion magic
                if(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y) > Constants.ELEVATOR_MOTION_MAGIC_DEADBAND) {
                    Elevator.manualMotionMagic(coDriverLeftY);
                }
                
                // If button1(A) is pressed go to the level one hatch and player station
                if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)) {
                    Elevator.setTargetPos(ElevatorStates.RocketLevelOneHatchAndPlayerStation);
                }
                // If button2(B) is pressed then go to the level two hatch
                else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_B)) {
                    Elevator.setTargetPos(ElevatorStates.RocketLevelTwoHatch);
                }
                // If button4(Y) is pressed go to the level three cargo
                else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_Y)) {
                    Elevator.setTargetPos(ElevatorStates.RocketLevelThreeHatch);
                }
        
            }
        // Elevator Stuff---------------------------------------------------------

        // Hatch Floor Intake-----------------------------------------------------
            //gives the run method the codrivers left bumper status
            HatchFloorIntake.run(coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB));
        // Hatch Floor Intake-----------------------------------------------------
        }
    
    
    
    // Checks to see if a trigger is pressed. Itgets a axisvalue
    private static boolean isTriggerPressed(double axisValue) {
        
        // If the  axis is above the deadband it returns true if not returns false
        if (axisValue >= Constants.TRIGGER_PRESSED_VALUE_THRESHOLD){
            return true;
        }
        else{
            return false;
        }
    }
    public static void setStartTimerFalse(){
        endgameStartTimer = false;
    }
    

}
