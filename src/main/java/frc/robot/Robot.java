package frc.robot;

import com.ctre.phoenix.motorcontrol.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.Servo;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID;
import frc.robot.subClass.*;

//TalonSRX&VictorSPXのライブラリー

public class Robot extends TimedRobot {

    //コントローラー
    //とりあえず、XboxController2つ
    XboxController driver, operator;
    Joystick joystick;
    Controller controller;

    //DriveMotor
    WPI_TalonSRX driveRightFrontMotor, driveLeftFrontMotor;
    VictorSPX driveRightBackMotor, driveLeftBackMotor;

    //ShooterMotor
    TalonSRX shooterLeftMotor, shooterRightMotor;

    TalonSRX armMotor;
    //IntakeMotor
    VictorSPX intakeBeltFrontMotor, intakeBeltBackMotor;
    VictorSPX intakeMotor;

    //climbMotor
    TalonSRX climbMotor;
    Servo climbServo;
    TalonSRX slideMotor;


    //センサー
    DigitalInput intakeFrontSensor, intakeBackSensor;
    SensorCollection armEncoder;

    //カメラ
    CameraServer camera;

    //SubClass
    Drive drive;
    State state;
    Shooter shooter;
    Intake intake;
    IntakeBelt intakeBelt;
    Climb climb;
    Arm arm;
    ArmSensor armSensor;
    Panel panel;

    //モード
    PanelRotationMode panelRotationMode;
    ShootingBallMode shootingBallMode;
    DriveMode driveMode;
    ClimbMode climbMode;

    @Override
    public void robotInit() {

        //Intakeセンサー
        intakeFrontSensor = new DigitalInput(Const.IntakeBeltSensorFrontPort);
        intakeBackSensor = new DigitalInput(Const.IntakeBeltSensorBackPort);

        //シューターのモーター
        shooterLeftMotor = new TalonSRX(Const.shooterLeftMotor);
        shooterRightMotor = new TalonSRX(Const.shooterRightMotor);

        //アームのモーター
        armMotor = new TalonSRX(Const.armMotor);
        //アームのセンサー
        armMotor.configForwardLimitSwitchSource(LimitSwitchSource.RemoteCANifier, LimitSwitchNormal.NormallyOpen);
        armMotor.configReverseLimitSwitchSource(LimitSwitchSource.RemoteCANifier, LimitSwitchNormal.NormallyOpen);
        armEncoder = new SensorCollection(armMotor);

        //IntakeBelt
        intakeBeltFrontMotor = new VictorSPX(Const.intakeBeltFrontMotor);
        intakeBeltBackMotor = new VictorSPX(Const.intakeBeltBackMotor);
        intakeMotor = new VictorSPX(Const.IntakeMotorPort);

        //IntakeBeltのフォローの設定　※しない
        //intakeBeltBackMotor.follow(intakeBeltFrontMotor);

        //Climb Motor
        climbMotor = new TalonSRX(Const.climbMotorPort);
        climbServo = new Servo(Const.climbServoPort);
        slideMotor = new TalonSRX(Const.climbSlideMotor);

        //コントローラーの初期化
        operator = new XboxController(Const.OperateControllerPort);
        driver = new XboxController(Const.DriveControllerPort);
        //joystick = new Joystick(Const.JoystickPort);
        controller = new Controller(driver, operator);

        //cameraの初期化
        camera = CameraServer.getInstance();
        camera.startAutomaticCapture();

        //ドライブモーターの初期化
        driveRightFrontMotor = new WPI_TalonSRX(Const.DriveRightFrontPort);
        driveRightBackMotor = new VictorSPX(Const.DriveRightBackPort);
        driveLeftFrontMotor = new WPI_TalonSRX(Const.DriveLeftFrontPort);
        driveLeftBackMotor = new VictorSPX(Const.DriveLeftBackPort);

        //ドライブモーターの台形加速&フォローの設定
        driveLeftFrontMotor.configOpenloopRamp(Const.DriveFullSpeedTime);
        driveLeftBackMotor.follow(driveLeftFrontMotor);
        driveRightFrontMotor.configOpenloopRamp(Const.DriveFullSpeedTime);
        driveRightBackMotor.follow(driveRightFrontMotor);

        //シューターの設定を初期化
        shooterRightMotor.configFactoryDefault();
        shooterLeftMotor.configFactoryDefault();

        //シューターのPIDの設定
        shooterLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Const.kPIDLoopIdx,
                Const.kTimeoutMs);
        shooterRightMotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Const.kPIDLoopIdx,
                Const.kTimeoutMs);
        shooterLeftMotor.config_kF(Const.kPIDLoopIdx, Const.kGains_Velocit.kF, Const.kTimeoutMs);
        shooterLeftMotor.config_kP(Const.kPIDLoopIdx, Const.kGains_Velocit.kP, Const.kTimeoutMs);
        shooterLeftMotor.config_kI(Const.kPIDLoopIdx, Const.kGains_Velocit.kI, Const.kTimeoutMs);
        shooterLeftMotor.config_kD(Const.kPIDLoopIdx, Const.kGains_Velocit.kD, Const.kTimeoutMs);

        shooterRightMotor.config_kF(Const.kPIDLoopIdx, Const.kGains_Velocit.kF, Const.kTimeoutMs);
        shooterRightMotor.config_kP(Const.kPIDLoopIdx, Const.kGains_Velocit.kP, Const.kTimeoutMs);
        shooterRightMotor.config_kI(Const.kPIDLoopIdx, Const.kGains_Velocit.kI, Const.kTimeoutMs);
        shooterRightMotor.config_kD(Const.kPIDLoopIdx, Const.kGains_Velocit.kD, Const.kTimeoutMs);

        shooterLeftMotor.configMaxIntegralAccumulator(Const.kPIDLoopIdx, Const.kGains_Velocit.MaxIntegralAccumulator);
        shooterRightMotor.configMaxIntegralAccumulator(Const.kPIDLoopIdx, Const.kGains_Velocit.MaxIntegralAccumulator);

        shooterRightMotor.setSensorPhase(true);
        shooterLeftMotor.setSensorPhase(true);

        //Armの設定を初期化
        armMotor.configFactoryDefault();

        //ArmのPID設定
        armMotor.configSelectedFeedbackSensor(FeedbackDevice.Analog,
                Const.kArmPIDLoopIdx,
                Const.kTimeoutMs);

        armMotor.config_kF(Const.kArmPIDLoopIdx, Const.kGains_ArmPosition.kF, Const.kTimeoutMs);
        armMotor.config_kP(Const.kArmPIDLoopIdx, Const.kGains_ArmPosition.kP, Const.kTimeoutMs);
        armMotor.config_kI(Const.kArmPIDLoopIdx, Const.kGains_ArmPosition.kI, Const.kTimeoutMs);
        armMotor.config_kD(Const.kArmPIDLoopIdx, Const.kGains_ArmPosition.kD, Const.kTimeoutMs);

        armMotor.configMaxIntegralAccumulator(Const.kPIDLoopIdx, Const.kGains_ArmPosition.MaxIntegralAccumulator);

        armMotor.setSensorPhase(true);
        armMotor.setInverted(true);
        /*
        初期値が確認できたら、削除予定
        ShooterLeft.configNominalOutputForward(0, Const.kTimeoutMs);
        shooterLeft.configNominalOutputReverse(0, Const.kTimeoutMs);
        shooterLeft.configPeakOutputForward(1, Const.kTimeoutMs);
        shooterLeft.configPeakOutputReverse(-1, Const.kTimeoutMs);
        */

        /*
        初期値が確認できたら、削除予定
        shooterRight.configNominalOutputForward(0, Const.kTimeoutMs);
        shooterRight.configNominalOutputReverse(0, Const.kTimeoutMs);
        shooterRight.configPeakOutputForward(1, Const.kTimeoutMs);
        shooterRight.configPeakOutputReverse(-1, Const.kTimeoutMs);
         */

        //サブクラスの生成
        armSensor = new ArmSensor(armMotor);
        arm = new Arm(armMotor, armEncoder, armSensor);
        drive = new Drive(driveLeftFrontMotor, driveRightFrontMotor);
        shooter = new Shooter(shooterRightMotor, shooterLeftMotor);
        intake = new Intake(intakeMotor);
        intakeBelt = new IntakeBelt(intakeBeltFrontMotor, intakeBeltBackMotor, intakeFrontSensor, intakeBackSensor);
        panel = new Panel(shooter);
        state = new State();
        climb = new Climb(climbMotor, climbServo, slideMotor, arm);

        //モードのクラスの生成
        driveMode = new DriveMode(drive, intake, intakeBelt, shooter, arm);
        panelRotationMode = new PanelRotationMode(drive, panel, arm);
        shootingBallMode = new ShootingBallMode(drive, shooter, arm, intakeBelt, intake);
        climbMode = new ClimbMode(drive, arm, climb);
    }

    @Override
    public void robotPeriodic() {
        //処理なし
    }

    @Override
    public void autonomousInit() {

        /*
        driveLeftFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);
        driveRightFront.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,0);

        driveLeftFront.setSensorPhase(true);
        driveRightFront.setSensorPhase(true);

        driveRightFront.setInverted(false);
        driveLeftFront.setInverted(false);

        driveLeftFront.configNominalOutputForward(0,0);
        driveLeftFront.configNominalOutputReverse(0,0);
        driveLeftFront.configPeakOutputForward(1,0);
        driveLeftFront.configPeakOutputReverse(-1,0);

        driveRightFront.configNominalOutputForward(0,0);
        driveRightFront.configNominalOutputReverse(0,0);
        driveRightFront.configPeakOutputForward(1,0);
        driveRightFront.configPeakOutputReverse(-1,0);

        driveLeftFront.configAllowableClosedloopError(0,0,0);

        driveRightFront.configAllowableClosedloopError(0,0,0);
        driveLeftFront.config_kF(0,0.0,0);
        driveLeftFront.config_kP(0,0.15,0);
        driveLeftFront.config_kI(0,0.0,0);
        driveLeftFront.config_kD(0,1.0,0);

        driveRightFront.config_kF(0,0.0,0);
        driveRightFront.config_kP(0,0.15,0);
        driveRightFront.config_kI(0,0.0,0);
        driveRightFront.config_kD(0,1.0,0);
        */

    }

    @Override
    public void autonomousPeriodic() {
        /*
        double targetPositionRotations = Util.deadbandProcessing(_joy.getY()) * 10.0 * 4096;
        driveLeftFront.set(ControlMode.Position, targetPositionRotations);
        driveRightFront.set(ControlMode.Position, targetPositionRotations);
         */
    }

    public  void  teleopInit() {
        state.controlMode = State.ControlMode.m_Drive;
    }

    @Override
    public void teleopPeriodic() {

        //状態初期化
        state.stateInit();

        //Mode Change
        state.changeMode(controller);

        switch (state.controlMode) {
            case m_Drive:
                //ほかに関係なくドライブ
                state.driveState = State.DriveState.kManual;
                state.driveStraightSpeed = Util.deadbandProcessing(-driver.getY(GenericHID.Hand.kLeft));
                state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(GenericHID.Hand.kRight));

                if (Util.deadbandCheck(driver.getTriggerAxis(GenericHID.Hand.kLeft))) {
                    //D LT ボールを取り込む
                    state.intakeState = State.IntakeState.kIntake;
                    state.intakeBeltState = State.IntakeBeltState.kIntake;
                    state.shooterState = State.ShooterState.kintake;
                } else if (Util.deadbandCheck(driver.getTriggerAxis(GenericHID.Hand.kRight))) {
                    //D RT ボールを出す
                    state.intakeState = State.IntakeState.kOuttake;
                    state.intakeBeltState = State.IntakeBeltState.kOuttake;
                    state.shooterState = State.ShooterState.kouttake;
                } else if (driver.getBumper(GenericHID.Hand.kRight)) {
                    //D RB アームを平行（パネルくぐり）
                    state.armState = State.ArmState.k_Parallel;
                }
                if (Util.deadbandCheck(operator.getTriggerAxis(GenericHID.Hand.kLeft))) {
                    //O LT 砲台の角度をゴールへ調節する
                    state.armState = State.ArmState.k_Shoot;
                }

                driveMode.applyMode(state);
                break;

            case m_ShootingBall:
                state.armState = State.ArmState.k_Conserve;
                state.driveState = State.DriveState.kdoNothing;
                if (Util.deadbandCheck(operator.getTriggerAxis(GenericHID.Hand.kRight))) {
                    //O RT ボールを飛ばす
                    state.shooterState = State.ShooterState.kshoot;
                    state.shooterPIDSpeed = -operator.getTriggerAxis(GenericHID.Hand.kRight);
                    state.intakeBeltState = State.IntakeBeltState.kOuttake;
                } else if (Util.deadbandCheck(driver.getX(GenericHID.Hand.kLeft))) {
                    //D Stick ドライブを少し動かす
                    state.driveState = State.DriveState.kLow;
                    state.driveRotateSpeed = driver.getX(GenericHID.Hand.kLeft);
                    state.driveStraightSpeed = driver.getY(GenericHID.Hand.kRight);
                } else if (Util.deadbandCheck(operator.getTriggerAxis(GenericHID.Hand.kLeft))) {
                    //O LT 砲台の角度をゴールへ調節する
                    state.armState = State.ArmState.k_Shoot;
                    state.setArmAngle = Const.armShootAngle;
                } else if (Util.deadbandCheck(operator.getX(GenericHID.Hand.kLeft))) {
                    //O LStick X 砲台の角度を手動で調節, 正か負のみ
                    state.armState = State.ArmState.k_Adjust;
                    state.armMotorSpeed = operator.getX(GenericHID.Hand.kLeft);
                }
                    /*if(Util.deadbandCheck(operator.getTriggerAxis(GenericHID.Hand.kRight))&&Util.deadbandCheck(operator.getTriggerAxis(GenericHID.Hand.kLeft))){
                        state.intakeBeltState = State.IntakeBeltState.kouttake;
                    }*/
                shootingBallMode.applyMode(state);

                break;

            case m_Climb:
                //Drive
                state.driveState = State.DriveState.kLow;
                state.driveStraightSpeed = Util.deadbandProcessing(-driver.getY(GenericHID.Hand.kLeft));
                state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(GenericHID.Hand.kRight));

                //Climb
                state.armState = State.ArmState.k_Conserve;
                if (operator.getYButton()) {
                    //O Y クライムの棒を伸ばす
                    state.climbState = State.ClimbState.climbExtend;
                    state.climbExtendAdjustSpeed = -operator.getY(GenericHID.Hand.kRight)/10;
                } else if (operator.getBButton()) {
                    //O B クライムする
                    state.climbState = State.ClimbState.climbShrink;
                } else if(Util.deadbandCheck(operator.getX(GenericHID.Hand.kLeft))) {
                    //O LStick X スライド
                    state.climbState =  State.ClimbState.climbSlide;
                    /*if(Util.deadbandCheck(operator.getTriggerAxis(GenericHID.Hand.kLeft))){
                        //O LT 高出力でスライド
                        state.climbSlideMotorSpeed = -operator.getX(GenericHID.Hand.kLeft);
                        System.out.println("Highhhhhhhhhhhhhhhhhhhhhhhh");
                    }else */{
                        //1/2の出力でスライド
                        state.climbSlideMotorSpeed = -operator.getX(GenericHID.Hand.kLeft) / 2;
                    }
                } else if (Util.deadbandCheck(operator.getTriggerAxis(GenericHID.Hand.kRight))) {
                    //O RT クライムの棒をロック
                    state.climbState = State.ClimbState.climbLock;
                } else if (operator.getAButton()) {
                    //O A(仮) Climb Motorだけ縮める
                    state.climbState = State.ClimbState.climbMotorOnlyShrink;
                } else if (operator.getXButton()) {
                    //O X（仮）　Climb　Motorだけ伸ばす
                    state.climbState = State.ClimbState.climbMotorOnlyExtend;
                }


                climb.changeState(state);

                climbMode.applyMode(state);
                break;

            case m_PanelRotation:
                state.armState = State.ArmState.k_Panel;
                    if (driver.getXButton()) {
                        //D X 赤に合わせる
                        state.panelState = State.PanelState.p_toRed;
                    } else if (driver.getYButton()) {
                        //D Y 緑に合わせる
                        state.panelState = State.PanelState.p_toGreen;
                    } else if (driver.getBButton()) {
                        //D B 青に合わせる
                        state.panelState = State.PanelState.p_toBlue;
                    } else if (driver.getAButton()) {
                        //D A 黄色に合わせる
                        state.panelState = State.PanelState.p_toYellow;
                    } else if (Util.deadbandCheck(driver.getTriggerAxis(GenericHID.Hand.kLeft))) {
                        //D LT 手動左回転
                        state.panelState = State.PanelState.p_ManualRot;
                        state.panelManualSpeed = -Const.panelRotateSpeed;
                    } else if (Util.deadbandCheck(driver.getTriggerAxis(GenericHID.Hand.kRight))) {
                        //D RT 手動右回転
                        state.panelState = State.PanelState.p_ManualRot;
                        state.panelManualSpeed = Const.panelRotateSpeed;
                    }
                panelRotationMode.applyMode(state);
                break;
        }
        /*
        driveMode.applyMode(state);
        shootingBallMode.applyMode(state);
        panelRotationMode.applyMode(state);
        climbMode.applyMode(state);   
        */

    }

    @Override
    public void testPeriodic() {
    }
}
