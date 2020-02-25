package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import frc.robot.subClass.*;

public class ShootingBallMode {
    Drive drive;
    Shooter shooter;
    Arm arm;
    XboxController driver,operator;

    ShootingBallMode(Drive drive, Shooter shooter,Arm arm, Controller controller){
        this.drive = drive;
        this.shooter = shooter;
        this.arm = arm;
        this.driver = controller.driver;
        this.operator = controller.operator;
    }

    public void applyMode(State state){
        if(state.controlState == State.ControlState.m_ShootingBall){

        }

    }
}
