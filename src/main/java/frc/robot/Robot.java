package frc.robot;

import com.kauailabs.navx.frc.AHRS;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Ultrasonic;
import edu.wpi.first.wpilibj.I2C.Port;
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

  @Override
  public void robotInit() {
    RobotMap.init();
    driveTrain = new DriveTrain();
    elevator = new Elevator();
    wrist = new Wrist();
    intake = new Intake();
    //ultrasonic = new Ultrasonic(1, 1);
    gyro = new AHRS(Port.kMXP);
    m_oi = new OI();
    pdp = new PowerDistributionPanel();

    elevator.zeroElevator();
    wrist.zeroWrist();
    gyro.reset();

    System.out.println("DRIVE TEAM, THIS IS ROBOT, I AM GO FOR LAUNCH.");
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void disabledInit() {
    System.out.println("LQPV ROBOTICS, THE ROBOT HAS LANDED.");
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void autonomousInit() {
    teleopPeriodic(); //go straight to driver control
  }

  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
  }

  @Override
  public void teleopInit() {
  }

  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
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
