package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Intake {
    VictorSPX intakeMotor;


    public Intake(VictorSPX intakeMotor) {
        this.intakeMotor = intakeMotor;
    }

    public void applyState(State state) {
        switch (state.intakeState) {
            case kOuttake:
                setSpeed(-Const.outtakeSpeed);
                break;
            case kIntake:
                setSpeed(-Const.intakeSpeed);
                break;
            case doNothing:
                setSpeed(0);
                break;
            case kDrive:
                setSpeed(0.20);
        }
    }

    public void setSpeed(double speed) {
        intakeMotor.set(ControlMode.PercentOutput, speed);
    }
}
