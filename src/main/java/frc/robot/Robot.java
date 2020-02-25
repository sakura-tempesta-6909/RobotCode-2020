package frc.robot;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;
import com.ctre.phoenix.motorcontrol.LimitSwitchSource;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.cameraserver.CameraServer;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
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
    VictorSPX intakeBeltFrontMortor, intakeBeltBackMotor;
    VictorSPX intakeMotor;

    //センサー
    DigitalInput intakeFrontSensor, intakeBackSensor;

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
        intakeFrontSensor = new DigitalInput(0);
        intakeBackSensor = new DigitalInput(1);

        //シューターのモーター
        shooterLeftMotor = new TalonSRX(4);
        shooterRightMotor = new TalonSRX(5);

        //アームのモーター
        armMotor = new TalonSRX(1);
        armMotor.configForwardLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);
        armMotor.configReverseLimitSwitchSource(LimitSwitchSource.Deactivated, LimitSwitchNormal.NormallyOpen);

        //IntakeBelt
        intakeBeltFrontMortor = new VictorSPX(11);
        intakeBeltBackMotor = new VictorSPX(15);
        intakeMotor = new VictorSPX(14);

        //IntakeBeltのフォローの設定
        intakeBeltBackMotor.follow(intakeBeltFrontMortor);

        //コントローラーの初期化
        operator = new XboxController(1);
        driver = new XboxController(2);
        joystick = new Joystick(0);
        controller = new Controller(driver,operator);

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

        shooterLeftMotor.configMaxIntegralAccumulator(Const.kPIDLoopIdx,Const.kGains_Velocit.MaxIntegralAccumulator);
        shooterRightMotor.configMaxIntegralAccumulator(Const.kPIDLoopIdx,Const.kGains_Velocit.MaxIntegralAccumulator);

        shooterRightMotor.setSensorPhase(true);
        shooterLeftMotor.setSensorPhase(true);

        /*
        初期値が確認できたら、削除予定
        shooterLeft.configNominalOutputForward(0, Const.kTimeoutMs);
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
        arm = new Arm(armMotor,armSensor);
        drive = new Drive(driveLeftFrontMotor, driveRightFrontMotor);
        shooter = new Shooter(shooterRightMotor,shooterLeftMotor);
        intake = new Intake(intakeMotor);
        intakeBelt = new IntakeBelt(intakeBeltFrontMortor,intakeFrontSensor,intakeBackSensor);
        panel = new Panel(shooter);
        state = new State();

        //モードのクラスの生成
        driveMode = new DriveMode(drive,intake,intakeBelt,controller);
        panelRotationMode = new PanelRotationMode(drive,panel,controller);
        shootingBallMode = new ShootingBallMode(drive,shooter,arm,controller);
        climbMode = new ClimbMode(drive,arm,climb,controller);
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

    @Override
    public void teleopPeriodic() {
        climbMode.applyMode(state);
        driveMode.applyMode(state);
        panelRotationMode.applyMode(state);
        shootingBallMode.applyMode(state);
    }

    @Override
    public void testPeriodic() {
    }
}
