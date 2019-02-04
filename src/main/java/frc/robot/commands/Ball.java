package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class Ball extends Command {
  public double speedInside;
  public double speedOutside;
  public Ball(double speedInside, double speedOutside) {
    requires(Robot.intake);
    this.speedInside = speedInside;
    this.speedOutside = speedOutside;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.intake.ball(speedInside, speedOutside);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.intake.ball(0, 0);
  }

  @Override
  protected void interrupted() {
    end();
  }
}