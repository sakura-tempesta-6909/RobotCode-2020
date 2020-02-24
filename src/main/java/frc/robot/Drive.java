package frc.robot;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.*;

public class Drive extends DifferentialDrive{

  public Drive(SpeedController leftMotor, SpeedController rightMotor) {
    super(leftMotor, rightMotor);
}

    public void apllyState(State state){

        switch(state.driveState){
            case kAuto:

            break;

            case kManual:
            setSpeed(state.driveStraightSpeed, state.driveRotateSpeed);
            break;

            case kTest:

            break;

        }

    }

    public void setSpeed(double straightSpeed,double rotateSpeed) {
    arcadeDrive(straightSpeed, rotateSpeed);
  }

}