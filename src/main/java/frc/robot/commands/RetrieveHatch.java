package frc.robot.commands;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;

public class RetrieveHatch extends CommandGroup {

  public RetrieveHatch() {
    
    addParallel(new ElevatorCom(2800)); //raises elevator 4 inches before clamp
    addSequential(new WaitCommand(.5));
    addSequential(new Hatch(Value.kReverse, Value.kReverse));
  }
}
