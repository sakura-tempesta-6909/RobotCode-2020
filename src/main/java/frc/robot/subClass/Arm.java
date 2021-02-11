package frc.robot.subClass;

//import edu.wpi.first.wpilibj.Encoder;
//import edu.wpi.first.wpilibj.DigitalInput;
//import edu.wpi.first.wpilibj.SpeedController;
//import edu.wpi.first.wpilibj.PIDController;
//import edu.wpi.first.wpilibj.Talon;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.DemandType;
import com.ctre.phoenix.motorcontrol.SensorCollection;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Arm {

    //宣言
    TalonSRX Motor;            //モーター
    SensorCollection Encoder;  //角度測る半固定抵抗

    ArmSensor armSensor;

    double armAccelTime; //未使用
    double armConstTime; //未使用
    double armTargetAngle;
    double armPIDPower;
    double armOutput;


    //コンストラクター
    public Arm(TalonSRX ArmMotor, SensorCollection ArmEncoder, ArmSensor armSensor) {
        this.Motor = ArmMotor;
        this.Encoder = ArmEncoder;
        this.armSensor = armSensor;
    }

    //---------------------------------------------------------------------------------------------------
    //出力処理
    public void applyState(State state) {
        SmartDashboard.putNumber("armpoint", Encoder.getAnalogInRaw());

        switch (state.armState) {
            //---------------------------------------------------------------
            //砲台の角度を基本状態に
            case k_Basic:
                ArmChangeBasic(state.armAngle);
                break;
            //---------------------------------------------------------------
            //砲台の角度をPIDで制御
            case k_PID:
                ArmPIDMove(state.armSetAngle, state.armAngle);
                break;
            //---------------------------------------------------------------
            //砲台の角度を微調整 正か負のみ
            case k_Adjust:
                armAdjust(state);
                break;
            //---------------------------------------------------------------
            //手動操作
            case k_Manual:
                ArmMove(state.armMotorSpeed);
                break;
            //---------------------------------------------------------------
            //維持する
            case k_Conserve:
                ArmStop(state.armAngle);
                break;
            //---------------------------------------------------------------
            //指定した角度
            case k_ConstAng:
                armPIDControl(state.armFinalTargetAngle, state.armAngle);
                //state.DisAng = state.armSetAngle - state.armAngle;
                //armAccelTime = accelTime(state.DisAng);
                //armConstTime = constTime(state.DisAng);
                break;
            //---------------------------------------------------------------
            //何もしない
            case k_DoNothing:
                ArmMove(0);
        }
    }

    //--------------------------------------------------------------------------------
    //砲台の角度を微調整する（PID無し）正か負のみ
    void armAdjust(State state) {
        if (armSensor.getArmFrontSensor()) {
            //------------------------------------------
            //角度が初期状態（-30度）
            if (state.armMotorSpeed > 0) {
                ArmMove(Const.ArmBasicUpSpeed);
            } else {
                ArmMove(0);
            }
        } else if (armSensor.getArmBackSensor()) {
            //------------------------------------------
            //角度が最も上（80度）
            if (state.armMotorSpeed < 0) {
                //LowSpeedで下げると下がらないので少し早め
                ArmMove(Const.ArmHighDownSpeed);
            } else {
                ArmMove(0);
            }
        } else {
            //------------------------------------------
            //角度が-30度～80度
            if (state.armMotorSpeed > 0) {
                System.out.println("armUp!!!");
                ArmMove(state.armMotorSpeed);
            } else if (state.armMotorSpeed < 0) {
                ArmMove(Const.ArmLowDownSpeed);
            } else {
                //念のため、入力ナシならStop
                ArmStop(state.armAngle);
            }

        }
    }

    //--------------------------------------------------------------------------------
    //砲台のモーターを回す制御(速度をsetSpeedで決める)
    private void ArmMove(double setSpeed) {
        Motor.set(ControlMode.PercentOutput, setSpeed);
        SmartDashboard.putNumber("setSpeedMove", setSpeed);
    }

    //重力オフセットを使う事でモーターを停止させる(SetFeedForward()から決める)
    public void ArmStop(double NowAngle) {
        Motor.set(ControlMode.PercentOutput, 0,
                DemandType.ArbitraryFeedForward, SetFeedForward(NowAngle));
        SmartDashboard.putNumber("Stop", NowAngle);
    }

    //砲台のモーターを回すPID制御(位置をSetPoint()で決める・重力オフセットをSetFeedForward()で決める)
    public void ArmPIDMove(double TargetAngle, double NowAngle) {
        Motor.set(ControlMode.Position, SetPoint(TargetAngle),
                DemandType.ArbitraryFeedForward, SetFeedForward(NowAngle));
    }

    //--------------------------------------------------------------------------------

    //砲台を初期状態にする     
    void ArmChangeBasic(double NowAngle) {
        if (!armSensor.getArmFrontSensor()) {
            //角度下限認識スイッチが反応したら何も起こらない
            //角度下限認識スイッチが反応してなかったら、回す
            if (NowAngle > Const.ArmDownBorderAngle) {
                //早く落とす
                SmartDashboard.putNumber("High", NowAngle);
                ArmMove(Const.ArmHighDownSpeed);
            } else {
                //ゆっくり落とす
                SmartDashboard.putNumber("Low", NowAngle);
                ArmMove(Const.ArmLowDownSpeed);
            }
        } else {
            ArmMove(0);
        }
    }

    //---------------------------------------------------------------------
    //砲台角度制御に関する計算式

    //現在の砲台の角度を計算
    //(角度の最大最小差分) ÷（エンコーダー値の最大最小差分) × (エンコーダー現在値 - 最小値) + (角度の最小値)
    public double getArmNow() {
        return Const.armAngleDifference / Const.armPointDifference *
                (Encoder.getAnalogInRaw() - Const.armMinPoint) + Const.armMinAngle;
    }

    //目標角度に合わせたPIDの目標値を計算
    //(目標角度 - 最小角度) ×（エンコーダー値の最大最小差分) ÷ (角度の最大最小差分) + (0からの差分)
    private double SetPoint(double TargetAngle) {
        return (TargetAngle - Const.armMinAngle) * Const.armPointDifference /
                Const.armAngleDifference + Const.armMinPoint;
    }

    //目標角度に合わせた重力オフセットを計算
    //(地面と水平な時の重力オフセット) × (cos現在角度)
    public double SetFeedForward(double NowAngle) {
        return Const.armMaxOffset * Math.cos(Math.toRadians(NowAngle));
    }

    //--------------------------------------------------------------------------------

    //砲台を指定した角度まで台形制御で動かす
    /** armAccelTime（等速で動いている時間）の計算.
     * @param distance 現在のアームの角度と目標角度の差（上方向の場合+、下方向の場合－）
     */
    double accelTime(double distance) {
        double accelTime;
        if(Math.abs(distance) < Const.ArmConAng) {
            accelTime = Const.ArmFullSpeedTime * (Math.abs(distance) / Const.ArmFullSpeedTime / Const.ArmMaxSpeed);
        }
        else {
            accelTime = Const.ArmFullSpeedTime;
        }
        return accelTime;
    }
    /** armConstTime（等速で動いている時間）の計算.
     * @param distance 現在のアームの角度と目標角度の差（上方向の場合+、下方向の場合－）
     */
    double constTime(double distance) {
        double constTime;
        if(Math.abs(distance) < Const.ArmConAng) {
            constTime = 0;
        }
        else {
            constTime = (Math.abs(distance) - Const.ArmFullSpeedTime * Const.ArmMaxSpeed) / Const.ArmMaxSpeed;
        }
        return constTime;
    }
    /** PID制御 */


    /** 重力分追加 */


    /** アーム動かす */

    void armPIDControl(double finalTargetAngle, double nowAngle) {
        armPIDPower = (finalTargetAngle - nowAngle) * 0;// P制御
        if(Math.abs(finalTargetAngle - nowAngle) > Const.Acceleration) {
            if(finalTargetAngle - nowAngle > 0) {
                armTargetAngle = armTargetAngle + Const.Acceleration;
            }
            else {
                armTargetAngle = armTargetAngle - Const.Acceleration;
            }
        }
        else {
            armTargetAngle = finalTargetAngle;
        }
        armOutput = (armTargetAngle - nowAngle) / Const.Acceleration * Const.ArmMaxSpeed + armPIDPower + SetFeedForward(nowAngle);
        ArmMove(armOutput);
    }
}