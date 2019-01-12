/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;


public class Robot extends TimedRobot {

  
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

  
  @Override
  public void robotPeriodic() {
  }

 
  @Override
  public void autonomousInit() {

    Sandstorm.init();

  }

 
  @Override
  public void autonomousPeriodic() {

    Sandstorm.run();

  }

  
  @Override
  public void teleopPeriodic() {

    TeleopHandler.run();

  }

  
  @Override
  public void testPeriodic() {
  }
}
