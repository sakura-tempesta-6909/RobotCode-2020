package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;

//TalonSRX&VictorSPXのライブラリー
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.*;
import edu.wpi.first.cameraserver.CameraServer;

public class Robot extends TimedRobot {

    //コントローラー
    //とりあえず、Xbox2つ
    XboxController driver, operator;
    Joystick _joy;

    //DriveMotor
    WPI_TalonSRX  driveRightFront,driveLeftFront;
    VictorSPX driveRightBack,driveLeftBack;

    //ShooterMortor
    TalonSRX shooterLeft, shooterRight;

    //IntakaMortor
    VictorSPX intakeBeltFront,intakeBeltBack ;
    VictorSPX intake;

    //センサー
    DigitalInput intake_f ,intake_b;

    //カメラ
    CameraServer camera;

    //SubClass
    Drive drive;
    State state;
    Shooter shooter;

    @Override
    public void robotInit() {

        intake_f = new DigitalInput(0);
        intake_b = new DigitalInput(1);
        shooterLeft = new TalonSRX(4);
        shooterRight = new TalonSRX(5);

        //IntakeBelt
        intakeBeltFront = new VictorSPX(11);
        intakeBeltBack = new VictorSPX(15);
        intake = new VictorSPX(14);

        //コントローラーの初期化
        driver = new XboxController(2);
        _joy = new Joystick(0);

        //cameraの初期化
        camera = CameraServer.getInstance();
        camera.startAutomaticCapture();

        //gyroの初期化
        //gyro = new ADXRS450_Gyro();

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


        intakeBeltBack.follow(intakeBeltFront);
        /* Factory Default all hardware to prevent unexpected behaviour */
        shooterLeft.configFactoryDefault();
        shooterLeft.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Const.kPIDLoopIdx,
                Const.kTimeoutMs);

        shooterRight.configFactoryDefault();
        shooterRight.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Const.kPIDLoopIdx,
                Const.kTimeoutMs);


        shooterLeft.setSensorPhase(true);

        /*
        初期値が確認できたら、削除予定
        shooterLeft.configNominalOutputForward(0, Const.kTimeoutMs);
        shooterLeft.configNominalOutputReverse(0, Const.kTimeoutMs);
        shooterLeft.configPeakOutputForward(1, Const.kTimeoutMs);
        shooterLeft.configPeakOutputReverse(-1, Const.kTimeoutMs);
        */
        shooterLeft.config_kF(Const.kPIDLoopIdx, Const.kGains_Velocit.kF, Const.kTimeoutMs);
        shooterLeft.config_kP(Const.kPIDLoopIdx, Const.kGains_Velocit.kP, Const.kTimeoutMs);
        shooterLeft.config_kI(Const.kPIDLoopIdx, Const.kGains_Velocit.kI, Const.kTimeoutMs);
        shooterLeft.config_kD(Const.kPIDLoopIdx, Const.kGains_Velocit.kD, Const.kTimeoutMs);


        shooterRight.setSensorPhase(true);
        /*
        初期値が確認できたら、削除予定
        shooterRight.configNominalOutputForward(0, Const.kTimeoutMs);
        shooterRight.configNominalOutputReverse(0, Const.kTimeoutMs);
        shooterRight.configPeakOutputForward(1, Const.kTimeoutMs);
        shooterRight.configPeakOutputReverse(-1, Const.kTimeoutMs);
         */
        shooterRight.config_kF(Const.kPIDLoopIdx, Const.kGains_Velocit.kF, Const.kTimeoutMs);
        shooterRight.config_kP(Const.kPIDLoopIdx, Const.kGains_Velocit.kP, Const.kTimeoutMs);
        shooterRight.config_kI(Const.kPIDLoopIdx, Const.kGains_Velocit.kI, Const.kTimeoutMs);
        shooterRight.config_kD(Const.kPIDLoopIdx, Const.kGains_Velocit.kD, Const.kTimeoutMs);

        shooterLeft.configMaxIntegralAccumulator(Const.kPIDLoopIdx,Const.kGains_Velocit.MaxIntegralAccumulator);
        shooterRight.configMaxIntegralAccumulator(Const.kPIDLoopIdx,Const.kGains_Velocit.MaxIntegralAccumulator);




        drive = new Drive(driveLeftFront, driveRightFront);
        state = new State();

    }

    @Override
    public void robotPeriodic() {
    }

    @Override
    public void autonomousInit() {
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
        /* Config Position Closed Loop gains in slot0, tsypically kF stays zero. */
        driveLeftFront.config_kF(0,0.0,0);
        driveLeftFront.config_kP(0,0.15,0);
        driveLeftFront.config_kI(0,0.0,0);
        driveLeftFront.config_kD(0,1.0,0);

        driveRightFront.config_kF(0,0.0,0);
        driveRightFront.config_kP(0,0.15,0);
        driveRightFront.config_kI(0,0.0,0);
        driveRightFront.config_kD(0,1.0,0);

    }

    @Override
    public void autonomousPeriodic() {
        double targetPositionRotations = Util.deadbandProcessing(_joy.getY()) * 10.0 * 4096;
        driveLeftFront.set(ControlMode.Position, targetPositionRotations);
        driveRightFront.set(ControlMode.Position, targetPositionRotations);
    }

    @Override
    public void teleopPeriodic() {
        double leftYstick = -1 * _joy.getY();
        double motorOutput = shooterLeft.getMotorOutputPercent();
        if(_joy.getRawButton(3)||_joy.getRawButton(4)){
            if(!intake_f.get()){
                intakeBeltFront.set(ControlMode.PercentOutput,-1);
            }else if (driver.getAButton()){
                intakeBeltFront.set(ControlMode.PercentOutput,1);
            }else{
                intakeBeltFront.set(ControlMode.PercentOutput,0);
            }
        }else{
            intakeBeltFront.set(ControlMode.PercentOutput,0);
        }


        if (_joy.getRawButton(1)) {
            double targetVelocity_UnitsPer100ms;
            if(Math.abs(leftYstick)>0.2){
                targetVelocity_UnitsPer100ms = leftYstick *100000;
            }else{
                targetVelocity_UnitsPer100ms = 0;
            }
            intakeBeltFront.set(ControlMode.PercentOutput,1.0);
            shooterLeft.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
            shooterRight.set(ControlMode.Velocity, -targetVelocity_UnitsPer100ms);
        }else if(_joy.getRawButton(4)){
            intake.set(ControlMode.PercentOutput,-1.0);
            shooterLeft.set(ControlMode.PercentOutput, -0.6);
            shooterRight.set(ControlMode.PercentOutput, 0.6);
        }else  if(_joy.getRawButton(3)){
            shooterLeft.set(ControlMode.PercentOutput, 0.6);
            shooterRight.set(ControlMode.PercentOutput, -0.6);
            intake.set(ControlMode.PercentOutput,1.0);
        }else{
            if(Math.abs(leftYstick)>0.2){
                double targetVelocity_UnitsPer100ms = leftYstick *100000;
                shooterLeft.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
                shooterRight.set(ControlMode.Velocity, -targetVelocity_UnitsPer100ms);
            }else{
                intake.set(ControlMode.PercentOutput,0);
                shooterLeft.set(ControlMode.PercentOutput, 0);
                shooterRight.set(ControlMode.PercentOutput, 0);
            }
        }


        /********** Drive ***********/
        state.driveState = State.DriveState.kManual;
        if(driver.getRawButton(6)){
        state.driveStraightSpeed = Util.deadbandProcessing(-driver.getY(Hand.kLeft)/3);
        state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(Hand.kRight)/3);
        }else if(driver.getRawButton(5)){
          state.driveStraightSpeed = Util.deadbandProcessing(-driver.getY(Hand.kLeft)/2);
          state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(Hand.kRight)/2);
          }else{
          state.driveStraightSpeed = Util.deadbandProcessing(-driver.getY(Hand.kLeft));
          state.driveRotateSpeed = Util.deadbandProcessing(driver.getX(Hand.kRight));
        }
        drive.apllyState(state);
    }

    @Override
    public void testPeriodic() {
        System.out.println("HellowWorld");
        if(!intake_f.get()&&!driver.getAButton()){
            System.out.println("true");
            intakeBeltFront.set(ControlMode.PercentOutput,-1);
            shooterLeft.set(ControlMode.PercentOutput, 0.);
            shooterRight.set(ControlMode.PercentOutput, 0);
        }else if (driver.getAButton()){
            intakeBeltFront.set(ControlMode.PercentOutput,1);
            shooterLeft.set(ControlMode.PercentOutput, 0.3);
            shooterRight.set(ControlMode.PercentOutput, -0.3);
            System.out.println("false");
        }else{
            intakeBeltFront.set(ControlMode.PercentOutput,0);
            shooterLeft.set(ControlMode.PercentOutput, 0);
            shooterRight.set(ControlMode.PercentOutput, 0);
            System.out.println("false");
        }
        if (driver.getXButton()){
            shooterLeft.set(ControlMode.PercentOutput, -0.3);
            shooterRight.set(ControlMode.PercentOutput, 0.3);
        }
        SmartDashboard.putBoolean("intake_f",intake_f.get());
        SmartDashboard.putBoolean("intake_b",intake_b.get());
    }
}
