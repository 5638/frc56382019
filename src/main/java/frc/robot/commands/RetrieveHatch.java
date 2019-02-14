package frc.robot.commands;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.command.CommandGroup;
import frc.robot.Equations;
import frc.robot.Robot;
import frc.robot.RobotMap;

public class RetrieveHatch extends CommandGroup {

  public RetrieveHatch() {
    addParallel(new ElevatorCom(RobotMap.elevatorMaster.getSelectedSensorPosition(0) + 1000)); //raises elevator 4 inches before clamp
    Timer.delay(1.5);
    addParallel(new Hatch(Value.kReverse, Value.kReverse));
  }
}
