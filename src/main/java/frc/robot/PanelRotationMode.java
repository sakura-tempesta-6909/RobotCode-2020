package frc.robot;


import frc.robot.subClass.*;

public class PanelRotationMode {
    Drive drive;
    Shooter shooter;
    Arm arm;

    PanelRotationMode(Drive drive, Shooter shooter, Arm arm) {
        this.drive = drive;
        this.shooter = shooter;
        this.arm = arm;
    }


    public void applyMode(State state) {
        drive.applyState(state);
        arm.applyState(state);
        shooter.applyState(state);
    }

}
