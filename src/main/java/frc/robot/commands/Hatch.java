package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Hatch extends Command {
  public Value valueClamp;
  public Value valueExtend;

  public Hatch(Value valueClamp, Value valueExtend) {
    requires(Robot.intake);
    this.valueClamp = valueClamp;
    this.valueExtend = valueExtend;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.intake.hatch(valueClamp, valueExtend);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
  }

  @Override
  protected void interrupted() {
    end();
  }
}
