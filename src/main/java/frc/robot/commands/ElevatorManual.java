package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ElevatorManual extends Command {

  public double speed;

  public ElevatorManual(double speed) {
    requires(Robot.elevator);
    this.speed = speed;
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    Robot.elevator.elevatorManualControl(speed);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.elevator.stop();
  }

  @Override
  protected void interrupted() {
    end();
  }
}
