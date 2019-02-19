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
    private static DigitalInput bumperR = new DigitalInput(Constants.TELEOPH_HATCH_BUTTON_SWITCH_R);

    //sets up the button sensor for left
    private static DigitalInput bumperL = new DigitalInput(Constants.TELEOPH_HATCH_BUTTON_SWITCH_L);

    private static boolean pressedL = false;

    private static boolean pressedR = false;

    static {
        
    }

    // To be initialized at start of teleop period
    public static void init() {
        
        driver = new Joystick(Constants.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(Constants.CODRIVER_CONTROLLER_ID);
        Elevator.getSmartDashboardElevator(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y), manualElevatorToggle);
        Utilities.startCapture();
        HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
        CargoIntake.run(CargoPositionEnums.cargoStop);

    }

    // To be run during teleop periodic
    public static void run() {

        Elevator.getSmartDashboardElevator(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y), manualElevatorToggle);
        
        // Drive Code--------------------------------    
            if(!driver.getRawButton(Constants.DRIVER_BUTTON_RB)) {
                Drive.run(driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_X), driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));
            }
        // Drive Code--------------------------------

        // Beak Code-------------------------------
            pressedL = bumperL.get();
            pressedR = bumperR.get();   
        
            if((!isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_LT)) /* || pressedL || pressedR)*/ && beakToggle == false && !isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_RT)))) {
                beakToggle = true;
            }
            
            else if((isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_LT))/* || pressedL || pressedR)*/ && beakToggle == true)) {
                beakToggle = false;
                
                beakStatus = !beakStatus;
                HatchIntake.run(beakStatus);
            }
        // Beak Code-------------------------------

        // Arm Intake Code---------------------------

            // If LB is pressed and the button control is false, set button control true
            if (isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LT))) {
                IntakeArm.run(Constants.ARM_STATE_DOWN);
            }

            // If LB is pressed and the button control is true, set button control false and set armActivity opposite to itself
            else{
                IntakeArm.run(Constants.ARM_STATE_UP);
            }
        // Arm Intake Code---------------------------


        // Endgame Code------------------------------
            if(driver.getRawButton(Constants.DRIVER_BUTTON_RB)){
                Endgame.run(driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));
            }
        // Endgame Code------------------------------

        // Cargo Intake Code-------------------------

            //Motor control sets speed for intake. Hand is out.
            if(isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_RT)) && !isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LT)) && Elevator.atIntakePostiion()) { 
                HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoIntake);
            }
            //Motor control sets speed for outtake. Hand is out.
            else if(isTriggerPressed(driver.getRawAxis(Constants.DRIVER_AXIS_RT)) && !isTriggerPressed(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LT))) {
                HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
                CargoIntake.run(CargoIntake.CargoPositionEnums.cargoOutTake);
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
                if(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y) > Constants.DRIVE_DEADBAND_JOYSTICK) {
                    Elevator.manualMotionMagic(coDriverLeftY);
                }

                // If button1(A) is pressed then go to the position 500 using motion magic
                else if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)) {
                    Elevator.setTargetPos(ElevatorStates.RocketLevelOneCargo); 
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
                if(coDriver.getRawAxis(Constants.CODRIVER_AXIS_LEFT_Y) > Constants.DRIVE_DEADBAND_JOYSTICK) {
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
