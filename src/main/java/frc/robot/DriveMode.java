package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class DriveMode {
    Drive drive;
    Intake intake;
    IntakeBelt intakeBelt;
    XboxController driver,operator;

    DriveMode(Drive drive, Intake intake,IntakeBelt intakeBelt, Controller controller){
        this.drive = drive;
        this.intake = intake;
        this.intakeBelt = intakeBelt;
        this.driver = controller.driver;
        this.operator = controller.operator;
    }

    public void applyMode(State state){
        if(state.controlState == State.ControlState.m_Drive){
            //ほかに関係なくドライブ
            state.driveState = State.DriveState.kManual;
            state.driveStraightSpeed = Util.deadbandProcessing(-driver.getY(GenericHID.Hand.kLeft));
            state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(GenericHID.Hand.kRight));

            if(Util.deadbandCheck(driver.getTriggerAxis(GenericHID.Hand.kLeft))&&!Util.deadbandCheck(driver.getTriggerAxis(GenericHID.Hand.kRight))){
                //ボールを取り込む
                state.intakeState = State.IntakeState.kIntake;
                state.intakeBeltState = State.IntakeBeltState.kIntake;
            }else if(Util.deadbandCheck(driver.getTriggerAxis(GenericHID.Hand.kRight))&&!Util.deadbandCheck(driver.getTriggerAxis(GenericHID.Hand.kLeft))) {
                //ボールを出す
                state.intakeState = State.IntakeState.kouttake;
                state.intakeBeltState = State.IntakeBeltState.kouttake;
            }else{
                //インテイクは何もしない
                state.intakeState = State.IntakeState.doNothing;
                state.intakeBeltState = State.IntakeBeltState.doNothing;
            }

            if(operator.getBumper(GenericHID.Hand.kLeft)){
                //ボール発射モードへ切り替え
                state.controlState = State.ControlState.m_ShootingBall;
                state.intakeState = State.IntakeState.doNothing;
                state.intakeBeltState = State.IntakeBeltState.doNothing;
            }else if(operator.getBackButton()){
                //コントロールパネル回転モードへ切り替え
                state.controlState = State.ControlState.m_PanelRotation;
                state.intakeState = State.IntakeState.doNothing;
                state.intakeBeltState = State.IntakeBeltState.doNothing;
            }else if(driver.getStartButton()){
                //クライムモードへ切り替え
                state.controlState = State.ControlState.m_Climb;
                state.intakeState = State.IntakeState.doNothing;
                state.intakeBeltState = State.IntakeBeltState.doNothing;
            }

            drive.apllyState(state);
            intake.applyState(state);
            intakeBelt.applyState(state);
        }
    }
}
