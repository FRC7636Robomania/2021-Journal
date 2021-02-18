package frc.robot;

import edu.wpi.first.wpilibj.TimedRobot;

import javax.lang.model.type.ErrorType;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.Joystick;

public class Robot extends TimedRobot {
  private TalonSRX flyWheel =new TalonSRX(25);
  private Joystick joy= new  Joystick(0);
  private float error;
  private float errorSum;
  public float errorRate;
  @Override
  public void robotInit() {
    flyWheel.configFactoryDefault();
    flyWheel.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Relative,0,10);
  }

  @Override
  public void robotPeriodic() {
  }

  @Override
  public void autonomousInit() {
  }

  @Override
  public void autonomousPeriodic() {
  }

  @Override
  public void teleopInit() {
    flyWheel.setSelectedSensorPosition(0);
    flyWheel.setSensorPhase(true);
  }

  @Override
  public void teleopPeriodic() {
    error=3000-flyWheel.getSelectedSensorVelocity();
    if(error<100){
      errorSum=errorSum++;
    }else{
      errorSum=0;
    }
    if(joy.getRawButton(1)==true){
      flyWheel.set(ControlMode.Velocity,1*error+1*errorSum+1*errorRate);
    }
    /*if(flyWheel.getSelectedSensorPosition()>0){
        SmartDashboard.putBoolean("Positive/Nagative", true);
      }else{
        SmartDashboard.putBoolean("Positive/Nagative", false);
      }*/
    SmartDashboard.putNumber("error", error);
    SmartDashboard.putNumber("speed", flyWheel.getSelectedSensorVelocity(0));
    SmartDashboard.putNumber("position", flyWheel.getSelectedSensorPosition(0));
  }

  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
  }

  @Override
  public void testInit() {
  }

  @Override
  public void testPeriodic() {
  }

}
