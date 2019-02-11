package frc.robot;

import com.ctre.phoenix.ErrorCode;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class TalonChecker {

    public static ErrorCode m_errorCode;

    public static void checkError(String className, ErrorCode errorCode, String message, Boolean smartDashboard){
        m_errorCode = errorCode;
        if(errorCode != ErrorCode.OK){
            DriverStation.reportError(message + errorCode, false);
            smartDashboard = SmartDashboard.putBoolean(className + " Encoder", false);
        }else{
            smartDashboard = SmartDashboard.putBoolean(className + " Encoder", true);
        }
    }

    public static boolean getTrueFalse(){
        if(m_errorCode == ErrorCode.OK){
            return true;
        }else{
            return false;
        }
    }
}
