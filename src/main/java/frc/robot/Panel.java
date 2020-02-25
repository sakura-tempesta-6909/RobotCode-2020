package frc.robot;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.ColorSensorV3;

import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.util.Color;
import frc.robot.State.ControlState;

public class Panel{

    //ports n such (>const?)
    final static I2C.Port i2cPort = I2C.Port.kOnboard;
    final static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    public enum ColorCode {
        yellow,
        red,
        green,
        blue,
        inRange,
        outOfRange
    }

    ColorCode colorOutput = ColorCode.inRange;
    //construction
    Shooter shooter;

    Panel(Shooter shooter){
        this.shooter = shooter;
    }


    public void applyState(State state){
            switch (state.panelState) {

                case p_ManualRot:
                    MotorLeft.set(state.panelManualSpeed);
                    MotorRight.set(state.panelManualSpeed);
                    break;

                //色合わせ　青<->赤、黄<->緑
                case p_toBlue:
                    AlignPanelTo(ColorCode.red);
                    break;

                case p_toYellow:
                    AlignPanelTo(ColorCode.green);
                    break;

                case p_toRed:
                    AlignPanelTo(ColorCode.blue);
                    break;

                case p_toGreen:
                    AlignPanelTo(ColorCode.yellow);
                    break;

                case p_DoNothing:
                    break;
        }

    }
    //


    //DetectedColor(ロボット側のカラーセンサーの目標値　青<->赤、黄<->緑)　で呼び出す
    private ColorCode DetectedColor() {
        int p = m_colorSensor.getProximity();
        Color detectedColor = m_colorSensor.getColor();
        double r = detectedColor.red;
        double g = detectedColor.green;
        double b = detectedColor.blue;
        if (p < 80){
            return ColorCode.outOfRange;
        }
        if ((0.2 <= r && r < 0.4) && (0.45 <= g) && (b < 0.2)){
            return ColorCode.yellow;
        }
        if ((0.3 <= r) && (0.2 <= g && g < 0.48) && (b < 0.3)){
            return ColorCode.red;
        }
        if ((r < 0.3) && (0.4 <= g) && (0.2 <=b && b < 0.27)){
            return ColorCode.green;
        }
        if ((r < 0.25) && (0.4 <= g) && (0.27 <= b)){
            return ColorCode.blue;
        }
        return ColorCode.inRange;
    }

    private void AlignPanelTo(ColorCode c){

        if(DetectedColor()==c){
            shooter.setSpeedPersent(0,0);
        } else {
            shooter.setSpeedPersent(Const.shooterPanelSpeed,Const.shooterPanelSpeed);
        }

    }


}