/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import frc.robot.RobotMap;
import frc.robot.commands.Shift;

/**
 * An example subsystem. You can replace me with your own Subsystem.
 */
public class DriveTrain extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  double  kp = .05, 
          ki = 0.1, 
          kd = 0.005;

  private final DifferentialDrive drive = RobotMap.drive;
  private final DoubleSolenoid shift = RobotMap.shift;
  private final WPI_TalonSRX right = RobotMap.rightMaster;
  private final WPI_TalonSRX left = RobotMap.leftMaster;
  private final WPI_TalonSRX elevator = RobotMap.elevatorMaster;

  private double elevatorHeight = elevator.getSelectedSensorPosition(0);
  private double rampSpeed = elevatorHeight/15000;

  private double integral, previous_error = 0;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new DriveCom());
    setDefaultCommand(new Shift(Value.kForward));
  }

  public void drive(Joystick xbox){
    drive.arcadeDrive((-xbox.getRawAxis(2) + xbox.getRawAxis(3)), -xbox.getRawAxis(0));

    right.configOpenloopRamp(rampSpeed, 10);
    left.configOpenloopRamp(rampSpeed, 10);
  }

  public void stop(){
    drive.arcadeDrive(0, 0);
  }

  public void vision(Joystick xbox){
    double ta0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta0").getDouble(0);
    double ta1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta0").getDouble(0);
    double error = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    double ta0err = Math.abs((ta1 - ta0)/ta1); //change difference in area to % error
    double ta1err = Math.abs((ta0 - ta1)/ta0);

    //FOR GETTING PERPENDICULAR TO TARGET
    if(ta0err > .05){
      error = error - ta1;
    }else if(ta1err > .05){
      error = error - ta0;
    }else{
      error = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }
    //END
    
    integral += (error * .02);
    double derivative = (error - previous_error) / 0.02;
    previous_error = error;

    double visionOutput = (kp*error) + (ki * integral) + (kd * derivative);

    drive.setSafetyEnabled(false);
    drive.arcadeDrive((-xbox.getRawAxis(2) + xbox.getRawAxis(3)), -visionOutput);
    System.out.println("Final Motor Output: " + visionOutput);
    System.out.println("ta0err: " + ta0err);
    System.out.println("ta1err: " + ta1err);
  }

  public void shift(Value value){
    shift.set(value);
  }

}
