package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.Controller;
import frc.robot.subClass.Drive;
import frc.robot.subClass.Intake;
import frc.robot.subClass.State;

public class DriveMode {
    Drive drive;
    Intake intake;
    XboxController driver,operator;

    DriveMode(Drive drive, Intake intake, Controller controller){
        this.drive = drive;
        this.intake = intake;
        this.driver = controller.driver;
        this.operator = controller.operator;
    }

    public void applyMode(State state){
        if(state.controlState == State.ControlState.m_Drive){
            
        }

    }
}
