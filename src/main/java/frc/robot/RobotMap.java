package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Spark;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class RobotMap {
  //DT START
  public static WPI_TalonSRX leftMaster;
  public static WPI_VictorSPX leftSlave0;
  public static WPI_VictorSPX leftSlave1;
  public static WPI_TalonSRX rightMaster;
  public static WPI_VictorSPX rightSlave0;
  public static WPI_VictorSPX rightSlave1;

  public static SpeedControllerGroup leftGear;
  public static SpeedControllerGroup rightGear;

  public static DifferentialDrive drive;

  public static DoubleSolenoid shift;
  //DT END

  //ELEVATOR START
  public static WPI_TalonSRX elevatorMaster;
  public static WPI_VictorSPX elevatorSlave;
  //ELEVATOR END

  //WRIST START
  public static WPI_TalonSRX wrist;
  //WRIST END

  //INTAKE START
  public static WPI_VictorSPX intake;
  public static WPI_VictorSPX intake2;
  public static DoubleSolenoid hatchClamp;
  public static DoubleSolenoid hatchExtend;
  //INTAKE END

  //LED START
  public static Spark led;
  //LED END

  public static void init(){
    //DT START
    leftMaster = new WPI_TalonSRX(1);
    leftMaster.configFactoryDefault();
    leftMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    leftMaster.setNeutralMode(NeutralMode.Coast);
    //leftMaster.configOpenloopRamp(1, 10);
    leftSlave0 = new WPI_VictorSPX(2);

    rightMaster = new WPI_TalonSRX(3);
    rightMaster.configFactoryDefault();
    rightMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    rightMaster.setNeutralMode(NeutralMode.Coast);
    //rightMaster.configOpenloopRamp(1, 10);
    rightSlave0 = new WPI_VictorSPX(4);

    leftGear = new SpeedControllerGroup(leftMaster, leftSlave0);
    rightGear = new SpeedControllerGroup(rightMaster, rightSlave0);

    drive = new DifferentialDrive(leftGear, rightGear);
    drive.setSafetyEnabled(false);

    shift = new DoubleSolenoid(0, 4, 5);

    System.out.println("DRIVE ... GO.");
    //DT END

    //ELEVATOR START
    elevatorMaster = new WPI_TalonSRX(5);
    elevatorMaster.configFactoryDefault();
    elevatorMaster.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    elevatorMaster.selectProfileSlot(0, 0);
    elevatorMaster.config_kP(0, 5, 10);
    elevatorMaster.config_kI(0, 0.1, 10);
    elevatorMaster.config_kD(0, 0, 10);
    elevatorMaster.config_kF(0, 5, 10);
    elevatorMaster.config_IntegralZone(0, 10, 10);
    elevatorMaster.configPeakOutputForward(1, 10);
    elevatorMaster.configPeakOutputReverse(-1, 10);
    elevatorMaster.configMotionCruiseVelocity(1300, 10);
    elevatorMaster.configMotionAcceleration(1000, 10);
    elevatorMaster.setNeutralMode(NeutralMode.Brake);
    elevatorMaster.setInverted(true);
    elevatorMaster.setSensorPhase(true);

    elevatorSlave = new WPI_VictorSPX(6);
    elevatorSlave.follow(elevatorMaster);
    elevatorSlave.setInverted(true);

    System.out.println("ELEVATOR ... WE ARE GO.");
    //ELEVATOR END

    //WRIST START
    wrist = new WPI_TalonSRX(7);
    wrist.configFactoryDefault();
    wrist.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative, 0, 10);
    wrist.selectProfileSlot(0, 0);
    wrist.config_kP(0, 1, 10);
    wrist.config_kI(0, 0, 10);
    wrist.config_kD(0, 0, 10);
    wrist.config_kF(0, 0, 10);
    wrist.config_IntegralZone(0, 10, 10);
    wrist.configPeakOutputForward(1, 10);
    wrist.configPeakOutputReverse(-1, 10);
    wrist.configMotionCruiseVelocity(1500, 10);
    wrist.configMotionAcceleration(1000, 10);
    wrist.setNeutralMode(NeutralMode.Brake);

    System.out.println("WRIST ... GO FLIGHT.");
    //WRIST END

    //INTAKE START
    intake = new WPI_VictorSPX(8);
    intake.configFactoryDefault();
    intake.setNeutralMode(NeutralMode.Brake);
    intake2 = new WPI_VictorSPX(9);
    intake2.configFactoryDefault();
    intake2.setNeutralMode(NeutralMode.Brake);
    hatchClamp = new DoubleSolenoid(0, 0, 1);
    hatchExtend = new DoubleSolenoid(0, 2, 3);

    System.out.println("INTAKE ...  WE'RE GO, FLIGHT.");
    //INTAKE END

    //LED START
    led = new Spark(0);
    //LED END
  }
}
