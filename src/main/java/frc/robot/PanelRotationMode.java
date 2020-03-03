package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class PanelRotationMode {
    Drive drive;
    Panel panel;
    Arm arm;

    PanelRotationMode(Drive drive, Panel panel, Arm arm) {
        this.drive = drive;
        this.panel = panel;
        this.arm = arm;
    }


    public void applyMode(State state) {
        drive.applyState(state);
        panel.applyState(state);
        arm.applyState(state);
    }

}
