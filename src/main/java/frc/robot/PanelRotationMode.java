package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class PanelRotationMode {
    Drive drive;
    Panel panel;
    XboxController driver,operator;

    PanelRotationMode(Drive drive,Panel panel, Controller controller){
        this.drive = drive;
        this.panel = panel;
        this.driver = controller.driver;
        this.operator = controller.operator;
    }

    public void applyMode(State state){
        if(state.controlState == State.ControlState.m_PanelRotation){

        }

    }

}
