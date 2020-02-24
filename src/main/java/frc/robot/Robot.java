package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.interfaces.*;

//TalonSRX&VictorSPXのライブラリー
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.*;

import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.DigitalInput;

import edu.wpi.first.wpilibj.smartdashboard.*;

import edu.wpi.first.wpilibj.*;
public class Robot extends TimedRobot {

    //コントローラー
    //とりあえず、Xbox2つ
    XboxController driver, operator;

    //DriveMotor
    WPI_TalonSRX  driveRightFront,driveLeftFront;
    VictorSPX driveRightBack,driveLeftBack;

    //センサー
    //Gyro gyro;

    //SubClass
    Drive drive;
    State state;

    TalonSRX _talon;
    TalonSRX _talon_s ;

    VictorSPX ibf ;
    Joystick _joy;
    VictorSPX ibb ;
    VictorSPX intake ;
    DigitalInput intake_f,intake_b;
    CameraServer camera;

    @Override
    public void robotInit() {
        intake_f = new DigitalInput(0);
        intake_b = new DigitalInput(1);
        _talon = new TalonSRX(4);
        _talon_s = new TalonSRX(5);

        ibf = new VictorSPX(11);
        ibb = new VictorSPX(15);
        intake = new VictorSPX(14);

        //コントローラーの初期化
        driver = new XboxController(2);
        _joy = new Joystick(0);

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


        ibb.follow(ibf);
        /* Factory Default all hardware to prevent unexpected behaviour */
        _talon.configFactoryDefault();
        _talon.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.kPIDLoopIdx,
                Constants.kTimeoutMs);

        _talon_s.configFactoryDefault();
        _talon_s.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,
                Constants.kPIDLoopIdx,
                Constants.kTimeoutMs);


        _talon.setSensorPhase(true);
        _talon.configNominalOutputForward(0, Constants.kTimeoutMs);
        _talon.configNominalOutputReverse(0, Constants.kTimeoutMs);
        _talon.configPeakOutputForward(1, Constants.kTimeoutMs);
        _talon.configPeakOutputReverse(-1, Constants.kTimeoutMs);
        _talon.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
        _talon.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
        _talon.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
        _talon.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);


        _talon_s.setSensorPhase(true);
        _talon_s.configNominalOutputForward(0, Constants.kTimeoutMs);
        _talon_s.configNominalOutputReverse(0, Constants.kTimeoutMs);
        _talon_s.configPeakOutputForward(1, Constants.kTimeoutMs);
        _talon_s.configPeakOutputReverse(-1, Constants.kTimeoutMs);
        _talon_s.config_kF(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kF, Constants.kTimeoutMs);
        _talon_s.config_kP(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kP, Constants.kTimeoutMs);
        _talon_s.config_kI(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kI, Constants.kTimeoutMs);
        _talon_s.config_kD(Constants.kPIDLoopIdx, Constants.kGains_Velocit.kD, Constants.kTimeoutMs);

        _talon.configMaxIntegralAccumulator(Constants.kPIDLoopIdx,2000000);
        _talon_s.configMaxIntegralAccumulator(Constants.kPIDLoopIdx,2000000);




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
        double motorOutput = _talon.getMotorOutputPercent();
        if(_joy.getRawButton(3)||_joy.getRawButton(4)){
            if(!intake_f.get()){
                ibf.set(ControlMode.PercentOutput,-1);
            }else if (driver.getAButton()){
                ibf.set(ControlMode.PercentOutput,1);
            }else{
                ibf.set(ControlMode.PercentOutput,0);
            }
        }else{
            ibf.set(ControlMode.PercentOutput,0);
        }


        if (_joy.getRawButton(1)) {
            double targetVelocity_UnitsPer100ms;
            if(Math.abs(leftYstick)>0.2){
                targetVelocity_UnitsPer100ms = leftYstick *100000;
            }else{
                targetVelocity_UnitsPer100ms = 0;
            }
            ibf.set(ControlMode.PercentOutput,1.0);
            _talon.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
            _talon_s.set(ControlMode.Velocity, -targetVelocity_UnitsPer100ms);
        }else if(_joy.getRawButton(4)){
            intake.set(ControlMode.PercentOutput,-1.0);
            _talon.set(ControlMode.PercentOutput, -0.6);
            _talon_s.set(ControlMode.PercentOutput, 0.6);
        }else  if(_joy.getRawButton(3)){
            _talon.set(ControlMode.PercentOutput, 0.6);
            _talon_s.set(ControlMode.PercentOutput, -0.6);
            intake.set(ControlMode.PercentOutput,1.0);
        }else{
            if(Math.abs(leftYstick)>0.2){
                double targetVelocity_UnitsPer100ms = leftYstick *100000;
                _talon.set(ControlMode.Velocity, targetVelocity_UnitsPer100ms);
                _talon_s.set(ControlMode.Velocity, -targetVelocity_UnitsPer100ms);
            }else{
                intake.set(ControlMode.PercentOutput,0);
                _talon.set(ControlMode.PercentOutput, 0);
                _talon_s.set(ControlMode.PercentOutput, 0);
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
            ibf.set(ControlMode.PercentOutput,-1);
            _talon.set(ControlMode.PercentOutput, 0.);
            _talon_s.set(ControlMode.PercentOutput, 0);
        }else if (driver.getAButton()){
            ibf.set(ControlMode.PercentOutput,1);
            _talon.set(ControlMode.PercentOutput, 0.3);
            _talon_s.set(ControlMode.PercentOutput, -0.3);
            System.out.println("false");
        }else{
            ibf.set(ControlMode.PercentOutput,0);
            _talon.set(ControlMode.PercentOutput, 0);
            _talon_s.set(ControlMode.PercentOutput, 0);
            System.out.println("false");
        }
        if (driver.getXButton()){
            _talon.set(ControlMode.PercentOutput, -0.3);
            _talon_s.set(ControlMode.PercentOutput, 0.3);
        }
        SmartDashboard.putBoolean("intake_f",intake_f.get());
        SmartDashboard.putBoolean("intake_b",intake_b.get());
    }
}
