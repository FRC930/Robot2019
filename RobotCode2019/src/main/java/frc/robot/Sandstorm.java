/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;
//import com.sun.tools.javadoc.main.Start;

import edu.wpi.first.wpilibj.Timer;
import frc.robot.CargoIntake.CargoPositionEnums;
/*
 * Just running Teleop
 */
public class Sandstorm {
    private static Timer orderTimer = new Timer();

    static {

    }

    public static void init() {
        HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);
        IntakeArm.setArmPiston(Constants.ARM_STATE_DOWN);
        // orderTimer.reset();
        // orderTimer.start();
        
        
        //IntakeArm.setArmPiston(Constants.ARM_STATE_DOWN);
        //HatchIntake.setHatchPiston(Constants.HATCH_STATE_OPEN);

    }

    public static void run() {
        // System.out.println("Running...");
        // //Sets it so that we can run the main code during sandstorm
        // if(orderTimer.get() >= 0.25){
        //     IntakeArm.setArmPiston(Constants.ARM_STATE_DOWN);
        //     orderTimer.stop();
        // }
       
        // if(orderTimer.get() >= 1){//Constants.SANDSTORM_TIMER_CARGO_OUT * 3)) {
        //     System.out.println("Stopping cargo");
        //     CargoIntake.run(CargoPositionEnums.cargoStop);
        //     orderTimer.stop();
        // }
        
        
        
        // else if(orderTimer.get() >= 0.2){ //Constants.SANDSTORM_TIMER_CARGO_OUT){
        //     System.out.println("Starting cargo");
        //     CargoIntake.run(CargoPositionEnums.cargoOutTake);
        // }
       
        
        TeleopHandler.run();
        
    }
    
}
