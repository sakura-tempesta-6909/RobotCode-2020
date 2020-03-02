package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Shooter {
    TalonSRX shooterRight, shooterLeft;

    public Shooter(TalonSRX shooterRight, TalonSRX shooterLeft) {
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;
    }

    public void applyState(State state) {
        switch (state.shooterState) {
            case kshoot:
                setSpeed(state.shooterPIDSpeed);
                break;
            case kintake:
                setSpeedPercent(Const.shooterIntakeSpeed, -Const.shooterIntakeSpeed);
                break;
            case kouttake:
                setSpeedPercent(Const.shooterOutTakeSpeed, -Const.shooterOutTakeSpeed);
                break;
            case kmanual:
                setSpeedPercent(state.shooterLeftSpeed, state.shooterRightSpeed);
                break;
            case doNothing:
                setSpeedPercent(0, 0);
                break;
        }
    }

    public void setSpeed(double speed) {
        double targetVelocity_UnitsPer100ms = speed * Const.shooterMotorMaxOutput;
        shooterLeft.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
        shooterRight.set(ControlMode.Velocity, -targetVelocity_UnitsPer100ms);
    }

    public void setSpeedPercent(double speedPercentLeft, double speedPercentRight) {
        //Rは正で出す
        shooterLeft.set(ControlMode.PercentOutput, speedPercentLeft);
        shooterRight.set(ControlMode.PercentOutput, speedPercentRight);
    }
}
