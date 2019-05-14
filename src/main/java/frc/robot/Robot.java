package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.subsystems.DriveTrain;
import frc.robot.subsystems.Elevator;
import frc.robot.subsystems.Intake;
import frc.robot.subsystems.Wrist;

public class Robot extends TimedRobot {
  public static DriveTrain driveTrain;
  public static Elevator elevator;
  public static Wrist wrist;
  public static Intake intake;
  public static Ultrasonic ultrasonic;
  public static AHRS gyro;
  public static OI m_oi;
  public static PowerDistributionPanel pdp;
  private Spark led;

  @Override
  public void robotInit() {
    RobotMap.init();
    driveTrain = new DriveTrain();
    elevator = new Elevator();
    wrist = new Wrist();
    intake = new Intake();
    //ultrasonic = new Ultrasonic(1, 1);
    gyro = new AHRS(SPI.Port.kMXP);
    m_oi = new OI();
    pdp = new PowerDistributionPanel();

    led = RobotMap.led;

    CameraServer.getInstance().startAutomaticCapture();

    elevator.zeroElevator();
    wrist.zeroWrist();
    gyro.reset();
    driveTrain.shift(Value.kForward);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("camMode").setNumber(1);
    NetworkTableInstance.getDefault().getTable("limelight").getEntry("ledMode").setNumber(1);

    System.out.println("DRIVE TEAM, THIS IS ROBOT, I AM GO FOR LAUNCH.");
    driveTrain.genPath();
  }

  @Override
  public void robotPeriodic() {
    SmartDashboard.putBoolean("limit switch", RobotMap.elevatorLimitSwitch.get());
    
  }

  @Override
  public void disabledInit() {
    System.out.println("START SENDING IT BOI");
    led.set(-.59);
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
    led.set(-.59);
  }

  @Override
  public void autonomousInit() {
    teleopPeriodic(); //go straight to driver control
    if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
      led.set(.83);
    }else{
      led.set(.61);
    }
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    teleopPeriodic();
  }

  @Override
  public void teleopInit() {
    if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue){
      led.set(.83);
    }else{
      led.set(.61);
    }
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue && DriverStation.getInstance().getMatchTime() > 30){
      led.set(.83);
    }else if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red && DriverStation.getInstance().getMatchTime() > 30){
      led.set(.61);
    }else if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Blue && DriverStation.getInstance().getMatchTime() < 30){
      led.set(-.09);
    }else if(DriverStation.getInstance().getAlliance() == DriverStation.Alliance.Red && DriverStation.getInstance().getMatchTime() < 30){
      led.set(-.11);
    }
  }

  @Override
  public void testPeriodic() {
  }

  public Robot(){
    //SmartDashboard.putNumber("Distance", ultrasonic.getRangeInches());
    //SmartDashboard.putNumber("PDP Current", pdp.getTotalCurrent());
    //SmartDashboard.putNumber("PDP Voltage", pdp.getVoltage());
  }

}
