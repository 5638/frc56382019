package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Equations;
import frc.robot.Robot;

public class PlaceHatch extends CommandGroup {

  public PlaceHatch() {
    addSequential(new Hatch(Value.kReverse, Value.kForward)); //unclamp
    addSequential(new ElevatorCom(Robot.elevator.getCurrentPosition() - Equations.rotationToHeight(Equations.hatchInches()))); //drops elevator 4 inches after placement.
    addSequential(new Hatch(Value.kReverse, Value.kReverse)); //retracts hatch mech.
  }
}
