package frc.robot;

public class State {


    public enum DriveState{
        kManual,
        kAuto,
        kdoNothing
    }
    public enum ShooterState{
        kshoot,
        kintake,
        kmanual,
        doNothing,
        kouttake
    }
    public enum  IntakeState{
        kIntake,
        kouttake,
        doNothing
    }

    public enum IntakeBeltState{
        kIntake,
        kouttake,
        doNothing
    }

    public enum ClimbState{
        doNothing,
        climbExtend,
        climbShrink,
        climbLock,
        climbRightSlide,
        climbLeftSlide
    }

    public enum ArmState{
        k_Basic,           //基本状態（最も下を向いている）
        k_Aaiming,         //砲台の照準を合わせている状態
        k_Maxup            //最も上を向いている状態
    }


    public double driveStraightSpeed, driveRotateSpeed;  //Driveの速度;
    public double shooterLeftSpeed,shooterRightSpeed,shooterPIDSpeed;
    public double hangingMotorSpeed;
    public double canonMotorSpeed;
    public double hangingServoAngle;
    public double climbSlideMotorSpeed;

    public ShooterState shooterState;
    public IntakeState intakeState;
    public IntakeBeltState intakeBeltState;
    public DriveState driveState;
    public ClimbState climbState;
    public ArmState armState;

    State(){
        stateInit();
    }

    public void stateInit(){

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
        canonMotorSpeed = 0;
        hangingServoAngle = 0;
        climbSlideMotorSpeed = 0;

        //Arm
        armState = ArmState.k_Basic;


    }

}