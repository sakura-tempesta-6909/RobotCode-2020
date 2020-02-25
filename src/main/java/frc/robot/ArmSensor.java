package frc.robot;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;

public class ArmSensor {
    private TalonSRX armMotor;
    ArmSensor(TalonSRX armMotor){
        this.armMotor = armMotor;
    }


    public boolean getArmFrontSensor(){
        return armMotor.isFwdLimitSwitchClosed() == 1;
    }

    public boolean getArmBackSensor(){
        return armMotor.isRevLimitSwitchClosed() == 1;
    }
}

