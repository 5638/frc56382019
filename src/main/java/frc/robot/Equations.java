package frc.robot;

public class Equations {

    public double ticksToRotation(double Rotations) {
        return Rotations*4096;  //1 rotation of Mag Encoder is 4096 ticks
                                //this means that if you want 1 rot, this will transfer it to ticks. (4096)
                                //2 rots will be 8192 ticks, and .5 rots will be 2048 ticks and so on...
    }

    public static double rotationToHeight(double heightInInches){

        double C = 2 * Math.PI * 0.895; // ~ 5.62"

        double rotationsPerInch = 4096/C; // ~ 729

        return rotationsPerInch * heightInInches;    
                    //C = 2*pi*r
                    //pitch dia = 1.79"
                    //C = 4096 enc ticks
                    //C = Elevator height after 1 rot
                    /*if we want 7 5.62 inches
                    then 729 * 5.62 ~ 4096 ticks

                    if we want 10 inches
                    then 729 * 10 ~ 7290 ticks
                    */
    }

    public static double heightToInches(double EncoderCount){
        double C = 2 * Math.PI * 0.895; // ~ 5.62"
        double rotationsPerInch = EncoderCount/C; // ~ 729

        return rotationsPerInch;
    }

    public static double degreesToRotation(double Degrees){  
        return (Degrees*4096)/360;    //same as above but divide by 360 to get degrees
                                        //1 rot is 360 (4096 ticks) degrees so (360*4096)/360 = 4096
                                        //.25 rot should be 90 (1024 ticks) degrees so (90*4096)/360 = 1024
                                        //...there is a method to my madness.
    }
}
