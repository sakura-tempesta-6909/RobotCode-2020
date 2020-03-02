package frc.robot.subClass;

public class State {


    public double driveStraightSpeed, driveRotateSpeed;  //Driveの速度;
    public double shooterLeftSpeed, shooterRightSpeed, shooterPIDSpeed;
    public double hangingMotorSpeed;
    public double armMotorSpeed;
    public double armAngle;
    public double setArmAngle;
    public double climbSlideMotorSpeed;
    public double panelManualSpeed;
    public double shooterAngle;
    public double armRotateSpeed;
    public ShooterState shooterState;
    public IntakeState intakeState;
    public IntakeBeltState intakeBeltState;
    public DriveState driveState;
    public ClimbState climbState;
    public ArmState armState;
    public ControlState controlState;
    public PanelState panelState;
    public boolean armPID_ON;
    
    //ボールを5個ゲットしたか
    public boolean is_IntakeFull;

    public State() {
        stateInit();
    }

    public void stateInit() {

        //DriveのStateを初期化
        driveState = DriveState.kManual;
        driveRotateSpeed = 0;

        //Shooter
        shooterState = ShooterState.doNothing;
        shooterLeftSpeed = 0;
        shooterRightSpeed = 0;
        shooterPIDSpeed = 0;

        //Intake
        intakeState = IntakeState.doNothing;
        is_IntakeFull = false;

        //IntakeBeltState
        intakeBeltState = IntakeBeltState.doNothing;

        //Climb
        climbState = ClimbState.doNothing;
        hangingMotorSpeed = 0;
        armMotorSpeed = 0;
        armAngle = 0;
        climbSlideMotorSpeed = 0;

        //Arm
        armState = ArmState.k_Basic;
        armMotorSpeed = 0;
        armPID_ON = false;
        setArmAngle = Const.armMinAngle;

        panelState = PanelState.p_DoNothing;
        //ControlMode

        // controlState = ControlState.m_Drive;



    }

    public void changeState() {

        //DriveのStateを初期化
        driveState = DriveState.kManual;
        driveRotateSpeed = 0;

        //Shooter
        shooterState = ShooterState.doNothing;
        shooterLeftSpeed = 0;
        shooterRightSpeed = 0;
        shooterPIDSpeed = 0;

        //Intake
        intakeState = IntakeState.doNothing;

        //IntakeBeltState
        intakeBeltState = IntakeBeltState.doNothing;
        //Climb
        climbState = ClimbState.doNothing;
        hangingMotorSpeed = 0;
        armMotorSpeed = 0;
        armAngle = 0;
        climbSlideMotorSpeed = 0;
        armState = ArmState.k_Basic;
        panelState = PanelState.p_DoNothing;


    }
    public enum ControlState {
        m_ShootingBall, m_PanelRotation, m_Climb, m_Drive

    }
    public enum DriveState {
        kManual,
        kLow,
        kdoNothing
    }
    public enum ShooterState {
        kshoot,
        kintake,
        kmanual,
        doNothing,
        kouttake
    }
    public enum IntakeState {
        kIntake,
        kOuttake,
        doNothing
    }
    public enum IntakeBeltState {
        kIntake,
        kOuttake,
        doNothing
    }

    public enum ClimbState {
        doNothing,
        climbExtend,
        climbShrink,
        climbLock,
        climbRightSlide,
        climbLeftSlide
    }

    public enum ArmState {
        k_Conserve,
        k_Shoot,
        k_Panel,
        k_Adjust,
        k_Parallel,
        k_Basic,
        k_Manual,
        k_DoNothing
    }

    public enum PanelState {
        p_DoNothing,        //stop
        p_ManualRot,        //手動
        p_toBlue,           //色合わせ(大会のパネル側のセンサーの色なのでカラーセンサーが読み取るのは二つずれた値。青<->赤、黄<->緑）
        p_toYellow,
        p_toRed,
        p_toGreen

    }

}