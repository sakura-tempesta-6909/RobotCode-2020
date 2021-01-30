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
            case kShoot:
                setSpeed(-1.0, 1.0);
                break;
            case kIntake:
                setSpeedPercent(Const.shooterIntakeSpeed, -Const.shooterIntakeSpeed);
                break;
            case kOuttake:
                setSpeedPercent(Const.shooterOutTakeSpeed, -Const.shooterOutTakeSpeed);
                break;
            case kManual:
                setSpeed(state.shooterLeftSpeed, state.shooterRightSpeed);
                break;
            case doNothing:
                setSpeed(0, 0);
                shooterLeft.setIntegralAccumulator(0);
                shooterRight.setIntegralAccumulator(0);
                break;
        }
    }

    public void setSpeed(double leftSpeed, double rightSpeed) {
        double targetLeftVelocity_UnitsPer100ms = leftSpeed * Const.shooterMotorMaxOutput;
        double targetRightVelocity_UnitsPer100ms = rightSpeed * Const.shooterMotorMaxOutput;
        shooterLeft.set(ControlMode.Velocity, targetLeftVelocity_UnitsPer100ms);
        shooterRight.set(ControlMode.Velocity, targetRightVelocity_UnitsPer100ms);
    }

    public void setSpeedPercent(double speedPercentLeft, double speedPercentRight) {
        //Rは正で出す
        shooterLeft.set(ControlMode.PercentOutput, speedPercentLeft);
        shooterRight.set(ControlMode.PercentOutput, speedPercentRight);
    }
}
