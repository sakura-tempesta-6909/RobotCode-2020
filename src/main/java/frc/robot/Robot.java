/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

//TalonSRX&VictorSPXのライブラリー
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

public class Robot extends TimedRobot {

  //コントローラー
  //とりあえず、Xbox2つ
  XboxController driver, operator;

  //DriveMotor
  WPI_TalonSRX  driveRightFront,driveLeftFront;
  VictorSPX driveRightBack,driveLeftBack;

  //センサー
  ADXRS450_Gyro gyro;

  //SubClass
  Drive drive;
  State state;

  @Override
  public void robotInit() {

    //コントローラーの初期化
    driver = new XboxController(Const.DriveControllerPort);
    operator = new XboxController(Const.OperateControllerPort);

    //gyroの初期化
    gyro = new ADXRS450_Gyro();

    //DriveMotor
    //ドライブモーターの初期化
    driveRightFront = new WPI_TalonSRX(Const.DriveRightFrontPort);
    driveRightBack = new VictorSPX(Const.DriveRightBackPort);
    driveLeftFront = new WPI_TalonSRX(Const.DriveLeftFrontPort);
    driveLeftBack = new VictorSPX(Const.DriveLeftBackPort);
    //ドライブモーターの台形加速&フォローの設定
    driveLeftFront.configOpenloopRamp(Const.DriveFullSpeedTime);
    driveLeftBack.follow(driveLeftFront);
    driveRightFront.configOpenloopRamp(Const.DriveFullSpeedTime);
    driveRightBack.follow(driveRightFront);


    drive = new Drive(driveLeftFront, driveRightFront, gyro);
    state = new State();

  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
    driveLeftFront.configSelectedFeedbackSensor(FeedbackDevice.Analog,0,0);
    driveRightFront.configSelectedFeedbackSensor(FeedbackDevice.Analog,0,0);
    driveLeftFront.setSensorPhase(true);
    driveRightFront.setSensorPhase(true);
    driveRightFront.setInverted(false);
    driveLeftFront.setInverted(false);
    /* Config the peak and nominal outputs, 12V means full */
    driveLeftFront.configNominalOutputForward(0,0);
    driveLeftFront.configNominalOutputReverse(0,0);
    driveLeftFront.configPeakOutputForward(1,0);
    driveLeftFront.configPeakOutputReverse(-1,0);
    driveRightFront.configNominalOutputForward(0,0);
    driveRightFront.configNominalOutputReverse(0,0);
    driveRightFront.configPeakOutputForward(1,0);
    driveRightFront.configPeakOutputReverse(-1,0);
    driveLeftFront.configAllowableClosedloopError(0,0,0);
    /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
    driveLeftFront.config_kF(0,0.0,0);
    driveLeftFront.config_kP(0,0.15,0);
    driveLeftFront.config_kI(0,0.0,0);
    driveLeftFront.config_kD(0,1.0,0);
    double targetPositionRotations = 0.2 * 10.0 * 4096;
    driveLeftFront.set(ControlMode.Position, targetPositionRotations);
    driveRightFront.set(ControlMode.Position, targetPositionRotations);

  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopPeriodic() {

        /********** Drive ***********/
          state.driveState = State.DriveState.kManual;
          state.driveStraightSpeed = Util.deadbandProcessing(driver.getY(Hand.kLeft));
          state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(Hand.kRight));

          drive.apllyState(state);
        }

  @Override
  public void testPeriodic() {
  }
}
