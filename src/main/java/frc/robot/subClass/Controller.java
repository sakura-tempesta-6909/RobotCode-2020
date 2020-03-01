package frc.robot.subClass;

import edu.wpi.first.wpilibj.XboxController;

public class Controller {
    public XboxController driver, operator;

    public Controller(XboxController driver, XboxController operator) {
        this.driver = driver;
        this.operator = operator;
    }
}
