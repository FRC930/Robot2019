/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/
package frc.robot;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;

/**b
 * Rotate the robot towards a vision target on the cargo ship or
 * rocket to properly line up the robot to place hatches and score cargo
 */
public class VisionTracking {
    // network table used to get data from the limelight
    private   NetworkTable limelightTable;

    // get the angle of the horizontal offset between the camera's crosshair and the target's crosshair from a network table
    // -- tx: the horizontal offset of the camera from the camera's crosshair and the target
    private  NetworkTableEntry tx;
    private   double horizontalAngle;

    // turning speed of the robot
    private   double horizontalSpeed;
    private   double distanceSpeed;

    // values of the left joystick's x-axis and the right joystick's y-axis
    private   double stickX;
    private   double stickY;
    private   boolean onStatus;
    private   boolean offStatus;

    //Movement of each side of wheels. used for rotation purposes
    private   double leftMovement;
    private   double rightMovement;
    
    // Used to see whether or not a target is in view of the camera
    // -- tv: a boolean that shows if a target is in view or out of view 
    private   NetworkTableEntry tv;

    //is set to tv. If tv is 1, then the target is visible. If 0, there is no target. if -1, the limelight is not connected
    private   double isTargetVisible;
    private   int elevatorAutoCounter;

    // Used to keep track of the current horizontal angle, and utilized when the target is out of sight of the limelight
    private   double prevHorizAngle;
    private   boolean autoElevatorState;
    private   NetworkTableEntry ta;
    private   NetworkTableEntry ledMode;
    private   int hatchAutoFrameCounter;
    private   boolean autoHatchGrabbed;

    public VisionTracking() {
        limelightTable = NetworkTableInstance.getDefault().getTable("limelight");
        tx = limelightTable.getEntry("tx");

        /* set the limelight and USB camera to picture-in-picture mode,
        which means the limelight's camera feed is shown in the
        bottom right corner of the USB camera's feed
            */

        limelightTable.getEntry("stream").setNumber(3);
        ledMode = limelightTable.getEntry("ledMode");

        ledMode.setNumber(3);
        tv = limelightTable.getEntry("tv");
        ta = limelightTable.getEntry("ta");

        horizontalAngle = 0;
        horizontalSpeed = 0;
        distanceSpeed = 0;
        stickX = 0.0;
        stickY = 0.0;
        leftMovement = 0.0;
        rightMovement = 0.0;
        isTargetVisible = -1;
        elevatorAutoCounter = 0;
        prevHorizAngle = 0;
        hatchAutoFrameCounter = 0;

        onStatus = true;
        offStatus = true;
        autoElevatorState = false;
        autoHatchGrabbed = false;
    }

    

    //isButtonPressed is a boolean, which expects a button's value
    //distanceSpeed is the driver's vertical joystick value for driving
    public   void run(boolean isButtonPressed, double rightX, double leftY) {

        // get the horizontal angle offest from the network table and store it as a double
        horizontalAngle = tx.getDouble(Constants.VISION_DEFAULT_LIMELIGHT_RETURN_VALUE);

        // Gets the state of if a target is in view, returns a 0 or 1 
        isTargetVisible = tv.getDouble(-1.0);

        stickX = -Math.pow(leftY, 3);
        stickY = Math.pow(rightX, 3);

        horizontalSpeed = stickY;
        distanceSpeed = stickX;

        /** 
         * if the A button is currently pressed, turn on the limelight's LEDs,
         * and if the A button is not pressed, turn off the LEDs
         *
         * getEntry("ledMode") is used to get the LedMode property from the NetworkTable
         * setNumber() is used to set the state of the LedMode property (the integer 1 sets the LEDs off, 3 sets the LEDs on)
         * 
         * ?: is a terenary operator in Java, it is basically a one-line if-else statement
         * To use it, give it a boolean variable, then do ?. Then after that, you can use it as
         * an if-else statement. 
         *
         * stick.getRawButton(A_BUTTON) ? 1 : 0
         * In this example, we use stick.getRawButton(A_BUTTON) as our boolean variable
         * if stick.getRawButton(A_BUTTON) is down (value is true), then it will return 1
         * if stick.getRawButton(A_BUTTON) is up (value is false), then it will return 0
         * 
         * With the 0 and 1 then, we use it to do some math. 
         * When the stick.getRawButton(A_BUTTON) is down (true), it returns 1
         * 1 + (2 * 1) = 3. 3 is sent into .setNumber(), which turns the LEDs on
         */

        //limelightTable.getEntry("ledMode").setNumber(1 + (2 * (isButtonPressed ? 1 : 0)));
        //limelightTable.getEntry("ledMode").setNumber();
        // Checks to see if the target is visible by the limelight
        if(isTargetVisible == 1) {

            // Sets the previous angle to the current horizontal angle 
            prevHorizAngle = horizontalAngle;
        }
        // code block will run if the driver's passed button is pressed
        
        /** 
         * rotate the robot towards the target if horizontal angle is greater 
         * than the horizontal angle threshold on either side of the target
         */
        horizontalSpeed = rotate(horizontalAngle, prevHorizAngle, isTargetVisible);
        //System.out.println("ADJUSTMENT = " + horizontalSpeed);
        // left and right speeds of the drivetrain
        leftMovement = distanceSpeed + horizontalSpeed;// + leftHorizSpeed;// * (horizontalAngle / 27);
        rightMovement = distanceSpeed - horizontalSpeed;// - rightHorizSpeed;// * (horizontalAngle / 27);
        //System.out.println("LEFT MOVEMENT = " + leftMovement);
        //System.out.println("RIGHT MOVEMENT = " + rightMovement);
        // sends the rotating speeds to the motors to rotate the robot
        Drive.runAt(-leftMovement, rightMovement);     
   }

   public   void runAutoElevator(int codriverButton){
        double area;
        
        area = ta.getDouble(Constants.VISION_DEFAULT_LIMELIGHT_RETURN_VALUE);
        if(!autoElevatorState){
            if(area >= Constants.VISION_AREA_FOR_ELEVATOR){
                elevatorAutoCounter++;
                if(elevatorAutoCounter >= Constants.VISION_ELEVATOR_LOOP_LIMIT){
                    autoElevatorState = true;
                    if(codriverButton == Constants.CODRIVER_BUTTON_A){
                       // Elevator.setTargetPos(Elevator.ElevatorStates.RocketLevelOneHatchAndPlayerStation);
                    }
                    if(codriverButton == Constants.CODRIVER_BUTTON_B){
                       // Elevator.setTargetPos(Elevator.ElevatorStates.RocketLevelTwoHatch);
                    }
                    if(codriverButton == Constants.CODRIVER_BUTTON_Y){
                       //Elevator.setTargetPos(Elevator.ElevatorStates.RocketLevelThreeHatch);
                    }
                }
            }
            else{
                elevatorAutoCounter = 0;
            }
        }
   }
   public   boolean getAutoElevatorState(){
        return autoElevatorState;

   }
   public   void setAutoElevatorState(boolean state){
    autoElevatorState = state;
   }

    //This method will enable the driver to automatically pick up
    //hatches from the playerstation
    public   double runAutoHatch(boolean isButtonPressed) {
        double targetArea;
        double rumbleIntensity = 0.0;        

        targetArea = ta.getDouble(Constants.VISION_DEFAULT_LIMELIGHT_RETURN_VALUE);

        if((targetArea > Constants.VISION_TARGET_AREA_LOWER_THRESHOLD && targetArea < Constants.VISION_TARGET_AREA_UPPER_THRESHOLD)) {
           
           
            if (isButtonPressed) {
                hatchAutoFrameCounter++;
            }

            //The rumble is not working, we need to fix it and clean it up
            //if (HatchIntake.getHatchPistonStatus()) 
                //rumbleIntensity = 0.5;

        } else
            hatchAutoFrameCounter = 0;

        if (hatchAutoFrameCounter >= Constants.VISION_FRAME_LIMIT) {
            
            HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
            TeleopHandler.setRumble(Constants.DRIVER_CONTROLLER_ID, Constants.RUMBLE_FULL_INTENSITY);

            setAutoHatchGrabbed(true);
            hatchAutoFrameCounter = 0;
        }

        //Returns a rumble value of 0
        return rumbleIntensity;
    }

    public   void setAutoHatchGrabbed(boolean status) {
        autoHatchGrabbed = status;
    }

    public   boolean getAutoHatchGrabbed() {
        return autoHatchGrabbed;
    }

    /**  
     * The rotate method returns a turning speed to rotate the robot based on the 
     * horizontal angle offset between the target's crosshair and the calibrated 
     * crosshair of the limelight.
     *   
     * The robot's turning speed is proportional to the angle offset, which means 
     * as the limelight gets closer to the target, the turning speed is much slower
     * and more precise.
     *
     * Once the angle offset is within a specified range, the robot will no longer
     * turn, meaning the robot will stop turning when the limelight's crosshair and
     * the target's crosshair are very near to each other.
     */
    private   double rotate(double xAngle, double previousAngle, double targetVisiblity) {

        // Initializing a variable for the rotational adjustment for the robot 
        double horizontalAdjustment = 0;
   
        // only rotate the robot if the horizontal angle offset is bigger than a threshold
        // uses absolute value of the horizontal angle to make sure it is above the threshold, no matter if it is negative or positive
        if(Math.abs(xAngle) > Constants.VISION_HORIZONTAL_ANGLE_THRESHOLD) {

            /* Current horizontal angle is divided by the maximum angle possible to lower the overall adjustment to speed 
            so as to not overshoot with too much speed.
            Multiplied by the default horizontal speed to limit the speed of the robot during the turn. This 
            is used to make sure that a higher angle will not turn the robot too fast.
            */
            //System.out.println("//----ADJUSTING----\\");
            horizontalAdjustment = Constants.VISION_DEFAULT_HORIZONTAL_SPEED * (xAngle / Constants.VISION_MAXIMUM_ANGLE);
        }

        // This section is for when a target is not in view, and we use it to get the target back into view
        // Tests to see if a target is not visible to the limelight camera
        if(targetVisiblity == 0) {

            // Checks to see if the previous angle that the limelight got is outside of the deadband
            if(Math.abs(previousAngle) > Constants.VISION_HORIZONTAL_ANGLE_THRESHOLD) {

                // Sets the motors to turn towards the target (For further explanation, see lines 110-113)
                horizontalAdjustment = Constants.VISION_DEFAULT_HORIZONTAL_SPEED * (previousAngle / Constants.VISION_MAXIMUM_ANGLE);
            }
        }
        // Returns the final value of the horizontal adjustment needed to turn the robot 
        return horizontalAdjustment;

    } //end of method rotate

} // end of class
