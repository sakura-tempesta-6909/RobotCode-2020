package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class ShootingBallMode {
    Drive drive;
    Shooter shooter;
    IntakeBelt intakeBelt;
    Arm arm;
    XboxController driver, operator;

    ShootingBallMode(Drive drive, Shooter shooter, Arm arm, IntakeBelt intakeBelt, Controller controller) {
        this.drive = drive;
        this.shooter = shooter;
        this.arm = arm;
        this.driver = controller.driver;
        this.operator = controller.operator;
        this.intakeBelt = intakeBelt;
    }

    public void applyMode(State state) {
            drive.applyState(state);
            shooter.applyState(state);
            arm.applyState(state);
            intakeBelt.applyState(state);
    }
}
