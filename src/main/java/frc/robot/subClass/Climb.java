package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;


public class Climb {

    //クライム用のモーター&エンコーダー
    private TalonSRX climbMotor;
    private TalonSRX armMotor;
    private Servo climbServo;
    private TalonSRX slideMotor;
    private ArmSensor armSensor;
    private Timer lockTimer;
    private Arm arm;

    Climb(TalonSRX climbMotor, Servo climbServo, TalonSRX climbSlideMotor, Timer climbTimer, ArmSensor armSensor, Arm arm) {
        this.climbMotor = climbMotor;
        this.climbServo = climbServo;
        this.slideMotor = climbSlideMotor;
        this.lockTimer = climbTimer;
        this.armSensor = armSensor;
        this.arm = arm;
    }

    public void applyState(State state) {
        switch (state.climbState) {

            case doNothing:
                break;
            case climbExtend:
                climbExtend(state.armAngle);
                break;

            case climbShrink:
                // ここ、if文の意味ないし、マイループreset and startする。0.3秒は長い気もする。
                lockTimer.reset();
                lockTimer.start();
                if (lockTimer.get() > 0.3) {
                    climbShrink(state.armAngle);
                }
                climbShrink(state.armAngle);
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



     // クライムを伸ばす
    private void climbExtend(double armAngle) {
        unlockServo();
        //Armの角度変更
        //arm.ArmMove(Const.climbArmExtendSpeed);

        if(-Const.armParallelAngleRange < armAngle && armAngle < Const.armParallelAngleRange){
            // Aｒｍ機構と合うようにスピードを調整
            arm.ArmMove(Const.climbArmExtendSpeed);
            setClimbMotorSpeed(Const.climbMotorExtendSpeed);
        }




    }

    // クライムを縮める
    private void climbShrink(double armAngle) {

        if(armAngle > Const.armParallelAngleRange) {
            unlockServo();
            //Armの角度変更
            arm.ArmMove(Const.climbArmShrinkSpeed);
            setClimbMotorSpeed(Const.climbMotorShrinkSpeed);
        } else if(-Const.armParallelAngleRange <= armAngle && armAngle <= Const.armParallelAngleRange) {
            arm.ArmMove(0);
            setClimbMotorSpeed(0);
            lockServo();
        }else if(armAngle < Const.armParallelAngleRange) {
            //機構破壊防止のためClimbが下がりすぎたら上げる。
            unlockServo();
            arm.ArmMove(Const.climbArmExtendSpeed);
            setClimbMotorSpeed(Const.climbMotorExtendSpeed);
        }


    }

    // クライムをアンロックする
    private void unlockServo() {
        setServoAngle(Const.unLockAngle);
    }

    // クライムをロックする
    private void lockServo() {
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

    private void setSlideMotorSpeed(double speed) {
        slideMotor.set(ControlMode.Velocity, speed);
    }

    private void setClimbMotorSpeed(double speed) {
        climbMotor.set(ControlMode.Velocity, speed);
    }


    private void setServoAngle(double angle) {
        climbServo.set(angle);
    }
    // private void climbCheck (State state) {

    // }
}