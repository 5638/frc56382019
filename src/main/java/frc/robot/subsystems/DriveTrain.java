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

  private static final double maxElevatorHeight = 15000;

  private double integral, previous_error = 0;

  public boolean hasShifted = false;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    //setDefaultCommand(new DriveCom());
    setDefaultCommand(new Shift(Value.kForward));
  }

  private double getElevatorHeight(){
    return elevator.getSelectedSensorPosition(0);
  }

  public void drive(Joystick xbox){
    drive.arcadeDrive((-xbox.getRawAxis(2) + xbox.getRawAxis(3)), -xbox.getRawAxis(0));

    double rampSpeed = (getElevatorHeight()/maxElevatorHeight) + 1;
    right.configOpenloopRamp(rampSpeed, 10);
    left.configOpenloopRamp(rampSpeed, 10);
  }

  public void stop(){
    drive.arcadeDrive(0, 0);
  }

  public void vision(Joystick xbox){
    double ta0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta0").getDouble(0);
    double ta1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta0").getDouble(0);
    double tx0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx0").getDouble(0);
    double tx1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx1").getDouble(0);
    double error = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

    //FOR GETTING PERPENDICULAR TO TARGET
    if(ta0 < ta1){
      error = error - tx1;
    }else if(ta1 < ta0){
      error = error - tx0;
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
  }

  //AUTOSHIFT START

  public boolean canShift(){
    boolean canShift = false;

    if(hasShifted == true){
      //.02 seconds per loop... .5 second timer... 25 loops for .5 sec
      for(int time = 0; time < 25; time++){
        if(time == 25){
          canShift = true;
          hasShifted = false;
        }
      }
    }
    return canShift;
  }

  public int getAverageVelocity(){
	  return (right.getSelectedSensorVelocity(0) + left.getSelectedSensorVelocity(0)) / 2;
  }

  public double getAverageAcceleration(){
    return getAverageVelocity() / 0.02;
  }

  public Value setHigh(){
    return Value.kForward;
  }

  public Value setLow(){
    return Value.kReverse;
  }

  public boolean isHigh(){
    return shift.get() == Value.kForward;
  }

  public boolean isLow(){
    return shift.get() == Value.kReverse;
  }

  public boolean wantsHigh(){
    if(getAverageVelocity() > 4000 || getAverageAcceleration() < 2000 && getElevatorHeight() < 10000){
      return true;
    }else{
      return false;
    }
  }

  public boolean wantsLow(){
    if(getAverageVelocity() < 3000 || getAverageAcceleration() > 3000 && getElevatorHeight() < 10000){
      return true;
    }else{
      return false;
    }
  }

  public void shift(Value value){
    shift.set(value);

    if(wantsHigh() && isLow() && canShift()){
      shift.set(setHigh());
      hasShifted = true;
    }else if(wantsLow() && isHigh() && canShift()){
      shift.set(setLow());
      hasShifted = true;
    }
  }
}
