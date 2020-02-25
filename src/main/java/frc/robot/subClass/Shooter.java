package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class Shooter {
    TalonSRX shooterRight,shooterLeft;

    public Shooter(TalonSRX shooterRight, TalonSRX shooterLeft){
        this.shooterLeft = shooterLeft;
        this.shooterRight = shooterRight;
    }

    public void applyState(State state){
        switch (state.shooterState){
            case kshoot:
                setSpeed(state.shooterPIDSpeed);
                break;
            case kintake:
                setSpeed(Const.shooterIntakeSpeed);
                break;
            case kouttake:
                setSpeed(Const.shooterOutTakeSpeed);
                break;
            case kmanual:
                setSpeedPersent(state.shooterLeftSpeed,state.shooterRightSpeed);
                break;
            case doNothing:
                setSpeed(0);
                break;
        }
    }
    public void setSpeed(double speed){
        double targetVelocity_UnitsPer100ms = speed*Const.shooterMotorMaxOutput;
        shooterLeft.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
        shooterRight.set(ControlMode.Velocity, -targetVelocity_UnitsPer100ms);
    }
    public void setSpeedPersent(double speedPersentLeft,double speedPersentRight){
        shooterLeft.set(ControlMode.PercentOutput,speedPersentLeft);
        shooterRight.set(ControlMode.PercentOutput, speedPersentRight);
    }
}
