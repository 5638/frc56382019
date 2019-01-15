/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

/**
 * The RobotMap is a mapping from the ports sensors and actuators are wired into
 * to a variable name. This provides flexibility changing wiring, makes checking
 * the wiring easier and significantly reduces the number of magic numbers
 * floating around.
 */
public class RobotMap {
  //DT START
  public static WPI_TalonSRX leftMaster;
  public static WPI_VictorSPX leftSlave0;
  public static WPI_VictorSPX leftSlave1;
  public static WPI_TalonSRX rightMaster;
  public static WPI_VictorSPX rightSlave0;
  public static WPI_VictorSPX rightSlave1;

  public static SpeedControllerGroup leftGear;
  public static SpeedControllerGroup rightGear;

  public static DifferentialDrive drive;
  //DT END

  //ELEVATOR START
  public static WPI_TalonSRX ElevatorMaster;
  public static WPI_VictorSPX ElevatorSlave;
  //ELEVATOR END

  public static void init(){
    //DT START
    leftMaster = new WPI_TalonSRX(1);
    leftSlave0 = new WPI_VictorSPX(2);
    leftSlave1 = new WPI_VictorSPX(3);
    rightMaster = new WPI_TalonSRX(4);
    rightSlave0 = new WPI_VictorSPX(5);
    rightSlave1 = new WPI_VictorSPX(6);

    leftGear = new SpeedControllerGroup(leftMaster, leftSlave0, leftSlave1);
    rightGear = new SpeedControllerGroup(rightMaster, rightSlave0, rightSlave1);

    drive = new DifferentialDrive(leftGear, rightGear);
    //DT END

    //ELEVATOR START
    ElevatorMaster = new WPI_TalonSRX(7);
    ElevatorMaster.config_kP(0, 0, 10);
    ElevatorMaster.config_kI(0, 0, 10);
    ElevatorMaster.config_kD(0, 0, 10);
    ElevatorMaster.configPeakOutputForward(1, 10);
    ElevatorMaster.configPeakOutputReverse(-1, 10);
    
    ElevatorSlave = new WPI_VictorSPX(8);
    ElevatorSlave.follow(ElevatorMaster);
    //ELEVATOR END
  }
}
