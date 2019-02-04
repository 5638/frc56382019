package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class ElevatorCom extends Command {
  public double position;

  public ElevatorCom(double position) {
    requires(Robot.elevator);
    this.position = position;
  }

  @Override
  protected void initialize() {
    
  }

  @Override
  protected void execute() {
      Robot.elevator.elevatorPosition(position);
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