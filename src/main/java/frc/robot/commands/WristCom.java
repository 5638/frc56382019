package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.Robot;

public class WristCom extends Command {

  public double position;
  private double elevatorVelocity = Math.abs(Robot.elevator.getElevatorVelocity());
  
  public WristCom(double position) {
    requires(Robot.wrist);
    this.position = position; //this sets the class variable equal to the function variable.

    

    //MOVES WRIST OUT OF WAY SO IT DOES NOT GET STUCK ON ELEVATOR
  }

  @Override
  protected void initialize() {
  }

  @Override
  protected void execute() {
    

    //CHANGE BEFORE LSR
    if(Math.abs(Robot.elevator.getElevatorVelocity()) > 100 && Robot.elevator.getElevatorPosition() > 10000){
      Robot.wrist.wristPosition(-1000);
    }else{
      Robot.wrist.wristPosition(position);
    }
    //CHANGE BEFORE LSR
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