package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristCom extends Command {

  public double position;
  private double elevatorVelocity = Math.abs(Robot.elevator.getElevatorVelocity());
  
  public WristCom(double position) {
    requires(Robot.wrist);
    this.position = position; //this sets the class variable equal to the function variable.

    //CHANGE BEFORE LSR
    if(elevatorVelocity > 100){
      this.position = -1000;
    }else{
      this.position = position;
    }
    //CHANGE BEFORE LSR

    //MOVES WRIST OUT OF WAY SO IT DOES NOT GET STUCK ON ELEVATOR
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