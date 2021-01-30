package frc.robot.subClass;

import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class ArmSensor {
    private TalonSRX armMotor;

    public ArmSensor(TalonSRX armMotor) {
        this.armMotor = armMotor;
    }

    //押されてたらtrue 
    public boolean getArmFrontSensor() {
        //ハード側で逆につけてしまった
        return armMotor.isRevLimitSwitchClosed() == 1;
    }

    //押されてたらtrue
    public boolean getArmBackSensor() {
        //ハード側で逆につけてしまった
        return armMotor.isFwdLimitSwitchClosed() == 1;
    }
}

