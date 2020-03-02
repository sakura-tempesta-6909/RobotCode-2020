package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;


public class Climb {

    //クライム用のモーター&エンコーダー
    private TalonSRX climbMotor;
    private Servo climbServo;
    private TalonSRX slideMotor;
    private Timer lockTimer;
    private Arm arm;

    public Climb(TalonSRX climbMotor, Servo climbServo, TalonSRX climbSlideMotor, Arm arm) {
        this.climbMotor = climbMotor;
        this.climbServo = climbServo;
        this.slideMotor = climbSlideMotor;
        this.lockTimer = new Timer();
       
        this.arm = arm;
    }

    public void changeState(State state) {
        state.armAngle = arm.getArmNow();
        System.out.println(state.climbState);

        switch (state.climbState) {
            case doNothing:
                lockServo();
                setClimbMotorSpeed(0);
                break;
            case climbExtend:
                System.out.println("climbExtending");
                climbExtend(state.armAngle, state);
                break;

            case climbShrink:
                // ここ、if文の意味ないし、マイループreset and startする。0.3秒は長い気もする。
                lockTimer.reset();
                lockTimer.start();
                if (lockTimer.get() > 0.3) {
                    climbShrink(state.armAngle, state);
                }
                climbShrink(state.armAngle, state);
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

    public void applyState(State state) {}

     // クライムを伸ばす
    private void climbExtend(double armAngle, State state) {
        unlockServo();
        if(armAngle <= -Const.armParallelAngleRange) {
        //Armの角度変更
        state.armState = State.ArmState.k_Parallel;
        }
        System.out.println(armAngle);
        if(-Const.armParallelAngleRange < armAngle){
            // Arｍ機構と合うようにスピードを調整
            System.out.println("parallel");
            state.armState = State.ArmState.k_LittleAim;
            state.armMotorSpeed = arm.SetFeedForward(armAngle) + Const.climbArmExtendSpeed;
            setClimbMotorSpeed(Const.climbMotorExtendSpeed);
        }

    }

    // クライムを縮める
    private void climbShrink(double armAngle, State state) {

        if(armAngle > Const.armParallelAngleRange) {
            unlockServo();
            //Armの角度変更
            state.armState = State.ArmState.k_Shrink;
            setClimbMotorSpeed(Const.climbMotorShrinkSpeed);
        } else if(-Const.armParallelAngleRange <= armAngle && armAngle <= Const.armParallelAngleRange) {
            //アームの速さを任意でセットする関数をArmにつくる
            state.armState = State.ArmState.k_Conserve;
            setClimbMotorSpeed(-0.1);
            lockServo();
        }else if(armAngle < Const.armParallelAngleRange) {
            //機構破壊防止のためClimbが下がりすぎたら上げる。
            unlockServo();
            //アームの速さを任意でセットする関数をArmにつくる
            state.armState = State.ArmState.k_LittleAim;
            state.armMotorSpeed = Const.climbArmExtendSpeed;
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
        slideMotor.set(ControlMode.PercentOutput, speed);
    }

    public void setClimbMotorSpeed(double speed) {
        System.out.println("climb motor" + speed);
        climbMotor.set(ControlMode.PercentOutput, speed);
    }


    private void setServoAngle(double angle) {
        climbServo.set(angle);
    }
    // private void climbCheck (State state) {

    // }
}