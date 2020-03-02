package frc.robot.subClass;

public class Const {

    //ControllerPort(コントローラーのポート)
    public static final int JoystickPort = 0;
    public static final int DriveControllerPort = 0;
    public static final int OperateControllerPort = 1;

    public static final double deadband = 0.2;


    //Drive-motor-port
    public static final int DriveRightFrontPort = 2;
    public static final int DriveRightBackPort = 12;
    public static final int DriveLeftFrontPort = 6;
    public static final int DriveLeftBackPort = 13;

    //Arm - Motor-port & Encoder - port
    public static final int ArmMotorPort = 3;


    public static final int IntakeMotorPort = 14;
    public static final int IntakeBeltSensorFrontPort = 0;
    public static final int IntakeBeltSensorBackPort = 1;

    public static final int HangingMotorPort = 7;


    //Drive
    public static final double DriveFullSpeedTime = 0.5;

    //Sensor


    //その他
    public static final double Deadband = 0.2;


    //Shooter
    public static final int shooterLeftMotor = 5;
    public static final int shooterRightMotor = 4;
    public static final int shooterMotorMaxOutput = 100000;
    public static final int kSlotIdx = 0;
    public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
    public final static Gains kGains_Velocit = new Gains(0.01, 0.000005, 0, 1023.0 / 7200.0, 300, 1.00, 20000000.0);
    public final static double shooterOutTakeSpeed = -0.2;
    public final static double shooterIntakeSpeed = 0.1575;


    //Intake
    public final static double intakeSpeed = 0.7;
    public final static double outtakeSpeed = -0.6;
    public final static int intakeBeltFrontMotor = 11;
    public final static int intakeBeltBackMotor = 15;


    //Climb
    public static final int climbMotorPort = 7;
    public static final int climbServoPort = 9;
    public static final int climbSlideMotor = 16;
    public static final double climbMotorExtendSpeed = 0.6725;
    public static final double climbMotorShrinkSpeed = -0.20;
    public static final double armMotorShrinkSpeed = 0;
    public static final double unLockAngle = 30;
    public static final double lockAngle = 0;
    public static final double slideMotorRight = 0.30;
    public static final double slideMotorLeft = -0.30;

    //Offset is included
    public static final double climbArmExtendSpeed = 0.2075;
    public static final double climbArmShrinkSpeed = -0.1;
    public static final double armParallelAngleRange = 10;


    //ARM
    public static final int armMotor = 3;
    //定速で回すとき
    public static final double ArmLowDownSpeed = -0.05;
    public static final double ArmHighDownSpeed = -0.2;
    public static final double ArmBasicUpSpeed = 0.4;
    public static final double ArmDownBorderAngle = -5;
    //スティックの傾きに対するモーターの速さの倍率
    public static final double armMagnification = 0.1;
    //アームの可動域の角度＆エンコーダーからの値の最大
    public static final double armMaxAngle = 80;
    public static final double armMinAngle = -30;
    public static final double armMaxPoint = 500;
    public static final double armMinPoint = 166;

    public static final double armAngleDifference = armMaxAngle - armMinAngle;
    public static final double armPointDifference = armMaxPoint - armMinPoint;

    public static final double armMaxOffset = 0.13;

    public final static Gains kGains_ArmPosition = new Gains(8, 0.01, 10, 0, (int) (0.5 * 1025 / 8), 1.00, 0.15 * 1023 / 0.01);
    public static final int kArmPIDLoopIdx = 0;

    //目標角度（現在不明）
    public static final double armShootAngle = 30;
    public static final double armParallelAngle = 0;
    public static final double armPanelAngle = 0;


    public static final double shooterPanelSpeed = 0.2;

}
