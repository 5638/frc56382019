/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.DoubleSolenoid.Value;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.buttons.JoystickButton;
import frc.robot.commands.Ball;
import frc.robot.commands.ElevatorCom;
import frc.robot.commands.Hatch;
import frc.robot.commands.Shift;
import frc.robot.commands.VisionCom;
import frc.robot.commands.WristCom;
import frc.robot.Equations;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

  public Joystick xbox;

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
  public JoystickButton clampHatch;
  public JoystickButton releaseHatch;
  //INTAKE END


  public OI(){
    xbox = new Joystick(0);

    //DRIVE START
    vision = new JoystickButton(xbox, 1);
    vision.whileHeld(new VisionCom());

    hi = new JoystickButton(xbox, 10);
    hi.toggleWhenPressed(new Shift(Value.kForward));

    lo = new JoystickButton(xbox, 10);
    lo.toggleWhenPressed(new Shift(Value.kReverse));
    //DRIVE END

    //ELEVATOR START
    elevatorLevel0 = new JoystickButton(xbox, 2);
    elevatorLevel0.whenPressed(new ElevatorCom(0));
    
    elevatorLevel1 = new JoystickButton(xbox, 3);
    elevatorLevel1.whenPressed(new ElevatorCom(1000));
    //ELEVATOR END

    //INTAKE START
    intakeBall = new JoystickButton(xbox, 4);
    intakeBall.whileHeld(new Ball(-.5));

    outakeBall = new JoystickButton(xbox, 5);
    outakeBall.whileHeld(new Ball(.5));

    clampHatch = new JoystickButton(xbox, 6);
    clampHatch.whenPressed(new Hatch(Value.kForward));

    releaseHatch = new JoystickButton(xbox, 7);
    releaseHatch.whenPressed(new Hatch(Value.kReverse));
    //INTAKE END

    //WRIST START
    wrist0 = new JoystickButton(xbox, 8);
    wrist0.whenPressed(new WristCom(Equations.degreesToRotation(0)));

    wrist90 = new JoystickButton(xbox, 9);
    wrist90.whenPressed(new WristCom(Equations.degreesToRotation(90)));
    //WRIST END
  }

}
