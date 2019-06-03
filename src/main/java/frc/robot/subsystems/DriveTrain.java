package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.command.WaitCommand;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.Equations;
import frc.robot.RobotMap;
import frc.robot.commands.DriveCom;
import jaci.pathfinder.Pathfinder;
import jaci.pathfinder.Trajectory;
import jaci.pathfinder.Waypoint;
import jaci.pathfinder.modifiers.TankModifier;

public class DriveTrain extends Subsystem {
  double  kp = .1, 
          ki = 0, 
          kd = 0;

  private final DifferentialDrive drive = RobotMap.drive;
  private final DoubleSolenoid shift = RobotMap.shift;
  private final WPI_TalonSRX right = RobotMap.rightMaster;
  private final WPI_TalonSRX left = RobotMap.leftMaster;
  private final WPI_TalonSRX elevator = RobotMap.elevatorMaster;

  public Trajectory leftTraj;
  public Trajectory rightTraj;
  public Trajectory trajectory;

  private static final double maxElevatorHeight = 21100;

  private double integral, previous_error = 0;

  public boolean hasShifted = false;

  public double prev_time = 0;

  @Override
  public void initDefaultCommand() {
    setDefaultCommand(new DriveCom(Value.kForward));
  }

  private double getElevatorHeight(){
    return elevator.getSelectedSensorPosition(0);
  }

  /*public double getRampRate() {
    TalonSRXConfiguration config = new TalonSRXConfiguration();
    right.getAllConfigs(config);
    return config.closedloopRamp;
  }*/

  public void drive(Joystick xbox){
    drive.arcadeDrive((xbox.getRawAxis(2) - xbox.getRawAxis(3)), -xbox.getRawAxis(0));

    double rampSpeed = (2 * (getElevatorHeight()/maxElevatorHeight)) + 1;
    //SmartDashboard.putNumber("DT Ramp Speed", getRampRate());
    SmartDashboard.putNumber("DT Velocity", getAverageVelocity());
    SmartDashboard.putNumber("DT Acceleration", getAverageAcceleration());
    right.configOpenloopRamp(rampSpeed, 10);
    left.configOpenloopRamp(rampSpeed, 10);
  }

  public void stop(){
    drive.arcadeDrive(0, 0);
  }

  public void vision(Joystick xbox){
    double ta0, ta1, tx0, tx1, error;

    ta0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta0").getDouble(0);
    ta1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("ta1").getDouble(0);
    tx0 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx0").getDouble(0);
    tx1 = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx1").getDouble(0);
    error = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);

    //FOR GETTING PERPENDICULAR TO TARGET
    /*if(ta0 < ta1 - (ta1 * 0.05)){
      error = error + tx0;
    }else if(ta1 < ta0 - (ta0 * 0.05)){
      error = error + tx1;
    }else{
      error = NetworkTableInstance.getDefault().getTable("limelight").getEntry("tx").getDouble(0);
    }
    */
    //END
    
    integral += (error * .02);
    double derivative = (error - previous_error) / 0.02;
    previous_error = error;

    double visionOutput = (kp*error) + (ki * integral) + (kd * derivative);

    drive.setSafetyEnabled(false);
    drive.arcadeDrive((xbox.getRawAxis(2) - xbox.getRawAxis(3)), -visionOutput);
    System.out.println("Final Motor Output: " + visionOutput);
  }

  //AUTOSHIFT START

  public boolean canShift(){
    boolean canShift = false;

    if(hasShifted == true){
      //.02 seconds per loop... .5 second timer... 25 loops for .5 sec
      for(int time = 0; time < 25; time++){
        if(time == 25){
          canShift = true;
          hasShifted = false;
        }
      }
    }
    return canShift;
  }

  public int getAverageVelocity(){
	  return (right.getSelectedSensorVelocity(0) + left.getSelectedSensorVelocity(0)) / 2;
  }

  public double getAverageAcceleration(){
    return getAverageVelocity() / 0.02;
  }

  public Value setHigh(){
    return Value.kForward;
  }

  public Value setLow(){
    return Value.kReverse;
  }

  public boolean isHigh(){
    return shift.get() == Value.kForward;
  }

  public boolean isLow(){
    return shift.get() == Value.kReverse;
  }

  public boolean wantsHigh(){
    if(getAverageVelocity() > 4000 || getAverageAcceleration() < 2000 && getElevatorHeight() < 10000){
      return true;
    }else{
      return false;
    }
  }

  public boolean wantsLow(){
    if(getAverageVelocity() < 3000 || getAverageAcceleration() > 3000 && getElevatorHeight() < 10000){
      return true;
    }else{
      return false;
    }
  }

  public void shift(Value value){
    shift.set(value);
/*
    if(wantsHigh() && isLow() && canShift()){
      shift.set(setHigh());
      hasShifted = true;
    }else if(wantsLow() && isHigh() && canShift()){
      shift.set(setLow());
      hasShifted = true;
    }
    */
  }


  public void genPath(){
    Trajectory.Config config = new Trajectory.Config(Trajectory.FitMethod.HERMITE_CUBIC, Trajectory.Config.SAMPLES_HIGH, 0.05, 5.2, 10, 30);
        Waypoint[] points = new Waypoint[] {
                new Waypoint(-4, -1, Pathfinder.d2r(-45)),
                new Waypoint(-2, -2, 0),
                new Waypoint(0, 0, 0)
      };

    trajectory = Pathfinder.generate(points, config);

    // Wheelbase Width = 0.5m
    TankModifier modifier = new TankModifier(trajectory).modify(0.5);

    // Do something with the new Trajectories...
    leftTraj = modifier.getLeftTrajectory();
    rightTraj = modifier.getRightTrajectory();
  }

  public void followPath() {
    for (int i = 0; i < leftTraj.length(); i++) {
      Trajectory.Segment leftSeg = leftTraj.get(i);
      Trajectory.Segment rightSeg = rightTraj.get(i);

      double leftvel = Equations.metersPerSecond_to_unitsPerSecond(leftSeg.velocity);
      double rightvel = Equations.metersPerSecond_to_unitsPerSecond(rightSeg.velocity);

      left.set(ControlMode.Velocity, leftvel);
      right.set(ControlMode.Velocity, rightvel);
      
      System.out.printf("%f,%f,%f,%f,%f,%f,%f,%f\n", 
          leftSeg.dt, leftSeg.x, leftSeg.y, leftSeg.position, leftSeg.velocity, 
          leftSeg.acceleration, leftSeg.jerk, leftSeg.heading);

      double cur_time = Timer.getFPGATimestamp();
      double d_t = cur_time - prev_time;
      prev_time = cur_time;
      System.out.println("TIME: "+d_t);
      System.out.println("VEL: "+left.getClosedLoopTarget()+" "+right.getClosedLoopTarget());
      Timer.delay(.05);
    }
  }
}


