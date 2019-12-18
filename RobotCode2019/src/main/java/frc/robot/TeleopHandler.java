/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.*;
import edu.wpi.first.wpilibj.GenericHID.RumbleType;
import frc.robot.CargoIntake.CargoPositionEnums;
//import frc.robot.Elevator.Constants.ElevatorStates;

public class TeleopHandler {
    private final int CODRIVER_AXIS_LEFT_X = 0;
    private final int CODRIVER_AXIS_LEFT_Y = 1;
    private final int CODRIVER_AXIS_RIGHT_X = 4;
    private final int CODRIVER_AXIS_RIGHT_Y = 5;
    private final int CODRIVER_AXIS_LT = 2;
    private final int CODRIVER_AXIS_RT = 3;

    private final int DRIVER_AXIS_LEFT_X = 0;
    private final int DRIVER_AXIS_LEFT_Y = 1;
    private final int DRIVER_AXIS_RIGHT_X = 4;
    private final int DRIVER_AXIS_RIGHT_Y = 5;

    private final int DRIVER_AXIS_LT = 2;
    private final int DRIVER_AXIS_RT = 3;

    // Driver joystick
    private static Joystick driver;
    // Codriver joystick
    private static Joystick coDriver;
    private final int CODRIVER_CONTROLLER_ID = 1;
    private final int DRIVER_CONTROLLER_ID = 0;

    // Elvevator Manual Toggle

    private static boolean manualElevatorToggle = false;
    private static boolean buttonManualToggle = false;
    private static double coDriverLeftY;

    private static double endgameCubedLeftJoyStick;

    private static boolean endgameToggleAuto = true;
    private static boolean endgameButtonToggle = false;

    private static boolean driverlimitingtoggle = false;
    private static boolean driverlimitingbutton = false;

    private static boolean sandstormCheck = false;
    private static double previousRumbleIntensity = Constants.RUMBLE_STOP;
    private HatchIntake hatchIntake;
    private VisionTracking visionTracking;
    private CargoIntake cargoIntake;
    private IntakeArm intakeArm;
    private Elevator elevator;
    private Endgame endgame;
    private Drive drive;

    // To be initialized at start of teleop period
    public TeleopHandler() {

        this.visionTracking = VisionTracking.getInstance();

        this.intakeArm = IntakeArm.getInstance();

        this.elevator = Elevator.getInstance();
        this.hatchIntake = HatchIntake.getInstance();
        this.cargoIntake = CargoIntake.getInstance();
        this.drive = Drive.getInstance();

        endgame = Endgame.getInstance();
        endgame.setMotorControllers();
        driver = new Joystick(this.DRIVER_CONTROLLER_ID);
        coDriver = new Joystick(this.CODRIVER_CONTROLLER_ID);
        this.elevator.putSmartDashboardElevator(coDriver.getRawAxis(this.CODRIVER_AXIS_LEFT_Y), manualElevatorToggle);
        Utilities.startCapture();
        this.hatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
        endgame.putSmartDashboardEndgame(endgameToggleAuto);
        cargoIntake.run(CargoPositionEnums.cargoStop);
    }

    // To be run during teleop periodic
    public void run() {
        Elevator myElevator = Elevator.getInstance();
        myElevator.putSmartDashboardElevator(coDriver.getRawAxis(this.CODRIVER_AXIS_LEFT_Y), manualElevatorToggle);

        // Drive Code--------------------------------
        if (driver.getRawButton(Constants.DRIVER_BUTTON_LB)) {
            if (!endgameToggleAuto) {
                // Drive.run(0, driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_Y));
            }
        } else {
            if (!driver.getRawButton(Constants.DRIVER_BUTTON_RB)) {
                // System.out.println("not holding LB and not holding RB");
                // Drive.run(driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_X),
                // driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));

                if (myElevator.atIntakePosition() && hatchIntake.getAutoHatchPickup()) {

                    // System.out.println(" elevator at intake position, autoHatch is true, and
                    // running limelight tracking");
                    this.visionTracking.run(driver.getRawButton(Constants.DRIVER_BUTTON_RB),
                            driver.getRawAxis(this.DRIVER_AXIS_RIGHT_X), driver.getRawAxis(this.DRIVER_AXIS_LEFT_Y));

                    // we removed the ! from this if statement
                    // -- we thought that the solenoid was hooked up incorrectly, which would mean
                    // that
                    // the getHatchPistonStatus method was returning the wrong boolean
                    if (hatchIntake.getHatchPistonStatus() == Constants.HATCH_STATE_CLOSED) {
                        // System.out.println(" beak is closed and running auto hatch pickup");
                        this.visionTracking.runAutoHatch(hatchIntake.getAutoHatchPickup());
                    }
                }
            } else {
                // System.out.println("not holding LB and holding RB");
                // Check if the elevator is at a lower level.
                if (myElevator.atIntakePosition()) {
                    // System.out.println(" elevator at intake position and running limelight
                    // tracking");
                    // Run Vision Tracking Method
                    this.visionTracking.run(driver.getRawButton(Constants.DRIVER_BUTTON_RB),
                            driver.getRawAxis(this.DRIVER_AXIS_RIGHT_X), driver.getRawAxis(this.DRIVER_AXIS_LEFT_Y));
                    if (hatchIntake.getHatchPistonStatus() == Constants.HATCH_STATE_CLOSED
                            && hatchIntake.getAutoHatchPickup()) {
                        // System.out.println(" beak is closed, autoHatch is true, and running auto
                        // hatch pickup");
                        this.visionTracking.runAutoHatch(hatchIntake.getAutoHatchPickup());
                    }
                } else {
                    // System.out.println(" elevator not at intake position and running regular
                    // drive code");
                    // Drive.run(driver.getRawAxis(Constants.DRIVER_AXIS_RIGHT_X),
                    // driver.getRawAxis(Constants.DRIVER_AXIS_LEFT_Y));
                }
            }
        }

        if (driver.getRawButton(Constants.DRIVER_BUTTON_START) && !driverlimitingbutton) {
            driverlimitingbutton = true;
        }
        if (!driver.getRawButton(Constants.DRIVER_BUTTON_START) && driverlimitingbutton) {
            driverlimitingbutton = false;
            driverlimitingtoggle = !driverlimitingtoggle;
            // Drive.driveTrainLimiting(driverlimitingtoggle);
        }

        // Drive Code--------------------------------

        // Beak Code-------------------------------
        // pressedL = bumperL.get();
        // pressedR = bumperR.get();

        hatchIntake.run(isTriggerPressed(driver.getRawAxis(this.DRIVER_AXIS_LT)),
                isTriggerPressed(coDriver.getRawAxis(this.CODRIVER_AXIS_RT)));
        // Beak Code-------------------------------

        // Hatch Pusher-------------------------------

        /*
         * if (!hatchIntake.getHatchPistonStatus()) { HatchPusher.run(); } else {
         * //Fixed for Mark :) hatchPusher.setHatchPusherToggleState(false); }
         */

        // Hatch Pusher-------------------------------

        // Arm Intake Code---------------------------

        // If coDriver LT is pressed, while Driver & coDriver RT isn't, and the button
        // control is false, set button control true
        if (isTriggerPressed(coDriver.getRawAxis(this.CODRIVER_AXIS_LT))
                && !isTriggerPressed(driver.getRawAxis(this.DRIVER_AXIS_RT))
                && !isTriggerPressed(coDriver.getRawAxis(this.CODRIVER_AXIS_RT))) {
            this.intakeArm.run(Constants.ARM_STATE_DOWN);
            sandstormCheck = true;
        }
        // If LB is pressed and the button control is true, set button control false and
        // set armActivity opposite to itself
        else if (sandstormCheck) {

            this.intakeArm.run(Constants.ARM_STATE_UP);
        }

        // Arm Intake Code---------------------------

        // Endgame Code------------------------------

        // cubes joystick for smoother motion during the manual code
        endgameCubedLeftJoyStick = Math.pow(driver.getRawAxis(this.DRIVER_AXIS_LEFT_Y), 3);

        // button toggle process
        if (driver.getRawButton(Constants.DRIVER_BUTTON_BACK) && !endgameButtonToggle) {
            endgameButtonToggle = true;
        }
        // button toggle process
        if (!driver.getRawButton(Constants.DRIVER_BUTTON_BACK) && endgameButtonToggle) {
            endgameButtonToggle = false;
            endgameToggleAuto = !endgameToggleAuto;
            // out puts the state of our endgame(either auto or manual) to shuffle board
            endgame.putSmartDashboardEndgame(endgameToggleAuto);
        }

        // when the driver is holding LB
        if (driver.getRawButton(Constants.DRIVER_BUTTON_LB)) {

            // sets our elevator all the way down
            elevator.setTargetPos(Constants.ElevatorStates.ResetElevator);

            // checks to see if we are in auto or manual
            // System.out.println("endgameToggleAuto: " + endgameToggleAuto);
            if (endgameToggleAuto) {
                // If our encoder values are bad then rumble the controller
                if (endgame.encoderCheck()) {
                    driver.setRumble(GenericHID.RumbleType.kLeftRumble, Constants.RUMBLE_FULL_INTENSITY);
                    driver.setRumble(GenericHID.RumbleType.kRightRumble, Constants.RUMBLE_FULL_INTENSITY);
                }
                // if the left joystick is all the way up
                if (driver.getRawAxis(this.DRIVER_AXIS_LEFT_Y) <= Constants.ENDGAME_AUTO_UP_DEADBAND) {
                    // System.out.println("Going up in auto");

                    // Unpause the Endgame if and only if we were previously paused
                    // -- The unpause endgame method checks to see if we were previously paused
                    endgame.unpauseEndgame();

                    // Runs the endgame like noraml in auto
                    endgame.runAuto();
                    // System.out.println("Running auto");
                }

                // if the left joystick is down run the endgame manually down and stops the
                // wheels
                else if (endgameCubedLeftJoyStick >= Constants.DRIVE_DEADBAND_JOYSTICK) {
                    // System.out.println("going down in auto");
                    endgame.runManual(endgameCubedLeftJoyStick);
                    this.drive.runAt(Constants.ENDGAME_STOP_SPEED, Constants.ENDGAME_STOP_SPEED);
                }

                // if the left joystick is not up or down then pause the endgame foot and drive
                // train
                else {
                    // System.out.println("Pausing auto");

                    // set the pause flag and maintian previouse state
                    endgame.pauseEndgame();

                    // Runs the endgame like normal in auto
                    endgame.runAuto();
                }

            }
            // this is our joystick controlled endgame code
            else {
                // Turn the rummble off if in the manual
                driver.setRumble(GenericHID.RumbleType.kLeftRumble, Constants.RUMBLE_STOP);
                driver.setRumble(GenericHID.RumbleType.kRightRumble, Constants.RUMBLE_STOP);

                // System.out.println("Manual piston toggle " +
                // coDriver.getRawButton(Constants.CODRIVER_BUTTON_BACK));
                endgame.setEndgamePiston(coDriver.getRawButton(Constants.CODRIVER_BUTTON_BACK));
                if (Math.abs(endgameCubedLeftJoyStick) >= Constants.DRIVE_DEADBAND_JOYSTICK) {
                    endgame.runManual(endgameCubedLeftJoyStick);
                }

                // if the left joystick is not up or down then stop the endgame foot and wheels
                else {
                    endgame.runManual(Constants.ENDGAME_STOP_SPEED);
                    // Endgame.setEndgamePiston(Constants.ENDGAME_PISTON_RETRACTED);
                }
            }
        }

        // if LB is not held then run stop so it does not move and turn the compressor
        // on again
        else {
            endgame.runManual(Constants.ENDGAME_STOP_SPEED);
            if (!Utilities.getCompressorState()) {
                Utilities.setCompressorState(Constants.COMPRESSOR_ON);
            }
        }
        // Endgame Code------------------------------

        // Cargo Intake Code-------------------------
        // Cargo controls will not run during the endgame
        if (!driver.getRawButton(Constants.DRIVER_BUTTON_LB)) {
            // Motor control sets speed for intake. Hand is out.
            if (isTriggerPressed(driver.getRawAxis(this.DRIVER_AXIS_RT))) {
                cargoIntake.run(CargoIntake.CargoPositionEnums.cargoOutTake);
            } else if (isTriggerPressed(coDriver.getRawAxis(this.CODRIVER_AXIS_RT))
                    && !isTriggerPressed(coDriver.getRawAxis(this.CODRIVER_AXIS_LT))) {
                // Elevator.setTargetPos(Constants.ElevatorStates.RocketLevelOneCargo);
                myElevator.setTargetPos(Constants.ElevatorStates.CARGO_INTAKE);

                cargoIntake.run(CargoIntake.CargoPositionEnums.cargoIntake);
            } else if (isTriggerPressed(coDriver.getRawAxis(this.CODRIVER_AXIS_RT))
                    && coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB)) {
                cargoIntake.run(CargoIntake.CargoPositionEnums.cargoIntake);

                // Elevator.setTargetPos(myConstants.ElevatorStates.RocketLevelOneCargo);
                myElevator.setTargetPos(Constants.ElevatorStates.CARGO_INTAKE);
            }
            // Motor control sets speed for outtake. Hand is out.
            else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB)
                    && -coDriver.getRawAxis(this.CODRIVER_AXIS_RIGHT_Y) > 0.5) {

                cargoIntake.run(CargoPositionEnums.cargoCarryingIntake);
            } else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB)
                    && -coDriver.getRawAxis(this.CODRIVER_AXIS_RIGHT_Y) < -0.5) {
                cargoIntake.run(CargoPositionEnums.cargoCarryingOuttake);
            } else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB)
                    && !isTriggerPressed(coDriver.getRawAxis(this.CODRIVER_AXIS_LT))) {
                cargoIntake.run(CargoIntake.CargoPositionEnums.cargoCarrying);
                // hatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
            }

            else if (-coDriver.getRawAxis(this.CODRIVER_AXIS_RIGHT_Y) > 0.5) {
                cargoIntake.runManual(true);
            } else if (-coDriver.getRawAxis(this.CODRIVER_AXIS_RIGHT_Y) < -0.5) {
                cargoIntake.runManual(false);
            } else { // Motor control sets speed to stop. Hand is up.
                cargoIntake.run(CargoIntake.CargoPositionEnums.cargoStop);

            }
        }
        // Cargo Intake Code-------------------------

        // Elevator Stuff----------------------------

        // Cubing left joystick for manual
        coDriverLeftY = Math.pow(coDriver.getRawAxis(this.CODRIVER_AXIS_LEFT_Y), 3);

        // If Button Press for manual mode of elevator
        if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_START) && buttonManualToggle == false) {
            buttonManualToggle = true;
        } else if (!coDriver.getRawButton(Constants.CODRIVER_BUTTON_START) && buttonManualToggle == true) {
            buttonManualToggle = false;
            manualElevatorToggle = !manualElevatorToggle;
        }

        // If button press is true then will run manaul
        if (manualElevatorToggle) {
            // If the left Y stick is bigger than dead band then send it to motion magic or
            // dont run
            if (Math.abs(coDriverLeftY) > Constants.DRIVE_DEADBAND_JOYSTICK) {
                myElevator.run(coDriverLeftY);
            } else {
                myElevator.run(0.0);
            }
        } else // if(VisionTracking.getAutoElevatorState() ||
               // !driver.getRawButton(Constants.DRIVER_BUTTON_RB))
        {
            /*
             * if(!driver.getRawButton(Constants.DRIVER_BUTTON_RB)){
             * VisionTracking.setAutoElevatorState(false); }
             */
            // If codriver is holding rb then motion magic will run for the cargo position
            if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_RB)
                    || coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB)) {
                // If the Y stick is above deadband run manual motion magic mode
                // System.out.println("PRESSING RB");

                if (coDriver.getRawAxis(this.CODRIVER_AXIS_LEFT_Y) > Constants.ELEVATOR_MOTION_MAGIC_DEADBAND) {
                    myElevator.manualMotionMagic(coDriverLeftY);
                    // System.out.println("Moving Joystick");
                }
                // If button1(A) is pressed then go to the position 500 using motion magic
                else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)) {
                    // System.out.println("PRESs A BEFORE");
                    myElevator.setTargetPos(Constants.ElevatorStates.RocketLevelOneCargo);
                    // System.out.println("PRESS A AFTER");
                }
                // If button2(B) is pressed then go to the middle spot using motion magic
                else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_B)) {
                    myElevator.setTargetPos(Constants.ElevatorStates.RocketLevelTwoCargo);
                }
                // If the y button is pressed go to level three cargo
                else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_Y)) {
                    myElevator.setTargetPos(Constants.ElevatorStates.RocketLevelThreeCargo);
                }
                // If the right stick is pressed go to the lowest position
                else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_RIGHT_STICK)) {
                    myElevator.setTargetPos(Constants.ElevatorStates.ResetElevator);
                }
            }

            // If none are true run hatch positions for motion magic
            else {
                // If the left stick is above dead band then run manual motion magic
                if (coDriver.getRawAxis(this.CODRIVER_AXIS_LEFT_Y) > Constants.ELEVATOR_MOTION_MAGIC_DEADBAND) {
                    myElevator.manualMotionMagic(coDriverLeftY);
                }
                // If button1(A) is pressed go to the level one hatch and player station
                if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)) {
                    myElevator.setTargetPos(Constants.ElevatorStates.RocketLevelOneHatchAndPlayerStation);
                }
                // If button2(B) is pressed then go to the level two hatch
                else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_B)) {
                    myElevator.setTargetPos(Constants.ElevatorStates.RocketLevelTwoHatch);
                }
                // If button4(Y) is pressed go to the level three cargo
                else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_Y)) {
                    myElevator.setTargetPos(Constants.ElevatorStates.RocketLevelThreeHatch);
                }
                // If the right stick is pressed go to the lowest position
                else if (coDriver.getRawButton(Constants.CODRIVER_BUTTON_RIGHT_STICK)) {
                    myElevator.setTargetPos(Constants.ElevatorStates.ResetElevator);
                }
            }
        }
        /*
         * if(!VisionTracking.getAutoElevatorState() &&
         * driver.getRawButton(Constants.DRIVER_BUTTON_RB)){
         * 
         * if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_A)){ buttonPressed =
         * Constants.CODRIVER_BUTTON_A; } else
         * if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_B)){ buttonPressed =
         * Constants.CODRIVER_BUTTON_B; } else
         * if(coDriver.getRawButton(Constants.CODRIVER_BUTTON_Y)){ buttonPressed =
         * Constants.CODRIVER_BUTTON_Y; } VisionTracking.runAutoElevator(buttonPressed);
         * }
         */
        // Elevator Stuff---------------------------------------------------------

        // Hatch Floor Intake-----------------------------------------------------
        // gives the run method the codrivers left bumper status
        // HatchFloorIntake.run(coDriver.getRawButton(Constants.CODRIVER_BUTTON_LB));
        // Hatch Floor Intake-----------------------------------------------------
    }

    // Checks to see if a trigger is pressed. Itgets a axisvalue
    private static boolean isTriggerPressed(double axisValue) {

        // If the axis is above the deadband it returns true if not returns false
        if (axisValue >= Constants.TRIGGER_PRESSED_VALUE_THRESHOLD) {
            return true;
        } else {
            return false;
        }
    }

    public static void setRumble(int controllerID, double intensity) {

        if (previousRumbleIntensity != intensity)
            previousRumbleIntensity = intensity;

        if (controllerID == 0) {
            driver.setRumble(RumbleType.kLeftRumble, intensity);
            driver.setRumble(RumbleType.kRightRumble, intensity);
        } else if (controllerID == 1) {
            coDriver.setRumble(RumbleType.kLeftRumble, intensity);
            coDriver.setRumble(RumbleType.kRightRumble, intensity);
        }
    } // end of setRumble

} // end of class of TeleopHandler
