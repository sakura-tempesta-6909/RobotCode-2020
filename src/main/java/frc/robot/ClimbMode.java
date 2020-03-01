package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class ClimbMode {
    Drive drive;
    Arm arm;
    Climb climb;
    XboxController driver, operator;

    ClimbMode(Drive drive, Arm arm, Climb climb, Controller controller) {
        this.drive = drive;
        this.arm = arm;
        this.climb = climb;
        this.driver = controller.driver;
        this.operator = controller.operator;
    }

    public void applyMode(State state) {
            drive.applyState(state);
            climb.applyState(state);
            arm.applyState(state);
    }
}
