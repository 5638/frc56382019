package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristManual extends Command {

  public WristManual() {
    requires(Robot.wrist);
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.wrist.wristManual(Robot.m_oi.xbox2);
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
