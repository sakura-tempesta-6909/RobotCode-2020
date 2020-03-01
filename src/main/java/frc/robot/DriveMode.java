package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class DriveMode {
    Drive drive;
    Intake intake;
    IntakeBelt intakeBelt;
    Shooter shooter;
    XboxController driver, operator;

    DriveMode(Drive drive, Intake intake, IntakeBelt intakeBelt, Shooter shooter, Controller controller) {
        this.drive = drive;
        this.intake = intake;
        this.intakeBelt = intakeBelt;
        this.shooter = shooter;
        this.driver = controller.driver;
        this.operator = controller.operator;
    }

    public void applyMode(State state) {
            drive.applyState(state);
            intake.applyState(state);
            intakeBelt.applyState(state);
            shooter.applyState(state);
    }
}
