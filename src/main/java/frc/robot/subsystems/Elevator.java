package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Equations;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.TalonChecker;
import frc.robot.commands.ElevatorManual;

public class Elevator extends Subsystem {

  private final WPI_TalonSRX ElevatorMaster = RobotMap.elevatorMaster;
  private final AHRS gyro = Robot.gyro;

  double previous_vel = 0;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ElevatorManual());
  }

  public Elevator(){
    TalonChecker.checkError("Elevator", ElevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100),
                            "Could not detect Elevator Encoder. Switch to manual control and check encoder ASAP: ",
                            SmartDashboard.putBoolean("Elevator Encoder", TalonChecker.getTrueFalse()));

    SmartDashboard.putNumber("Elevator Velocity", RobotMap.elevatorMaster.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("Elevator Acceleration", RobotMap.elevatorMaster.getSelectedSensorVelocity(0)/0.02);
  }

  public void zeroElevator(){
    ElevatorMaster.setSelectedSensorPosition(0);
  }

  public double getElevatorPosition(){
    return ElevatorMaster.getSelectedSensorPosition(0);
  }

  public double getElevatorVelocity(){
    double vel = ElevatorMaster.getSelectedSensorVelocity(0);
    return vel;
  }

  public double getElevatorAcceleration(){
    return (ElevatorMaster.getSelectedSensorVelocity(0) - previous_vel)/0.02;
  }
  public void elevatorPosition(double position){

    //if(Math.abs(gyro.getPitch()) > 25 || Math.abs(gyro.getRoll()) > 25){
    //  position = 0;
    //}

    ElevatorMaster.set(ControlMode.MotionMagic, position);
    SmartDashboard.putNumber("Elevator Target Position", position);
    SmartDashboard.putNumber("Elevator Height", Equations.heightToInches(ElevatorMaster.getSelectedSensorPosition(0)));
  }

  public void elevatorManualControl(Joystick xbox){
    ElevatorMaster.set(xbox.getRawAxis(1) * xbox.getRawAxis(1) * xbox.getRawAxis(1));
  }

  public void stop(){
    ElevatorMaster.set(0);
  }
}
