package frc.robot;

public class Const {

    //ControllerPort(コントローラーのポート)
    public static final int JoystickPort = 0;
    public static final int DriveControllerPort = 0;
    public static final int OperateControllerPort = 1;


    //Drive-motor-port
    public static final int DriveRightFrontPort = 2;
    public static final int DriveRightBackPort = 12;
    public static final int DriveLeftFrontPort = 6;
    public static final int DriveLeftBackPort = 13;

    //Arm - Motor-port & Encoder - port
    public static final int CanonMotorPort = 3;
    //public static final int CanonEncoderPort_A = 0;
    //public static final int CanonEncoderPort_B = 0;


    public static final int IntakeMotorPort = 14;

    public static final int BeltMotorFrontPort = 11;
    public static final int BeltMotorBackPort  = 15;

    public static final int HangingMotorPort = 7;
    //public static final int HangingEncoderPort_A = 0;
    //public static final int HangingEncoderPort_B = 0;

    //microSwitch-port
    public static final int MaxUpSwitchPort   = 0;
    public static final int MaxDownSwitchPort = 0;


    //Drive
    public static final double DriveFullSpeedTime = 0.5;

    //Seosor


    //その他
    public static final double Deadband = 0.2;


    //Shooter
    public static final int shooterMotorMaxOutput = 100000;
    public static final int kSlotIdx = 0;
    public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
    public final static Gains kGains_Velocit = new Gains( 0.01, 0.01, 0, 1023.0/7200.0,  300,  1.00,20000000);
    public final static double shooterOutTakeSpeed = 0.4;
    public final static double shooterIntakeSpeed = 0.4;
}