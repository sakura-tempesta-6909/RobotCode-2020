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
            //Drive
            state.driveState = State.DriveState.kManual;
            state.driveStraightSpeed = Util.deadbandProcessing(-driver.getY(GenericHID.Hand.kLeft));
            state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(GenericHID.Hand.kRight));
            if(Util.deadbandProcessing(driver.getTriggerAxis(GenericHID.Hand.kLeft))>0.2&&Util.deadbandProcessing(driver.getTriggerAxis(GenericHID.Hand.kRight))<0.2){
                state.intakeState = State.IntakeState.kIntake;
                state.intakeBeltState = State.IntakeBeltState.kIntake;
            }else if(Util.deadbandProcessing(driver.getTriggerAxis(GenericHID.Hand.kRight))>0.2 && Util.deadbandProcessing(driver.getTriggerAxis(GenericHID.Hand.kLeft))<0.2) {
                state.intakeState = State.IntakeState.kouttake;
                state.intakeBeltState = State.IntakeBeltState.kouttake;
            }else{
                state.intakeState = State.IntakeState.doNothing;
                state.intakeBeltState = State.IntakeBeltState.doNothing;
            }
            if(operator.getBumper(GenericHID.Hand.kLeft)){
                state.controlState = State.ControlState.m_ShootingBall;
            }else if(operator.getBackButton()){
                state.controlState = State.ControlState.m_PanelRotation;
            }else if(driver.getStartButton()){
                state.controlState = State.ControlState.m_Climb;
            }
            drive.apllyState(state);
            intake.applyState(state);
            intakeBelt.applyState(state);
        }

    }
}
