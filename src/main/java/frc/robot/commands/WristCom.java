package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristCom extends Command {
  public double position;
  public WristCom(double position) {
    requires(Robot.wrist);
    this.position = position; //this sets the class variable equal to the function variable.
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.wrist.wristPosition(position);
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
