package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class IntakeBelt {

    private VictorSPX intakeBeltFront, intakeBelfBack;
    private DigitalInput intakeFrontSensor, intakeBackSensor;
    private boolean is_preBallFront, ballget;

    public IntakeBelt(VictorSPX intakeBeltFront, VictorSPX intakeBeltBack, DigitalInput intakeFrontSensor, DigitalInput intakeBack) {
        this.intakeBeltFront = intakeBeltFront;
        this.intakeBelfBack = intakeBeltBack;
        this.intakeFrontSensor = intakeFrontSensor;
        this.intakeBackSensor = intakeBack;
    }

    public void applyState(State state) {
        switch (state.intakeBeltState) {
            case kIntake:
                intake(state);
                break;
            case kOuttake:
                outtake();
                Util.sendConsole("outtake", "out");
                break;
            case doNothing:
                setSpeed(0, 0);
                break;
        }
    }

    private void setSpeed(double frontSpeed, double backSpeed) {
        intakeBeltFront.set(ControlMode.PercentOutput, frontSpeed);
        intakeBelfBack.set(ControlMode.PercentOutput, backSpeed);
    }

    private void intake(State state) {
        //ToDo:一個ずつ
        //ボールがある＝回収中or満タン
        if(is_BallFront()) {
            is_preBallFront = true;
            ballget = false;
            if(is_BallBack()) {
                //前後にボールあれば満タン
                setSpeed(0, 0);
                state.is_IntakeFull = true;
                return;
            }
        } else {
            //ボールなし＝回収済みorゼロ個
            if(is_preBallFront) {
                //直前ボールあり(回収中)&&現在ボールなし(回収済)ならボールゲット、後ろストップ
                setSpeed(-1, 0);
                ballget = true;
            }
            is_preBallFront = false;
        }

        if(ballget) {
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
        System.out.println("ballFront");
        return !intakeFrontSensor.get();
    }

    //あったらtrue なかったらfalseを返す
    private boolean is_BallBack() {
        System.out.println("ballBack");
        return !intakeBackSensor.get();
    }
}
