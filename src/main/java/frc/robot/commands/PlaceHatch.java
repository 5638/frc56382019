package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Constants;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class PlaceHatch extends CommandGroup {

  public PlaceHatch() {
    addParallel(new Hatch(Value.kReverse, Value.kForward)); //unclamp
    addSequential(new ElevatorCom(Robot.elevator.getElevatorPosition() - 1000));
  }
}
