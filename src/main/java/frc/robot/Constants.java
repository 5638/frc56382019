/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

/**
 * Add your docs here.
 */
public class Constants {

    static double position = RobotMap.elevatorMaster.getSelectedSensorPosition();
    static double setpoint;

    public static double hatchPlace(){
        if(position < 2500){
            setpoint = 2000;
        }else if(position > 11000 && position < 13000){
            setpoint = 11700;
        }else if(position > 20100 && position < 22100){
            setpoint = 20800;
        }
        return setpoint;
    }
}
