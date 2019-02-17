package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import edu.wpi.first.wpilibj.command.WaitCommand;
import frc.robot.Equations;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RetrieveHatch extends CommandGroup {

  public RetrieveHatch() {
    
    addParallel(new ElevatorCom(2800)); //raises elevator 4 inches before clamp
    addSequential(new WaitCommand(.75));
    addSequential(new Hatch(Value.kReverse, Value.kReverse));
  }
}
