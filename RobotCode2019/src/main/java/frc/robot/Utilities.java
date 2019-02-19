/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.cscore.UsbCamera;
import edu.wpi.first.cameraserver.CameraServer;

/**
 * Code for components that don't fit in a subsystem, or can be used by multiple subsystems.
 * 
 * Ex: Compressor isn't related to a subsystem.
 *     PDP can be used by all subsystems.
 */
public class Utilities {

    //Sets up a compressor for use
    public static Compressor compress = new Compressor(Constants.UTILITIES_COMPRESSOR_ID);
    
    //sets up a PDP to use 
    public static final PowerDistributionPanel Power = new PowerDistributionPanel(Constants.ENDGAME_POWER_DISTRIBUTION_PANEL);

    static {

        //turns on the compressor
        compress.setClosedLoopControl(true);

    }

    public static void init() {

    }

    public static void run() {
        
    }
    // Starts the capture for the cameras
    public static void startCapture() {

        // creates a thread which runs concurrently with the program
        new Thread(() -> {

          // Instantiate the USB cameras and begin capturing their video streams
          UsbCamera camera = CameraServer.getInstance().startAutomaticCapture(Constants.UTIL_CAMERA_0_ID);
          UsbCamera camera2 = CameraServer.getInstance().startAutomaticCapture(Constants.UTIL_CAMERA_1_ID);
    
          // set the cameras' reolutions and FPS
          camera.setResolution(Constants.CAMERA1_WIDTH, Constants.CAMERA1_HEIGHT);
          camera.setFPS(Constants.CAMERA1_FPS);
          camera2.setResolution(Constants.CAMERA2_WIDTH, Constants.CAMERA2_HEIGHT);
          camera2.setFPS(Constants.CAMERA2_FPS);

        }).start();

    }

}
