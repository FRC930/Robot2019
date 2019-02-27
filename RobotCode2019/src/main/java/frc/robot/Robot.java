/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import frc.robot.CargoIntake.CargoPositionEnums;

/**
 * Main robot control
 */
public class Robot extends TimedRobot {

  /**
   * Initializes subsystems at robot startup
   */
  @Override
  public void robotInit() {

    TeleopHandler.init();
    Drive.init();
    Elevator.init();
    CargoIntake.init();
    HatchIntake.init();
    VisionTracking.init();
    Endgame.init();

  }

  /**
   * Runs periodically after robot startup
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * Initializes sandstorm requirements at start of Sandstorm Mode
   */
  @Override
  public void autonomousInit() {

    Sandstorm.init();

  }

  /**
   * Runs periodically during Sandstorm Mode
   */
  @Override
  public void autonomousPeriodic() {

    Sandstorm.run();

  }

  /**
   * Runs periodically during Teleoperated Mode
   */
  @Override
  public void teleopPeriodic() {

    TeleopHandler.run();

  }
  
  @Override
  public void testPeriodic() {
  }
}
