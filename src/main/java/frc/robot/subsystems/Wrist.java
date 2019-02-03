/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.TalonChecker;

/**
 * Add your docs here.
 */
public class Wrist extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private final WPI_TalonSRX wrist = RobotMap.wrist;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public Wrist(){
    TalonChecker.checkError("Wrist", wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100), 
                            "Could not detect Wrist Encoder. Switch to manual control and check encoder ASAP: ",
                            SmartDashboard.putBoolean("Wrist Encoder", false));

    SmartDashboard.putNumber("Wrist Velocity", RobotMap.wrist.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Wrist Acceleration", RobotMap.wrist.getSelectedSensorVelocity());
  }

  public void zeroWrist(){
    wrist.setSelectedSensorPosition(0);
  }
  public void wristPosition(double position){
    wrist.set(ControlMode.MotionMagic, position);
    System.out.println("Wrist Target Position: " + position);
  }
  public void stop(){
    wrist.set(0);
  }

  public void wristManual(double speed){
    wrist.set(speed);
  }
}
