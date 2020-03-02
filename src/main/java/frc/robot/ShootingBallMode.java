package frc.robot;

import frc.robot.subClass.*;

public class ShootingBallMode {
    Drive drive;
    Shooter shooter;
    IntakeBelt intakeBelt;
    Intake intake;
    Arm arm;

    ShootingBallMode(Drive drive, Shooter shooter, Arm arm, IntakeBelt intakeBelt, Intake intake) {
        this.drive = drive;
        this.shooter = shooter;
        this.arm = arm;
        this.intakeBelt = intakeBelt;
        this.intake = intake;
    }

    public void applyMode(State state) {
        drive.applyState(state);
        arm.applyState(state);
        intake.applyState(state);
        intakeBelt.applyState(state);
        shooter.applyState(state);

    }
}
