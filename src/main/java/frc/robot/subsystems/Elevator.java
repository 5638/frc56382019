package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Equations;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.TalonChecker;
import frc.robot.commands.ElevatorCom;

public class Elevator extends Subsystem {
  private final WPI_TalonSRX ElevatorMaster = RobotMap.elevatorMaster;
  private final AHRS gyro = Robot.gyro;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ElevatorCom(0));
  }

  public Elevator(){
    TalonChecker.checkError("Elevator", ElevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100), 
                            "Could not detect Elevator Encoder. Switch to manual control and check encoder ASAP: ",
                            SmartDashboard.putBoolean("Elevator Encoder", false));

    SmartDashboard.putNumber("Elevator Velocity", RobotMap.elevatorMaster.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("Elevator Acceleration", RobotMap.elevatorMaster.getSelectedSensorVelocity(0)/0.02);
  }

  public void zeroElevator(){
    ElevatorMaster.setSelectedSensorPosition(0);
  }

  public void elevatorPosition(double position){

    if(Math.abs(gyro.getPitch()) > 25 || Math.abs(gyro.getRoll()) > 25){
      position = 0;
    }

    ElevatorMaster.set(ControlMode.MotionMagic, position);
    SmartDashboard.putNumber("Elevator Target Position", position);
    SmartDashboard.putNumber("Elevator Height", Equations.heightToInches(ElevatorMaster.getSelectedSensorPosition(0)));
  }

  public void elevatorManualControl(double speed){
    ElevatorMaster.set(speed);
  }

  public void stop(){
    ElevatorMaster.set(0);
  }

}
