package frc.robot.subClass;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.util.Color;

public class Util {

    ColorCode colorOutput = ColorCode.inRange;

    //不感帯処理
    public static double deadbandProcessing(double value) {
        return Math.abs(value) > Const.Deadband ? value : 0;
    }

    public static boolean deadbandCheck(double value) {
        return Math.abs(value) > Const.Deadband;
    }

    //カラーセンサー

    public static void sendConsole(String key, String text) {
        System.out.println(key + ":" + text);
        SmartDashboard.putString(key, text);
    }

    private static ColorCode selectColorAction(Color DCImport, int p) {
        double r = DCImport.red;
        double g = DCImport.green;
        double b = DCImport.blue;
        if (p < 80) {
            return ColorCode.outOfRange;
        }
        if ((0.2 <= r && r < 0.4) && (0.45 <= g) && (b < 0.2)) {
            return ColorCode.yellow;
        }
        if ((0.3 <= r) && (0.2 <= g && g < 0.48) && (b < 0.3)) {
            return ColorCode.red;
        }
        if ((r < 0.25) && (0.4 <= g) && (0.2 <= b && b < 0.27)) {
            return ColorCode.green;
        }
        if ((r < 0.25) && (0.4 <= g) && (0.27 <= b)) {
            return ColorCode.blue;
        }
        return ColorCode.inRange;
    }

    public enum ColorCode {
        yellow,
        red,
        green,
        blue,
        inRange,
        outOfRange
    }
}
