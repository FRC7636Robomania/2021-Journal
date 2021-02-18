// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import edu.wpi.first.wpilibj.Joystick;

import edu.wpi.first.wpilibj.Encoder;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the TimedRobot documentation. If you change the name of this class or
 * the package after creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  //declare items
  private WPI_VictorSPX BRmotor = new WPI_VictorSPX(1);
  private WPI_VictorSPX BLmotor = new WPI_VictorSPX(2);
  private WPI_TalonSRX FRmotor = new WPI_TalonSRX(1);
  private WPI_TalonSRX FLmotor = new WPI_TalonSRX(2);
  private Joystick Crowded = new Joystick(1);
  private int setpoint = 0;

  private final double kDriveTick2Feet = 1.0 / 4096 * 13.5 * Math.PI ; 
  final double kP = 0.002;  
  final double kI = 0.05;
  final double ilimit = 100;

  double setpoint = 0;
  double errorsum = 0;
  double lasttimestamp = 0;

  private Encoder encoder = new Encoder(0,1,true,EncodingType.k4X);
  
  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    BRmotor.configFactoryDefault();
    BLmotor.configFactoryDefault();
    FRmotor.configFactoryDefault();
    FLmotor.configFactoryDefault();
    
    BRmotor.follow(FRmotor);
    BLmotor.follow(FRmotor);
    FLmotor.follow(FRmotor);

    FRmotor.setInverted(true);

    FRmotor.configPeakOutputForward(0.8);
    FRmotor.configPeakOutputReverse(-0.8);

    FRmotor.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
    FRmotor.setSensorPhase(true);
    FRmotor.configAllowableClosedloopError(0, 50, 10);

    FRmotor.config_kP(0, 0.1);
    FRmotor.config_kI(0, 0.1);
    FRmotor.config_kD(0, 0.1);

    
  }

  @Override
  public void robotPeriodic() {}

  @Override
  public void autonomousInit() {}

  @Override
  public void autonomousPeriodic() {}

  @Override
  public void teleopInit() {}

  @Override
  public void teleopPeriodic() {
    if (Crowded.getRawButton(1)){
      setpoint = 500;
    }else if (Crowded.getRawButton(2)){
     setpoint = 0;
    }

    double sensorPosition = encoder.get() * kDriveTick2Feet; 
    
    double error = setpoint - sensorPosition;
    double dt = Timer.getFPGATimestamp() - lasttimestamp;

    if (Math.abs(error) < ilimit){
      errorsum += error * dt;
    }
    SmartDashboard.putNumber("encoder value", encoder.get() * kDriveTick2Feet);

    double outputSpeed = kP * error + kI * errorsum;
    
    RFmotor.set(ControlMode.PercentOutput,-outputSpeed);
    RBmotor.set(ControlMode.PercentOutput,-outputSpeed);
    LFmotor.set(ControlMode.PercentOutput,outputSpeed);
    LBmotor.set(ControlMode.PercentOutput,outputSpeed);
    lasttimestamp = Timer.getFPGATimestamp();

  SmartDashboard.putNumber("sensorposition", sensorPosition);
  SmartDashboard.putNumber("speed", outputSpeed);
  }

  @Override
  public void disabledInit() {}

  @Override
  public void disabledPeriodic() {}

  @Override
  public void testInit() {}

  @Override
  public void testPeriodic() {}
}
