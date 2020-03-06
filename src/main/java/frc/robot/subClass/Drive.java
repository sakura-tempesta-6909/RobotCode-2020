package frc.robot.subClass;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends DifferentialDrive {

    public Drive(SpeedController leftMotor, SpeedController rightMotor) {
        super(leftMotor, rightMotor);
    }

    public void applyState(State state) {

        switch (state.driveState) {
            case kSuperLow:
                //パネル回転モード時の速さ
                setSpeed(state.driveStraightSpeed * 0.28, state.driveRotateSpeed *0.7);
                break;
            case kLow:
                //クライム・シュートモード時の速さ
                setSpeed(state.driveStraightSpeed *0.6, state.driveRotateSpeed *0.7);
                break;
            case kManual:
                //ドライブモード時の速さ
                setSpeed(state.driveStraightSpeed, state.driveRotateSpeed);
                break;
            case kdoNothing:
                setSpeed(0, 0);
                break;
        }
    }

    public void setSpeed(double straightSpeed, double rotateSpeed) {
        arcadeDrive(straightSpeed, rotateSpeed);
    }

}