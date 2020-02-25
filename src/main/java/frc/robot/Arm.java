package frc.robot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.Encoder;
import edu.wpi.first.wpilibj.SpeedController;
import edu.wpi.first.wpilibj.PIDController;

public class Arm {

    //宣言
    Encoder Encoder;            //エンコーダー（多分使わない）
    TalonSRX Motor;      //モーター

    PIDController ArmPID;     //PID制御（今後使う）
    ArmSensor armSensor;

    //コンストラクター
    Arm(TalonSRX ArmMotor, ArmSensor armSensor){
        this.Motor = ArmMotor;
        this.armSensor = armSensor;
    }

    //現在の砲台の状態を確認
   public void applyState(State state){
        switch (state.armState){
            case k_Basic:
                break;
            case k_Maxup:
                break;
            case k_Aaiming:
                break;
        }
    }
    //--------------------------------------------------------------------------------
    //砲台のモーターを回す(速さはsetspeedで決める)
    public void setArmSpeed(double setSpeed){
        Motor.set(ControlMode.Current,setSpeed);
    }


    //--------------------------------------------------------------------------------
    //砲台の位置を管理

    //砲台を初期状態にするコマンド
    private void ArmChangeBasic(){
        while(armSensor.getArmFrontSensor()){
            setArmSpeed(Const.ArmBasicSpeed_M);
        }
    }

    //砲台を地面と平行にするコマンド
    void ArmChangeParallel(){


    }

    //--------------------------------------------------------------------------------

    //エンコーダー管理




    //--------------------------------------------------------------------------------

    /*
    //PID管理
    //PIDの目標値を設定
    public void PIDSetPoint(double setPoint){
        ArmPID.setSetpoint(setPoint);
    }

    //PIDコントローラーをオン
    public void PIDEnable(){
        ArmPID.enable();
    }

    //PIDコントローラーをオフ
    public void PIDDisable(){
        ArmPID.disable();
    }

    */


}