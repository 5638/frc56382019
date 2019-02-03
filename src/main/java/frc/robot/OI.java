package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Ball;
import frc.robot.commands.ElevatorCom;
import frc.robot.commands.ElevatorManual;
import frc.robot.commands.Hatch;
import frc.robot.commands.Shift;
import frc.robot.commands.VisionCom;
import frc.robot.commands.WristCom;
import frc.robot.commands.WristManual;

public class OI {

  public Joystick xbox;
  public Joystick buttonBoard;

  //DRIVE START
  public JoystickButton vision;
  public JoystickButton hi;
  public JoystickButton lo;
  //DRIVE END

  //ELEVATOR START
  public JoystickButton elevatorLevel0;
  public JoystickButton elevatorLevel1;
  //ELEVATOR END

  //WRIST START
  public JoystickButton wrist0;
  public JoystickButton wrist90;
  //WRIST END

  //INTAKE START
  public JoystickButton intakeBall;
  public JoystickButton outakeBall;
  public JoystickButton retractHatch;
  public JoystickButton clampHatch;
  public JoystickButton releaseHatch;
  //INTAKE END

  //TEST MODE 
  public JoystickButton elevatorManualUp;
  public JoystickButton elevatorManualDo;

  public JoystickButton wristManualUp;
  public JoystickButton wristManualDo;
  //TEST MODE END


  public OI(){
    xbox = new Joystick(0);
    buttonBoard = new Joystick(1);

    //DRIVE START
    vision = new JoystickButton(xbox, 1);
    vision.whileHeld(new VisionCom());

    hi = new JoystickButton(xbox, 2);
    hi.toggleWhenPressed(new Shift(Value.kForward));

    lo = new JoystickButton(xbox, 2);
    lo.toggleWhenPressed(new Shift(Value.kReverse));
    //DRIVE END

    //ELEVATOR START
    elevatorLevel0 = new JoystickButton(buttonBoard, 1);
    elevatorLevel0.whenPressed(new ElevatorCom(0));
    
    elevatorLevel1 = new JoystickButton(buttonBoard, 2);
    elevatorLevel1.whenPressed(new ElevatorCom(1000));
    //ELEVATOR END

    //INTAKE START
    intakeBall = new JoystickButton(xbox, 4);
    intakeBall.whileHeld(new Ball(-.5, -.5));

    outakeBall = new JoystickButton(xbox, 5);
    outakeBall.whileHeld(new Ball(.5, .5));

    retractHatch = new JoystickButton(xbox, 14);
    retractHatch.whenPressed(new Hatch(Value.kReverse, Value.kReverse));

    clampHatch = new JoystickButton(xbox, 6);
    clampHatch.whenPressed(new Hatch(Value.kForward, Value.kForward));

    releaseHatch = new JoystickButton(xbox, 7);
    releaseHatch.whenPressed(new Hatch(Value.kReverse, Value.kForward));
    //INTAKE END

    //WRIST START
    wrist0 = new JoystickButton(xbox, 8);
    wrist0.whenPressed(new WristCom(Equations.degreesToRotation(0)));

    wrist90 = new JoystickButton(xbox, 9);
    wrist90.whenPressed(new WristCom(Equations.degreesToRotation(90)));
    //WRIST END



    //TEST MODE
    elevatorManualUp = new JoystickButton(xbox, 10);
    elevatorManualUp.whileHeld(new ElevatorManual(1));

    elevatorManualDo = new JoystickButton(xbox, 11);
    elevatorManualDo.whileHeld(new ElevatorManual(-1));

    wristManualUp = new JoystickButton(xbox, 12);
    wristManualUp.whileHeld(new WristManual(1));

    wristManualDo = new JoystickButton(xbox, 13);
    wristManualDo.whileHeld(new WristManual(-1));
    //TEST MODE END
  }

}
