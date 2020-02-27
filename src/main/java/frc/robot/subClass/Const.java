package frc.robot.subClass;

public class Const {

    //ControllerPort(コントローラーのポート)
    public static final int JoystickPort = 0;
    public static final int DriveControllerPort = 0;
    public static final int OperateControllerPort = 1;

    public static final double deadband =0.2;


    //Drive-motor-port
    public static final int DriveRightFrontPort = 2;
    public static final int DriveRightBackPort = 12;
    public static final int DriveLeftFrontPort = 6;
    public static final int DriveLeftBackPort = 13;

    //Arm - Motor-port & Encoder - port
    public static final int CanonMotorPort = 3;


    public static final int IntakeMotorPort = 14;
    public static final int IntakeBeltSensorFrontPort  = 0;
    public static final int IntakeBeltSensorBackPort = 1;

    public static final int HangingMotorPort = 7;


    //Drive
    public static final double DriveFullSpeedTime = 0.5;

    //Seosor


    //その他
    public static final double Deadband = 0.2;


    //Shooter
    public static final int shooterLeftMotor = 4;
    public static final int shooterRightMotor = 5;
    public static final int shooterMotorMaxOutput = 100000;
    public static final int kSlotIdx = 0;
    public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
    public final static Gains kGains_Velocit = new Gains( 0.01, 0.000005, 0, 1023.0/7200.0,  300,  1.00,20000000);
    public final static double shooterOutTakeSpeed = -0.2;
    public final static double shooterIntakeSpeed = 0.18;


    //Intake
    public final static double intakeSpeed = 0.8;
    public final static double outtakeSpeed = -0.6;
    public final static int intakeBeltFrontMotor = 11;
    public final static int intakeBeltBackMotor = 15;


    //Climb
    public static final double climbMotorAdvanceSpeed = 0.30;
    public static final double canonMotorAdvanceSpeed = 0.15;
    public static final double climbMotorShrinkSpeed = -0.30;
    public static final double canonMotorShrinkSpeed = -0.15;
    public static final double unLockAngle = 30;
    public static final double lockAngle = 0;
    public static final double slideMotorRight = 0.30;
    public static final double slideMotorLeft = -0.30;


    //ARM
    public static final int armMotor = 3;
    public static final double armMBasicSpeed_P = 0.05;
    public static final double armBasicSpeed_M = -0.05;
    //スティックの傾きに対するモーターの速さの倍率
    public static final double armMagni = 0.005;
    //アームの可動域の角度＆エンコーダーからの値の最大
    public static final double armMaxAngle = 135;
    public static final double armMaxPoint = 383;

    public static final double shooterPanelSpeed = 0.2;
}
