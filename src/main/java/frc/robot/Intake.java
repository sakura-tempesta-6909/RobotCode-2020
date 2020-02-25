package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake {
    VictorSPX intakeMotor;


    Intake(VictorSPX intakeMotor){
        this.intakeMotor = intakeMotor;
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
        intakeMotor.set(ControlMode.Current,speed);
    }
}
