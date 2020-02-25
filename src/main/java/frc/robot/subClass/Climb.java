package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;


public class Climb {

    //クライム用のモーター&エンコーダー
    private TalonSRX ClimbMotor;
    private TalonSRX CanonMotor;
    private Servo Servo;
    private TalonSRX SlideMotor;
    private ArmSensor armSensor;
    private Timer lockTimer;
    private Arm arm;

    Climb(TalonSRX hangingMotor, Servo hangingServo, TalonSRX climbSlideMotor, Timer climbTimer,ArmSensor armSensor,Arm arm) {
        this.ClimbMotor = hangingMotor;
        this.Servo = hangingServo;
        this.SlideMotor = climbSlideMotor;
        this.lockTimer = climbTimer;
        this.armSensor = armSensor;
        this.arm = arm;
    }

    public void apllyState(State state){
        switch(state.climbState){

            case doNothing:

            case climbExtend:
                unlockServo();
                climbAdvanced();
                break;

            case climbShrink:
                unlockServo();
                lockTimer.reset();
                lockTimer.start();
                if(lockTimer.get() > 0.3) {
                    climbShrinked();
                }
                climbShrinked();
                break;

            case climbLock:
                lockServo();
                break;

            case climbRightSlide:
                lockServo();
                rightSlide();
                break;

            case climbLeftSlide:
                lockServo();
                leftSlide();
                break;
        }
    }

    //砲台のモーターを回す(速さはsetspeedで決める)

    private void ClimbMove() {

        if (armSensor.getArmFrontSensor()) {
            setClimbMotorSpeed(Const.climbMotorAdvanceSpeed);
           arm.setArmSpeed(Const.canonMotorAdvanceSpeed);
        } else if  (armSensor.getArmBackSensor()) {
            setClimbMotorSpeed(Const.climbMotorAdvanceSpeed);
            arm.setArmSpeed(Const.canonMotorShrinkSpeed);
        }

    }

    // クライムを伸ばす
    private void climbAdvanced(){
        setClimbMotorSpeed(Const.climbMotorAdvanceSpeed);
        ClimbMove();
        // CanonMotor.set(0.15);

    }

    // クライムを縮める
    private void climbShrinked(){
        setClimbMotorSpeed(Const.climbMotorShrinkSpeed);
        ClimbMove();

    }

    // クライムをアンロックする
    private void unlockServo(){
        setServoAngle(Const.unLockAngle);
    }

    // クライムをロックする
    private void lockServo(){
        setServoAngle(Const.lockAngle);
    }

    // ジェネレーター上で右に動く
    private void rightSlide() {
        setSlideMotorSpeed(Const.slideMotorRight);
    }

    // ジェネレーター上で左に動く
    private void leftSlide() {
        setSlideMotorSpeed(Const.slideMotorLeft);
    }

    private void setSlideMotorSpeed(double speed){
        SlideMotor.set(ControlMode.Current,speed);
    }

    private void setClimbMotorSpeed(double speed){
        ClimbMotor.set(ControlMode.Current,speed);
    }


    private void setServoAngle(double angle){
        Servo.set(angle);
    }
    // private void climbCheck (State state) {

    // }
}