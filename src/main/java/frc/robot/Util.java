package frc.robot;

public class Util {

    //不感帯処理
    public static double deadbandProcessing(double value) {
        return Math.abs(value) > Const.Deadband ? value : 0 ;
    }

}
