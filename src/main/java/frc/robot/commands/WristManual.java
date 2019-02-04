package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristManual extends Command {

  public double speed;

  public WristManual(double speed) {
    requires(Robot.wrist);
    this.speed = speed;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.wrist.wristManual(speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.wrist.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
