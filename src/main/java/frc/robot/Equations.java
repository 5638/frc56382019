package frc.robot;

public class Equations {

    public double ticksToRotation(double Rotations) {
        return Rotations*4096;  //1 rotation of Mag Encoder is 4096 ticks
                                //this means that if you want 1 rot, this will transfer it to ticks. (4096)
                                //2 rots will be 8192 ticks, and .5 rots will be 2048 ticks and so on...
    }

    //public double rotationToHeight(double Height){
    //    return ;
    //}

    public static double degreesToRotation(double Degrees){  
        return (Degrees*4096)/360;    //same as above but divide by 360 to get degrees
                                        //1 rot is 360 (4096 ticks) degrees so (360*4096)/360 = 4096
                                        //.25 rot should be 90 (1024 ticks) degrees so (90*4096)/360 = 1024
                                        //...there is a method to my madness.
    }
}
