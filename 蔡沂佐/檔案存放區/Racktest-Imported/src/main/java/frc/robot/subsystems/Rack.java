/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.Constants;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.LimitSwitchNormal;


public class Rack extends SubsystemBase {
  public TalonSRX Rack = new TalonSRX(Constants.Rack);
  public static LimitSwitchNormal LS = LimitSwitchNormal.NormallyOpen;
    
  /**
   * Creates a new Rack.
   */
  public Rack() {
    while(LS.value != 1) {
      Rack.set(ControlMode.PercentOutput, 0.2);
    }
    Rack.configFactoryDefault();
    Rack.configPeakOutputForward(0.5);
    Rack.configPeakOutputReverse(-0.5);
    Rack.configNeutralDeadband(0.01);
    Rack.setSelectedSensorPosition(0);
    Rack.configSelectedFeedbackSensor(FeedbackDevice.CTRE_MagEncoder_Absolute,0,10);
    Rack.setSensorPhase(false);
  
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void position1() {
    Rack.set(ControlMode.Position, 500);
    // This method will be called once per scheduler run
  }

  public void position2() {
    Rack.set(ControlMode.Position, 1500);
    // This method will be called once per scheduler run
  }
}
