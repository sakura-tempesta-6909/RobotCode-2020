package frc.robot;

import frc.robot.subClass.*;

public class DriveMode {
    Drive drive;
    Intake intake;
    IntakeBelt intakeBelt;
    Shooter shooter;
    Arm arm;

    DriveMode(Drive drive, Intake intake, IntakeBelt intakeBelt, Shooter shooter, Arm arm) {
        this.drive = drive;
        this.intake = intake;
        this.intakeBelt = intakeBelt;
        this.shooter = shooter;
        this.arm = arm;
    }



    public void applyMode(State state) {
        drive.applyState(state);
        arm.applyState(state);
        intake.applyState(state);
        intakeBelt.applyState(state);
        shooter.applyState(state);

    }
}
