package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class ShootingBallMode {
    Drive drive;
    Shooter shooter;
    Arm arm;
    XboxController driver,operator;

    ShootingBallMode(Drive drive, Shooter shooter,Arm arm, Controller controller){
        this.drive = drive;
        this.shooter = shooter;
        this.arm = arm;
        this.driver = controller.driver;
        this.operator = controller.operator;
    }

    public void applyMode(State state){
        if(state.controlState == State.ControlState.m_ShootingBall){
            //もう一度バンパーが押されたら、ドライブモードへ切り替え
            if(!operator.getBumper(GenericHID.Hand.kLeft)){
                if(Util.deadbandCheck(operator.getTriggerAxis(GenericHID.Hand.kRight))){
                    //ボールを飛ばす
                    state.shooterState = State.ShooterState.kshoot;
                    state.shooterPIDSpeed = operator.getTriggerAxis(GenericHID.Hand.kRight);
                    state.driveState = State.DriveState.kdoNothing;
                }else if(Util.deadbandCheck(driver.getX(GenericHID.Hand.kLeft))){
                   //ドライブを少し動かす
                    state.shooterState = State.ShooterState.doNothing;
                    state.driveState = State.DriveState.kLow;
                    state.driveRotateSpeed = driver.getX(GenericHID.Hand.kLeft);
                    state.driveStraightSpeed = driver.getY(GenericHID.Hand.kRight);;
                }else if(operator.getBButton()){
                    //砲台の角度をゴールへ調節する
                    state.driveState = State.DriveState.kdoNothing;
                    state.shooterState = State.ShooterState.doNothing;
                    state.armState = State.ArmState.k_Aaiming;
                    state.shooterAngle = 0;
                }else if(Util.deadbandCheck(operator.getX(GenericHID.Hand.kLeft))){
                    //砲台の角度を手動で調節
                    state.driveState = State.DriveState.kdoNothing;
                    state.shooterState = State.ShooterState.doNothing;
                    state.armState = State.ArmState.k_Aaiming;
                    state.shooterAngle = operator.getX(GenericHID.Hand.kLeft);
                }
            }else{
                //ドライブモードへ切り替え
                state.controlState = State.ControlState.m_Drive;
                state.driveState = State.DriveState.kManual;
                state.shooterState = State.ShooterState.doNothing;
                state.armState = State.ArmState.k_Aaiming;
                Util.sendConsole("Mode","DriveMode");
            }

            drive.apllyState(state);
            shooter.applyState(state);
            arm.applyState(state);
        }
    }
}
