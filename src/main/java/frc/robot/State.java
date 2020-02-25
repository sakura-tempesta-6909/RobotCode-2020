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

    public DriveState driveState;
    public double driveStraightSpeed, driveRotateSpeed;  //Driveの速度;
    public double shooterLeftSpeed,shooterRightSpeed,shooterPIDSpeed;
    public ShooterState shooterState;
    public IntakeState intakeState;
    public IntakeBeltState intakeBeltState;

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


    }

}