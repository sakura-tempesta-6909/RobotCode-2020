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

    private int n_extendReverse;

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
                setSlideMotorSpeed(0);
                n_extendReverse = 0;
                break;
            case climbExtend:
                System.out.println("climbExtending");
                climbExtend(state);
                break;

            case climbShrink:
                climbShrink(state);
                break;

            case climbLock:
                lockServo();
                break;

            case climbSlide:
                //-で右 +で左
                lockServo();
                setSlideMotorSpeed(state.climbSlideMotorSpeed);
                break;

            case climbMotorOnlyExtend:
                unlockServo();
                setClimbMotorSpeed(0.4);
                break;

            case climbMotorOnlyShrink:
                lockServo();
                setClimbMotorSpeed(-0.3);
        }
    }

    public void applyState(State state) {
    }

    // クライムを伸ばす
    private void climbExtend(State state) {
        double armAngle = state.armAngle;
        unlockServo();
        if (armAngle <= -Const.armParallelAngleRange) {
            //Armの角度変更
            state.armState = State.ArmState.k_Parallel;
        }
        System.out.println(armAngle);
        if (-Const.armParallelAngleRange < armAngle) {
            // Arｍ機構と合うようにスピードを調整
            state.armState = State.ArmState.k_Adjust;
            state.armMotorSpeed = arm.SetFeedForward(armAngle) + Const.climbArmExtendSpeed + state.climbExtendAdjustSpeed;
            System.out.println(state.armMotorSpeed);
            if(n_extendReverse > 3) {
                System.out.println("climbextending");
                setClimbMotorSpeed(Const.climbMotorExtendSpeed);
            } else {
                setClimbMotorSpeed(-1);
                n_extendReverse++;
            }
        }
    }

    // クライムを縮める
    private void climbShrink(State state) {
        double armAngle = state.armAngle;
        if (armAngle > Const.armParallelAngleRange) {
            lockServo();
            //Armの角度変更
            state.armState = State.ArmState.k_Manual;
            state.armMotorSpeed = 0;
            setClimbMotorSpeed(Const.climbMotorShrinkSpeed);
        } else if (-Const.armParallelAngleRange <= armAngle && armAngle <= Const.armParallelAngleRange) {
            //アームの速さを任意でセットする関数をArmにつくる
            state.armState = State.ArmState.k_Conserve;
            setClimbMotorSpeed(0);
            lockServo();
        } else if (armAngle < Const.armParallelAngleRange) {
            //機構破壊防止のためClimbが下がりすぎたら上げる。


            lockServo();
            //アームの速さを任意でセットする関数をArmにつくる
            state.armState = State.ArmState.k_Manual;
            state.armMotorSpeed = Const.climbArmExtendSpeed;
            setClimbMotorSpeed(0);
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