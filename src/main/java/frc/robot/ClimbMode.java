package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.Timer;
import frc.robot.subClass.*;

public class ClimbMode {
    Arm arm;

    //クライム用のモーター&エンコーダー
    private TalonSRX climbMotor;
    private Servo climbServo;
    private TalonSRX slideMotor;
    private Timer lockTimer, slideTimer;
    private boolean is_LockTimerStart;

    private int n_extendReverse;

    ClimbMode(Arm arm, TalonSRX climbMotor, Servo climbServo, TalonSRX climbSlideMotor) {
        this.climbMotor = climbMotor;
        this.climbServo = climbServo;
        this.slideMotor = climbSlideMotor;
        this.lockTimer = new Timer();
        this.slideTimer = new Timer();
        slideTimer.start();
        this.arm = arm;
    }

    public void changeState(State state) {
        System.out.println(state.climbArmState);

        switch (state.climbArmState) {
            case doNothing:
                unlockServo();
                setClimbMotorSpeed(0);
                setSlideMotorSpeed(0);
                n_extendReverse = 0;
                is_LockTimerStart = false;
                break;
            case climbExtend:
                //伸ばさない
                climbArmUp(state);
                break;

            case climbShrink:
                climbShrink(state);
                break;

            case climbSlide:
                //-で右 +で左
                lockServo();
                setSlideMotorSpeed(state.climbSlideMotorSpeed);
                break;
        }

        switch (state.climbWireState) {
            case doNothing:
                break;
            case climbMotorOnlyExtend:
                if (!is_LockTimerStart) {
                    lockTimer.reset();
                    lockTimer.start();
                    is_LockTimerStart = true;
                }
                if (lockTimer.get() > 0.4) {
                    //実質0.04s
                    if (n_extendReverse > 1) {
                        unlockServo();
                        setClimbMotorSpeed(Const.climbMotorExtendSpeed);
                    } else {
                        unlockServo();
                        setClimbMotorSpeed(-1);
                        n_extendReverse++;
                    }
                } else {
                    unlockServo();
                }
                break;

            case climbMotorOnlyShrink:
                unlockServo();
                setClimbMotorSpeed(-0.5);
                break;

            case climbLock:
                lockServo();
        }
    }

    //アームを上げる
    private void climbArmUp(State state) {
        double armAngle = state.armAngle;

        if(armAngle < 0) {
            state.armState = State.ArmState.k_PID;
            state.armSetAngle = 15;
        }else {
            // Arｍ機構と合うようにスピードを調整
            state.armState = State.ArmState.k_Adjust;
            state.armMotorSpeed = arm.SetFeedForward(armAngle) + Const.climbArmExtendSpeed + state.climbExtendAdjustSpeed;
            System.out.println("armMotorSpeed" + state.armMotorSpeed);
        }
            /*
            if (!is_LockTimerStart) {
                lockTimer.reset();
                lockTimer.start();
                is_LockTimerStart = true;
            }
            if (lockTimer.get() > 0.4) {
                //実質0.04s
                if (n_extendReverse > 1) {
                    unlockServo();
                    setClimbMotorSpeed(Const.climbMotorExtendSpeed);
                } else {
                    unlockServo();
                    setClimbMotorSpeed(-1);
                    n_extendReverse++;
                }
            } else {
                unlockServo();
            }
             */

    }

    // クライムを縮める
    private void climbShrink(State state) {
            lockServo();
            //Armの角度変更
            state.armState = State.ArmState.k_DoNothing;
            setClimbMotorSpeed(Const.climbMotorShrinkSpeed);
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
        if(slideMotor.getStatorCurrent() > 30) {
            slideTimer.reset();
            slideTimer.start();
        }
        if(slideTimer.get() < 0.3) {
            //クールダウン
            speed = 0;
        }

        slideMotor.set(ControlMode.PercentOutput, speed);
        System.out.println("slideMotorCurrent(Out):" + slideMotor.getStatorCurrent());
    }

    public void setClimbMotorSpeed(double speed) {
        climbMotor.set(ControlMode.PercentOutput, speed);
    }

    private void setServoAngle(double angle) {
        climbServo.set(angle);
    }
}
