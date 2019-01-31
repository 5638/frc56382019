package frc.robot;

import com.ctre.phoenix.ErrorCode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonChecker {
    public static void checkError(String className, ErrorCode errorCode, String message, Boolean smartDashboard){
        if(errorCode != ErrorCode.OK){
            DriverStation.reportError(message + errorCode, false);
        }else{
            SmartDashboard.putBoolean(className + " Encoder", true);
        }
    }
}
