package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Shift extends Command {
  public Value value;

  public Shift(Value value) {
    requires(Robot.driveTrain);
    this.value = value;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.driveTrain.shift(value);
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
  }
}
