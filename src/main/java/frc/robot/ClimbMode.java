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

        this.arm = arm;
    }

    public void changeState(State state) {
        System.out.println(state.climbState);

        switch (state.climbState) {
            case doNothing:
                lockServo();
                setClimbMotorSpeed(0);
                setSlideMotorSpeed(0);
                n_extendReverse = 0;
                is_LockTimerStart = false;
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
                if (!is_LockTimerStart) {
                    lockTimer.reset();
                    lockTimer.start();
                    is_LockTimerStart = true;
                }
                if (lockTimer.get() > 0.4) {
                    //実質0.08s
                    if (n_extendReverse > 3) {
                        lockServo();
                        setClimbMotorSpeed(Const.climbMotorExtendSpeed);
                    } else {
                        lockServo();
                        setClimbMotorSpeed(-1);
                        n_extendReverse++;
                    }
                } else {
                    lockServo();
                }
                break;

            case climbMotorOnlyShrink:
                lockServo();
                setClimbMotorSpeed(-0.3);
        }
    }

    // クライムを伸ばす
    private void climbExtend(State state) {
        double armAngle = state.armAngle;

        if (armAngle <= -Const.armParallelAngleRange) {
            //Armの角度変更
            state.armState = State.ArmState.k_PID;
            state.setArmAngle = Const.armParallelAngle;
            n_extendReverse = 0;
            unlockServo();
        }
        System.out.println(armAngle);
        if (-Const.armParallelAngleRange < armAngle) {
            // Arｍ機構と合うようにスピードを調整
            state.armState = State.ArmState.k_Adjust;
            state.armMotorSpeed = arm.SetFeedForward(armAngle) + Const.climbArmExtendSpeed + state.climbExtendAdjustSpeed;
            if (!is_LockTimerStart) {
                lockTimer.reset();
                lockTimer.start();
                is_LockTimerStart = true;
            }
            if (lockTimer.get() > 0.4) {
                //実質0.08s
                if (n_extendReverse > 3) {
                    lockServo();
                    setClimbMotorSpeed(Const.climbMotorExtendSpeed);
                } else {
                    lockServo();
                    setClimbMotorSpeed(-1);
                    n_extendReverse++;
                }
            } else {
                lockServo();
            }
        }
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
        System.out.println("slideMotorCurrent(In):" + slideMotor.getSupplyCurrent());
    }

    public void setClimbMotorSpeed(double speed) {
        climbMotor.set(ControlMode.PercentOutput, speed);
    }

    private void setServoAngle(double angle) {
        climbServo.set(angle);
    }
}
