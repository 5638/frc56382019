/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.ElevatorCom;

/**
 * Add your docs here.
 */
public class Elevator extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.
  private final WPI_TalonSRX ElevatorMaster = RobotMap.elevatorMaster;
  private final AHRS gyro = Robot.gyro;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    setDefaultCommand(new ElevatorCom(0));
  }

  public void zeroElevator(){
    ElevatorMaster.setSelectedSensorPosition(0);
  }
  public void elevatorPosition(double position){

    if(Math.abs(gyro.getYaw()) > 25){
      position = 0;
    }

    ElevatorMaster.set(ControlMode.MotionMagic, position);
    System.out.println("Elevator Target Position: " + position);
  }
  public void stop(){
    ElevatorMaster.set(0);
  }

}
