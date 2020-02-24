package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Intake {
    TalonSRX intakeMortor;


    Intake(TalonSRX intakeMotor){
        this.intakeMortor = intakeMotor;
    }

    public void applyState(State state){
        switch (state.intakeState) {
            case kouttake:
                setSpeed(Const.outTakeSoeed);
                break;
            case kIntake:
                setSpeed(Const.intakeSpeed);
                break;
            case doNothing:
                setSpeed(0);
                break;
        }
    }
    public void setSpeed(double speed){
        intakeMortor.set(ControlMode.Current,speed);
    }
}
