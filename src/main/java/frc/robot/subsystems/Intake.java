package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Subsystem;
import frc.robot.RobotMap;
import frc.robot.commands.Hatch;

public class Intake extends Subsystem {

  private final WPI_VictorSPX intake = RobotMap.intake;
  private final WPI_VictorSPX intake2 = RobotMap.intake2;
  private final DoubleSolenoid hatchClamp = RobotMap.hatchClamp;
  private final DoubleSolenoid hatchExtend = RobotMap.hatchExtend;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new Hatch(Value.kReverse, Value.kReverse));
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
