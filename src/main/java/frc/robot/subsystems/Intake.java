/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;

/**
 * Add your docs here.
 */
public class Intake extends Subsystem {
  // Put methods for controlling this subsystem
  // here. Call these from Commands.

  private final WPI_VictorSPX intake = RobotMap.intake;
  private final WPI_VictorSPX intake2 = RobotMap.intake2;
  private final DoubleSolenoid hatchClamp = RobotMap.hatchClamp;
  private final DoubleSolenoid hatchExtend = RobotMap.hatchExtend;

  @Override
  public void initDefaultCommand() {
    // Set the default command for a subsystem here.
    // setDefaultCommand(new MySpecialCommand());
  }

  public void ball(double speedInside, double speedOutside){
    intake.set(speedInside);
    intake2.set(speedOutside);
  }

  public void hatch(Value valueClamp, Value valueExtend){
    hatchClamp.set(valueClamp);
    hatchExtend.set(valueExtend);
  }
}
