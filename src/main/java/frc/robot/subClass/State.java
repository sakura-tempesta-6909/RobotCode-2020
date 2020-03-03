package frc.robot.subClass;

import edu.wpi.first.wpilibj.GenericHID;

public class State {


    public double driveStraightSpeed, driveRotateSpeed;  //Driveの速度;
    public double shooterLeftSpeed, shooterRightSpeed, shooterPIDSpeed;
    public double hangingMotorSpeed;
    public double armMotorSpeed;
    public double armAngle;
    public double setArmAngle;
    public double climbSlideMotorSpeed;
    public double panelManualSpeed;
    public ShooterState shooterState;
    public IntakeState intakeState;
    public IntakeBeltState intakeBeltState;
    public DriveState driveState;
    public ClimbState climbState;
    public ArmState armState;
    public ControlMode controlMode = ControlMode.m_Drive;
    public PanelState panelState;
    public boolean armPID_ON;

    public double climbExtendAdjustSpeed;

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
        climbExtendAdjustSpeed = 0;

        //Arm
        armState = ArmState.k_Basic;
        armMotorSpeed = 0;
        armPID_ON = false;
        setArmAngle = Const.armMinAngle;

        //panel
        panelState = PanelState.p_DoNothing;
        panelManualSpeed = 0;
        //ControlMode

        // controlState = ControlState.m_Drive;

    }

    public void changeMode(Controller controller) {
        switch (controlMode) {
            case m_Drive:
                if (controller.operator.getBumper(GenericHID.Hand.kLeft)) {
                    //ボール発射モードへ切り替え
                    controlMode = ControlMode.m_ShootingBall;
                } else if (controller.driver.getBackButton()) {
                    //コントロールパネル回転モードへ切り替え
                    controlMode = ControlMode.m_PanelRotation;
                } else if (controller.operator.getBackButton()) {
                    //クライムモードへ切り替え
                    controlMode = ControlMode.m_Climb;
                }
                break;

            case m_Climb:
            case m_ShootingBall:
            case m_PanelRotation:
                if(controller.driver.getStartButton() || controller.operator.getStartButton()) {
                    controlMode = ControlMode.m_Drive;
                }
                break;
        }
        Util.sendConsole("Mode", controlMode.toString());

    }

    public enum ControlMode {
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
        climbSlide,
        climbMotorOnlyExtend,
        climbMotorOnlyShrink
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