package frc.robot;

import frc.robot.subClass.*;

public class ClimbMode {
    Drive drive;
    Arm arm;
    Climb climb;

    ClimbMode(Drive drive, Arm arm, Climb climb) {
        this.drive = drive;
        this.arm = arm;
        this.climb = climb;
    }



    public void applyMode(State state) {
        drive.applyState(state);
        arm.applyState(state);
        //climb.applyState(state);
    }
}
