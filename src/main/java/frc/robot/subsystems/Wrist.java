package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.RobotMap;
import frc.robot.TalonChecker;
import frc.robot.commands.WristManual;

public class Wrist extends Subsystem {
  
  private final WPI_TalonSRX wrist = RobotMap.wrist;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new WristManual());
  }

  public Wrist(){
    TalonChecker.checkError("Wrist", wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 100), 
                            "Could not detect Wrist Encoder. Switch to manual control and check encoder ASAP: ",
                            SmartDashboard.putBoolean("Wrist Encoder", false));

    SmartDashboard.putNumber("Wrist Velocity", RobotMap.wrist.getSelectedSensorVelocity());
    SmartDashboard.putNumber("Wrist Acceleration", RobotMap.wrist.getSelectedSensorVelocity());
  }

  public void zeroWrist(){
    wrist.setSelectedSensorPosition(0);
  }
  public void wristPosition(double position){
    wrist.set(ControlMode.MotionMagic, position);
    System.out.println("Wrist Target Position: " + position);
  }
  public void stop(){
    wrist.set(0);
  }

  public void wristManual(Joystick xbox){
    wrist.set(xbox.getRawAxis(3) * xbox.getRawAxis(3) * xbox.getRawAxis(3));
  }
}
