package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;

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
      SmartDashboard.putNumber("Command Pos", position);
  }

  @Override
  protected boolean isFinished() {
    return false;
  }

  @Override
  protected void end() {
    Robot.elevator.elevatorPosition(position);
  }

  @Override
  protected void interrupted() {
    end();
  }
}