package frc.robot;

public class State {


    public enum DriveState{
        kManual,
        kAuto,
        kTest
    }
    public enum ShooterState{
        kshoot,
        kintake,
        kmanual,
        kouttake
    }

    public DriveState driveState;
    public double driveStraightSpeed, driveRotateSpeed;  //Driveの速度
    public ShooterState shooterState;
    public double shooterLeftSpeed,shooterRightSpeed,shooterPIDSpeed;

    State(){
        stateInit();
    }

    public void stateInit(){

        //DriveのStateを初期化
        driveState = DriveState.kManual;
        driveRotateSpeed = 0;

        //Shooter
        shooterState = ShooterState.kintake;
        shooterLeftSpeed = 0;
        shooterRightSpeed = 0;
        shooterPIDSpeed = 0;


    }

}