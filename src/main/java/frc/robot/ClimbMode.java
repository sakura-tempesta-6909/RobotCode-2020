package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class ClimbMode {
    Drive drive;
    Arm arm;
    Climb climb;
    XboxController driver,operator;

    ClimbMode(Drive drive,Arm arm,Climb climb, Controller controller){
        this.drive = drive;
        this.arm = arm;
        this.climb = climb;
        this.driver = controller.driver;
        this.operator = controller.operator;
    }

    public void applyMode(State state){
        if (state.controlState == State.ControlState.m_Climb) {
            //Drive
            state.driveState = State.DriveState.kLow;
            state.driveStraightSpeed = Util.deadbandProcessing(-driver.getY(GenericHID.Hand.kLeft));
            state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(GenericHID.Hand.kRight));

            //Climb
            if(operator.getYButton()){
                state.climbState = State.ClimbState.climbExtend;
            }else if(operator.getBButton()){
                state.climbState = State.ClimbState.climbShrink;
            }else if(operator.getBumper(GenericHID.Hand.kLeft)){
                state.climbState = State.ClimbState.climbLeftSlide;
            }else if(operator.getBumper(GenericHID.Hand.kRight)){
                state.climbState = State.ClimbState.climbRightSlide;
            }else if(operator.getStartButton()){
                state.climbState = State.ClimbState.climbLock;
            }else{
                state.climbState = State.ClimbState.doNothing;
            }
            drive.apllyState(state);
            climb.apllyState(state);
        }

    }


}
