package frc.robot;

public class State {

    public enum DriveState{
        kManual,
        kAuto,
        kTest
    }

    public DriveState driveState;
    public double driveStraightSpeed, driveRotateSpeed;  //Driveの速度
    public double driveStraightSetpoint, driveRotateSetpoint;    // PID制御の目標値
    public boolean is_drivePIDOn;    // PID制御するかどうか

    State(){
        stateInit();
    }

    public void stateInit(){

        //DriveのStateを初期化
        driveState = DriveState.kManual;
        driveStraightSetpoint = 0;
        driveRotateSpeed = 0;
        is_drivePIDOn = false;

    }

}