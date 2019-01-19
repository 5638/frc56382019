/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
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

  public static DoubleSolenoid shift;
  //DT END

  //ELEVATOR START
  public static WPI_TalonSRX elevatorMaster;
  public static WPI_VictorSPX elevatorSlave;
  //ELEVATOR END

  //WRIST START
  public static WPI_TalonSRX wrist;
  //WRIST END

  //INTAKE START
  public static WPI_VictorSPX intake;
  public static DoubleSolenoid hatch;
  //INTAKE END

  public static void init(){
    //DT START
    leftMaster = new WPI_TalonSRX(1);
    leftSlave0 = new WPI_VictorSPX(2);
    //leftSlave1 = new WPI_VictorSPX(3);
    rightMaster = new WPI_TalonSRX(3);
    rightSlave0 = new WPI_VictorSPX(4);
    //rightSlave1 = new WPI_VictorSPX(6);

    leftGear = new SpeedControllerGroup(leftMaster, leftSlave0/*, leftSlave1*/);
    rightGear = new SpeedControllerGroup(rightMaster, rightSlave0/*, rightSlave1*/);

    drive = new DifferentialDrive(leftGear, rightGear);

    shift = new DoubleSolenoid(1, 1, 2);
    //DT END

    //ELEVATOR START
    elevatorMaster = new WPI_TalonSRX(7);
    elevatorMaster.config_kP(0, 0, 10);
    elevatorMaster.config_kI(0, 0, 10);
    elevatorMaster.config_kD(0, 0, 10);
    elevatorMaster.configPeakOutputForward(1, 10);
    elevatorMaster.configPeakOutputReverse(-1, 10);
    
    elevatorSlave = new WPI_VictorSPX(8);
    elevatorSlave.follow(elevatorMaster);
    //ELEVATOR END

    //WRIST START
    wrist = new WPI_TalonSRX(9);
    wrist.config_kP(0, 0, 10);
    wrist.config_kI(0, 0, 10);
    wrist.config_kD(0, 0, 10);
    wrist.configPeakOutputForward(1, 10);
    wrist.configPeakOutputReverse(-1, 10);
    //WRIST END

    //INTAKE START
    intake = new WPI_VictorSPX(10);
    hatch = new DoubleSolenoid(0, 0, 1);
    //INTAKE END
  }
}
