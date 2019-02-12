package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Equations;
import frc.robot.Robot;

public class RetrieveHatch extends CommandGroup {

  public RetrieveHatch() {
    addSequential(new ElevatorCom(Robot.elevator.getElevatorPosition() + Equations.rotationToHeight(Equations.hatchInches()))); //raises elevator 4 inches before clamp
    addSequential(new Hatch(Value.kReverse, Value.kReverse));
  }
}
