package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj.DigitalInput;

public class IntakeBelt {

    private VictorSPX intakeBeltFront, intakeBelfBack;
    private DigitalInput intakeFrontSensor, intakeBackSensor;

    public IntakeBelt(VictorSPX intakeBeltFront, VictorSPX intakeBeltBack, DigitalInput intakeFrontSensor, DigitalInput intakeBack) {
        this.intakeBeltFront = intakeBeltFront;
        this.intakeBelfBack = intakeBeltBack;
        this.intakeFrontSensor = intakeFrontSensor;
        this.intakeBackSensor = intakeBack;
    }

    public void applyState(State state) {
        switch (state.intakeBeltState) {
            case kIntake:
                intake();
                break;
            case kouttake:
                outtake();
                Util.sendConsole("outtake", "out");
                break;
            case doNothing:
                setSpeed(0);
                break;
        }
    }

    private void setSpeed(double speed) {
        intakeBeltFront.set(ControlMode.PercentOutput, speed);
        intakeBelfBack.set(ControlMode.PercentOutput, speed);
    }

    private void intake() {
        //ToDo:一個ずつ
        setSpeed(-1);
    }

    private void outtake() {
        //ToDo:一個ずつ
        setSpeed(1);
    }

    private boolean getFrontSensor() {
        return intakeFrontSensor.get();
    }

    private boolean getBackSensor() {
        return intakeBackSensor.get();
    }
}
