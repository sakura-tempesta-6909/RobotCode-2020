/**
 * Class that organizes gains used when assigning values to slots
 */
package frc.robot.subClass;

public class Gains {
    public final double kP;
    public final double kI;
    public final double kD;
    public final double kF;
    public final int kIzone;
    public final double kPeakOutput;
    public final double MaxIntegralAccumulator;

    public Gains(double _kP, double _kI, double _kD, double _kF, int _kIzone, double _kPeakOutput, Double _MaxIntegralAccumulator) {
        kP = _kP;
        kI = _kI;
        kD = _kD;
        kF = _kF;
        kIzone = _kIzone;
        kPeakOutput = _kPeakOutput;
        MaxIntegralAccumulator = _MaxIntegralAccumulator;
    }
}
