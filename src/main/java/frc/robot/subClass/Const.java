package frc.robot.subClass;

public class Const {
    //Deadband
    public static final double Deadband = 0.2;

    //PORTS
    //ControllerPort(コントローラーのポート)
    public static final int JoystickPort = 0;
    public static final int DriveControllerPort = 0;
    public static final int OperateControllerPort = 1;
    //Drive-motor-port
    public static final int DriveRightFrontPort = 2;
    public static final int DriveRightBackPort = 12;
    public static final int DriveLeftFrontPort = 6;
    public static final int DriveLeftBackPort = 13;
    //Arm Port
    public static final int armMotor = 3;
    //Intake Port
    public static final int IntakeMotorPort = 14;
    public static final int IntakeBeltSensorFrontPort = 0;
    public static final int IntakeBeltSensorBackPort = 1;
    //Shooter Port
    public static final int shooterLeftMotor = 5;
    public static final int shooterRightMotor = 4;
    public final static int intakeBeltFrontMotor = 11;
    public final static int intakeBeltBackMotor = 15;
    //Climb Port
    public static final int climbMotorPort = 7;
    public static final int climbServoPort = 9;
    public static final int climbSlideMotor = 16;

    //Drive 加速度制限
    public static final double DriveFullSpeedTime = 0.5;

    //SHOOTER
    //PID
    public static final int shooterMotorMaxOutput = 100000;
    public static final int kSlotIdx = 0;
    public static final int kPIDLoopIdx = 0;
    public static final int kTimeoutMs = 30;
    public final static Gains kGains_ShooterVelocity = new Gains(0.01, 0.000005, 0, 1023.0 / 7200.0, 300, 1.00, 20000000.0);
    //In&Outtake
    public final static double shooterIntakeSpeed = 0.165;
    public final static double shooterOutTakeSpeed = -0.2;

    //INTAKE MECANUM
    public final static double intakeSpeed = 0.7;
    public final static double outtakeSpeed = -0.6;

    //CLIMB
    //Servo Angle for Climb Lock
    public static final double unLockAngle = 30;
    public static final double lockAngle = 0;
    //ArmAngle Range for Climb
    public static final double armParallelAngleRange = 10;
    //Climb Extend Speed
    public static final double climbMotorExtendSpeed = 0.675;
    public static final double climbArmExtendSpeed = 0.21; //Offset is included
    //Climb Shrink Speed
    public static final double climbMotorShrinkSpeed = -0.55;
    //Armは全力脱力タイムズ

    //ARM
    //定速で回すとき
    public static final double ArmLowDownSpeed = -0.05;
    public static final double ArmHighDownSpeed = -0.2;
    public static final double ArmBasicUpSpeed = 0.4;
    public static final double ArmDownBorderAngle = -5;
    //アームの可動域(角度と抵抗の値)
    public static final double armMaxAngle = 80;
    public static final double armMinAngle = -30;
    public static final double armMaxPoint = 500;
    public static final double armMinPoint = 170;
    //for Calculating Offset
    public static final double armAngleDifference = armMaxAngle - armMinAngle;
    public static final double armPointDifference = armMaxPoint - armMinPoint;
    public static final double armMaxOffset = 0.13;
    //PID
    public final static Gains kGains_ArmPosition = new Gains(8, 0.01, 10, 0, (int) (0.5 * 1025 / 8), 1.00, 0.15 * 1023 / 0.01);
    public static final int kArmPIDLoopIdx = 0;

    //目標角度
    public static final double armShootBelowPoint = 492;
    public static final double armShootBelowAngle = (armShootBelowPoint - armMinPoint) * armAngleDifference / armPointDifference + armMinAngle;
    public static final double armParallelAngle = 0;
    public static final double armPanelPoint = 359;
    public static final double armPanelAngle = (armPanelPoint - armMinPoint) * armAngleDifference / armPointDifference + armMinAngle;
    public static final double armShootInitialPoint = 383; //377~390
    public static final double armShootInitialAngle = (armShootInitialPoint - armMinPoint) * armAngleDifference / armPointDifference + armMinAngle;


    //PANEL
    public static final double shooterPanelSpeed = 0.2;



}
