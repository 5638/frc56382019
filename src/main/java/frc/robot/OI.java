package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Ball;
import frc.robot.commands.DriveCom;
import frc.robot.commands.ElevatorCom;
import frc.robot.commands.ElevatorManual;
import frc.robot.commands.Hatch;
import frc.robot.commands.PlaceHatch;
import frc.robot.commands.RetrieveHatch;
import frc.robot.commands.Shift;
import frc.robot.commands.VisionCom;
import frc.robot.commands.WristCom;
import frc.robot.commands.WristManual;

public class OI {

  public Joystick xbox;
  public Joystick xbox2;
  public Joystick buttonBoard1;
  public Joystick buttonBoard2;

  //DRIVE START
  public JoystickButton vision;
  public JoystickButton shift;
  //DRIVE END

  //ELEVATOR START
  public JoystickButton elevatorLevel1Cargo;
  public JoystickButton elevatorLevel2Cargo;
  public JoystickButton elevatorLevel3Cargo;
  public JoystickButton elevatorLevel1Hatch;
  public JoystickButton elevatorLevel2Hatch;
  public JoystickButton elevatorLevel3Hatch;
  //ELEVATOR END

  //WRIST START
  public JoystickButton wristPos;
  public JoystickButton wristPosCargo;
  //WRIST END

  //INTAKE START
  public JoystickButton intakeBall;
  public JoystickButton outakeBall;
  public JoystickButton retractHatch;
  public JoystickButton retrieveHatch;
  public JoystickButton placeHatch;
  public JoystickButton extendHatch;
  public JoystickButton preHatchIntake;
  //INTAKE END

  //TEST MODE
  public JoystickButton elevatorManualUp;
  public JoystickButton elevatorManualDo;

  public JoystickButton wristManualUp;
  public JoystickButton wristManualDo;
  //TEST MODE END


  public OI(){
    xbox = new Joystick(0);
    xbox2 = new Joystick(3);
    buttonBoard1 = new Joystick(1);
    buttonBoard2 = new Joystick(2);

    //DRIVE START
    vision = new JoystickButton(xbox, 1);
    vision.whileHeld(new VisionCom());

    shift = new JoystickButton(xbox, 4);
    shift.whileHeld(new DriveCom(Value.kReverse));
    shift.whenReleased(new DriveCom(Value.kForward));
    //DRIVE END

    //ELEVATOR START
    elevatorLevel1Cargo = new JoystickButton(buttonBoard1, 3);
    elevatorLevel1Cargo.whenPressed(new ElevatorCom(0));

    elevatorLevel2Cargo = new JoystickButton(buttonBoard1, 2);
    elevatorLevel2Cargo.whenPressed(new ElevatorCom(9900));

    elevatorLevel3Cargo = new JoystickButton(buttonBoard1, 1);
    elevatorLevel3Cargo.whenPressed(new ElevatorCom(20100));

    elevatorLevel1Hatch = new JoystickButton(buttonBoard1, 6);
    elevatorLevel1Hatch.whenPressed(new ElevatorCom(1700));

    elevatorLevel2Hatch = new JoystickButton(buttonBoard1, 5);
    elevatorLevel2Hatch.whenPressed(new ElevatorCom(12000));

    elevatorLevel3Hatch = new JoystickButton(buttonBoard1, 4);
    elevatorLevel3Hatch.whenPressed(new ElevatorCom(21100));
    //ELEVATOR END

    //INTAKE START
    intakeBall = new JoystickButton(xbox, 5);
    intakeBall.whileHeld(new Ball(-1, -1));

    outakeBall = new JoystickButton(xbox, 6);
    outakeBall.whileHeld(new Ball(1, 1));

    retractHatch = new JoystickButton(buttonBoard1, 8);
    retractHatch.whenPressed(new Hatch(Value.kReverse, Value.kReverse));

    extendHatch = new JoystickButton(buttonBoard1, 9);
    extendHatch.whenPressed(new Hatch(Value.kForward, Value.kForward));

    retrieveHatch = new JoystickButton(xbox, 2);
    retrieveHatch.whenPressed(new RetrieveHatch());

    placeHatch = new JoystickButton(xbox, 3);
    placeHatch.whenPressed(new PlaceHatch());

    preHatchIntake = new JoystickButton(buttonBoard2, 6);
    preHatchIntake.whenPressed(new Hatch(Value.kReverse, Value.kForward));
    //INTAKE END

    //WRIST START
    wristPos = new JoystickButton(xbox, 5);
    wristPos.whileHeld(new WristCom(-8500));
    wristPos.whenReleased(new WristCom(-300));
    placeHatch.whenReleased(new WristCom(-300));

    wristPosCargo = new JoystickButton(xbox, 9); //LEFT STICK
    wristPosCargo.whenPressed(new WristCom(-3000));
    //WRIST END



    //TEST MODE
    /*
    elevatorManualUp = new JoystickButton(xbox2, 10);
    elevatorManualUp.whileHeld(new ElevatorManual());

    elevatorManualDo = new JoystickButton(xbox2, 11);
    elevatorManualDo.whileHeld(new ElevatorManual());

    wristManualUp = new JoystickButton(xbox2, 12);
    wristManualUp.whileHeld(new WristManual(.25));

    wristManualDo = new JoystickButton(xbox2, 13);
    wristManualDo.whileHeld(new WristManual(-.25));
    */
    //TEST MODE END
  }

}
