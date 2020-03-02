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
        //後ろにボールがある(5つ入ってる)なら動かさない
        if(is_BallBack() && is_BallFront()) {is_preBallFront = is_BallFront(); setSpeed(0, 0); state.is_IntakeFull = true; return;}
        //直前までボールがある(1個分回収してない)かつ今ボールがない(回収した)ならボールゲット、ストップ
        if(is_preBallFront && !is_BallFront()) { is_preBallFront = is_BallFront(); setSpeed(-1, 0); ballget = true; return;}
        if(is_BallFront()) {is_preBallFront = true; ballget = false;}
        if(ballget) {setSpeed(-1, 0); return;}
        

        setSpeed(-1, -1);
        is_preBallFront = is_BallFront();
        
    }

    private void outtake() {
        //ToDo:一個ずつ
        //無理では？(名推理)
        setSpeed(1, 1); 
    }

     //あったらtrue なかったらfalseを返す
    private boolean is_BallFront() {
        System.out.println("ballfornt");
        return !intakeFrontSensor.get();
    }

    //あったらtrue なかったらfalseを返す
    private boolean is_BallBack() {
        System.out.println("ballback");
        return !intakeBackSensor.get();
    }
}
