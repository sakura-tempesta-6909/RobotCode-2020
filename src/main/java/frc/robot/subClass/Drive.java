package frc.robot.subClass;

import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends DifferentialDrive {

    public Drive(SpeedController leftMotor, SpeedController rightMotor) {
        super(leftMotor, rightMotor);
    }

    public void applyState(State state) {

        switch (state.driveState) {
            //パネル回転時の速さ
            case kSuperLow -> setSpeed(state.driveStraightSpeed * 0.28, state.driveRotateSpeed * 0.7);
            //パネル回転の調整時の速さ
            case kMiddleLow -> setSpeed(state.driveStraightSpeed * 0.4, state.driveRotateSpeed * 0.6);
            //クライム・シュートモード時の速さ
            case kLow -> setSpeed(state.driveStraightSpeed * 0.6, state.driveRotateSpeed * 0.6);
            //ドライブモード時の速さ
            case kManual -> setSpeed(state.driveStraightSpeed, state.driveRotateSpeed);
            case kStop -> stopMotor();
        }
    }

    public void setSpeed(double straightSpeed, double rotateSpeed) {
        arcadeDrive(straightSpeed, rotateSpeed);
    }

}