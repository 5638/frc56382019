/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCom;

/**
 * An example subsystem.  You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private final DifferentialDrive drive = RobotMap.drive;
  private double integral, previous_error = 0;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new DriveCom());
  }

  public void drive(Joystick xbox){
    drive.arcadeDrive((-xbox.getRawAxis(2) + xbox.getRawAxis(3)), -xbox.getRawAxis(0));
  }

  public void stop(){
    drive.arcadeDrive(0, 0);
  }

  public void vision(Joystick xbox){
    double error = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double kp = .05;
    double ki = 0.01;
    double kd = 0;
    integral += (error * .02);
    double derivative = (error - previous_error) / 0.02;
    previous_error = error;

    double visionOutput = (kp*error) + (ki * integral) + (kd * derivative);

    drive.setSafetyEnabled(false);
    drive.arcadeDrive((-xbox.getRawAxis(2) + xbox.getRawAxis(3)), -visionOutput);
    System.out.println(visionOutput);
  }

}
