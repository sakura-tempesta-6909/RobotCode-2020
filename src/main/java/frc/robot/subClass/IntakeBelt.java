package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Timer;

public class IntakeBelt {

    private VictorSPX intakeBeltFront, intakeBeltBack;
    private DigitalInput intakeFrontSensor, intakeBackSensor;
    private boolean is_preBallFront, ballGet;
    //Shootの加速不足解消
    private Timer timer;
    private boolean is_TimerStart;

    public IntakeBelt(VictorSPX intakeBeltFront, VictorSPX intakeBeltBack, DigitalInput intakeFrontSensor, DigitalInput intakeBack) {
        this.intakeBeltFront = intakeBeltFront;
        this.intakeBeltBack = intakeBeltBack;
        this.intakeFrontSensor = intakeFrontSensor;
        this.intakeBackSensor = intakeBack;
        timer = new Timer();
    }

    public void applyState(State state) {

        switch (state.intakeBeltState) {
            case kIntake:
                is_TimerStart = false;
                intake(state);
                break;
            case kOuttake:
                //if(!is_TimerStart) {timer.reset(); timer.start(); is_TimerStart = true;}
                //if(timer.get() > 0.2) {
                if (state.shooterMotorSpeed == 0.7) {
                    outtake();
                } else {
                    setSpeed(0, 0);
                }
                break;
            case doNothing:
                is_TimerStart = false;
                setSpeed(0, 0);
                break;
        }
    }

    private void setSpeed(double frontSpeed, double backSpeed) {
        intakeBeltFront.set(ControlMode.PercentOutput, frontSpeed);
        intakeBeltBack.set(ControlMode.PercentOutput, backSpeed);
    }

    private void intake(State state) {
        //ToDo:一個ずつ
        //ボールがある＝回収中or満タン
        if (is_BallFront()) {
            is_preBallFront = true;
            ballGet = false;
            if (is_BallBack()) {
                //前後にボールあれば満タン
                setSpeed(0, 0);
                state.is_IntakeFull = true;
                System.out.println("   Intake Fulllllll   ");
                return;
            }
        } else {
            //ボールなし＝回収済みorゼロ個
            if (is_preBallFront) {
                //直前ボールあり(回収中)&&現在ボールなし(回収済)ならボールゲット、後ろストップ
                setSpeed(-1, 0);
                ballGet = true;
            }
            is_preBallFront = false;
        }

        if (ballGet) {
            //ボール1~4個
            setSpeed(-1, 0);
            return;
        }

        setSpeed(-1, -1);
        is_preBallFront = is_BallFront();

    }

    private void outtake() {
        setSpeed(1, 1);
    }

    //あったらtrue なかったらfalseを返す
    private boolean is_BallFront() {
        return !intakeFrontSensor.get();
    }

    //あったらtrue なかったらfalseを返す
    private boolean is_BallBack() {
        return !intakeBackSensor.get();
    }
}
