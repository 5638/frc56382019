package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Equations;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.TalonChecker;
import frc.robot.commands.ElevatorCom;

public class Elevator extends Subsystem {

  //double wristPos;

  private final WPI_TalonSRX ElevatorMaster = RobotMap.elevatorMaster;
  private final WPI_VictorSPX ElevatorSlave = RobotMap.elevatorSlave;
  private final AHRS gyro = Robot.gyro;
  private final DigitalInput limitSwitch = RobotMap.elevatorLimitSwitch;
  //private final Command wristCom = new WristCom(wristPos);

  double previous_vel = 0;

  double position;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new ElevatorCom(0));
  }

  public Elevator(){
    TalonChecker.checkError("Elevator", ElevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100),
                            "Could not detect Elevator Encoder. Switch to manual control and check encoder ASAP: ",
                            SmartDashboard.putBoolean("Elevator Encoder", TalonChecker.getTrueFalse()));


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

  public boolean isFinished(){
    return ElevatorMaster.getClosedLoopError() < 25;
  }

  public double getTarget(){
    return position;
  }

  public double placeHatch(){
      if(position < 2500){
        return 2000;
      }else if(position > 11000 && position < 13000){
        return 11700;
      }else if(position > 20100 && position < 22100){
        return 20800;
      }else{
        return 0;
      }
  }

  public void elevatorPosition(double position){

    //if(Math.abs(gyro.getPitch()) > 25 || Math.abs(gyro.getRoll()) > 25){
     // position = 0;
    //}

    SmartDashboard.putNumber("Elevator Velocity", RobotMap.elevatorMaster.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("Elevator Position", RobotMap.elevatorMaster.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Elevator Target", position);
    SmartDashboard.putNumber("Elevator Acceleration", RobotMap.elevatorMaster.getSelectedSensorVelocity(0)/0.02);

    if(!limitSwitch.get()){
      zeroElevator();
    }

    
    ElevatorMaster.set(ControlMode.MotionMagic, position);
    SmartDashboard.putNumber("ElevatorMaster Output", ElevatorMaster.getMotorOutputPercent());
    SmartDashboard.putNumber("ElevatorSlave Output", ElevatorSlave.getMotorOutputPercent());

    SmartDashboard.putNumber("Elevator Target Position", position);
    SmartDashboard.putNumber("Elevator Height", Equations.heightToInches(ElevatorMaster.getSelectedSensorPosition(0)));

    
  }

  public void elevatorManualControl(Joystick xbox){
    ElevatorMaster.set(xbox.getRawAxis(1) * xbox.getRawAxis(1) * xbox.getRawAxis(1));
    SmartDashboard.putNumber("Elevator Velocity", RobotMap.elevatorMaster.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("Elevator Position", RobotMap.elevatorMaster.getSelectedSensorPosition(0));
    SmartDashboard.putNumber("Elevator Acceleration", RobotMap.elevatorMaster.getSelectedSensorVelocity(0)/0.02);
    SmartDashboard.putNumber("ElevatorMaster Output", ElevatorMaster.getMotorOutputPercent());
    SmartDashboard.putNumber("ElevatorSlave Output", ElevatorSlave.getMotorOutputPercent());
  }

  public void stop(){
    ElevatorMaster.set(0);
  }
}
