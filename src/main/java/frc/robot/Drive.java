package frc.robot;


import edu.wpi.first.wpilibj.ADXRS450_Gyro;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;

public class Drive extends DifferentialDrive{

/*    public Drive(SpeedController leftMotor, SpeedController rightMotor, Encoder drive_left, Encoder drive_right, ADXRS450_Gyro g_drive) {
        super(leftMotor, rightMotor);
    }
  */

  public Drive(SpeedController leftMotor, SpeedController rightMotor,ADXRS450_Gyro drive_Gyro) {
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