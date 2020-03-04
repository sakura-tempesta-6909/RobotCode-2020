package frc.robot.subClass;

import com.revrobotics.ColorSensorV3;
import edu.wpi.first.wpilibj.I2C;
import edu.wpi.first.wpilibj.util.Color;

public class Panel {

    //ports n such (>const?)
    final static I2C.Port i2cPort = I2C.Port.kOnboard;
    final static ColorSensorV3 m_colorSensor = new ColorSensorV3(i2cPort);
    ColorCode colorOutput = ColorCode.inRange;

    public Panel() {}

    public void changeState(State state) {
        switch (state.panelState) {

            case p_ManualRot:
                state.shooterState = State.ShooterState.kmanual;
                state.shooterLeftSpeed = state.shooterRightSpeed = state.panelManualSpeed;
                break;

            //色合わせ　青<->赤、黄<->緑
            case p_toBlue:
                AlignPanelTo(ColorCode.red, state);
                break;

            case p_toYellow:
                AlignPanelTo(ColorCode.green, state);
                break;

            case p_toRed:
                AlignPanelTo(ColorCode.blue, state);
                break;

            case p_toGreen:
                AlignPanelTo(ColorCode.yellow, state);
                break;

            case p_DoNothing:
                state.shooterState = State.ShooterState.kmanual;
                state.shooterLeftSpeed = state.shooterRightSpeed = 0;
                break;
        }
    }

    //DetectedColor(ロボット側のカラーセンサーの目標値　青<->赤、黄<->緑)　で呼び出す
    private ColorCode DetectedColor() {
        int p = m_colorSensor.getProximity();
        Color detectedColor = m_colorSensor.getColor();
        double r = detectedColor.red;
        double g = detectedColor.green;
        double b = detectedColor.blue;
        if (p < 80) {
            return ColorCode.outOfRange;
        }
        if ((0.2 <= r && r < 0.4) && (0.45 <= g) && (b < 0.2)) {
            return ColorCode.yellow;
        }
        if ((0.3 <= r) && (0.2 <= g && g < 0.48) && (b < 0.3)) {
            return ColorCode.red;
        }
        if ((r < 0.3) && (0.4 <= g) && (0.2 <= b && b < 0.27)) {
            return ColorCode.green;
        }
        if ((r < 0.25) && (0.4 <= g) && (0.27 <= b)) {
            return ColorCode.blue;
        }
        return ColorCode.inRange;
    }
    //

    private void AlignPanelTo(ColorCode c, State state) {

        if (DetectedColor() == c) {
            state.shooterState = State.ShooterState.kmanual;
            state.shooterLeftSpeed = state.shooterRightSpeed = 0;
        } else {
            state.shooterState = State.ShooterState.kmanual;
            state.shooterLeftSpeed = state.shooterRightSpeed = state.panelManualSpeed;
        }

    }

    public enum ColorCode {
        yellow,
        red,
        green,
        blue,
        inRange,
        outOfRange
    }


}