package org.usfirst.frc.team5677.robot;

import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.GenericHID;

public class ControlBoard {
  Joystick joystick1, joystick2;

  public ControlBoard(int joy1, int joy2) {
    joystick1 = new Joystick(joy1);
    joystick2 = new Joystick(joy2);
  }

  public double getDriveThrottle() {
    return joystick1.getRawAxis(4);
  }

  public double getDriveTurn() {
    return joystick1.getRawAxis(1);
  }

  public boolean getIntakeScoreGearButton() {
    return joystick1.getRawButton(5);
  }

  public boolean getHangButton() {
    return joystick1.getRawButton(3);
  }

    public boolean getGrabRopeButton(){
	return joystick1.getRawButton(1);
    }
  public boolean getScoreGearButton() {
    return joystick1.getRawButton(2);
  }

  public boolean getIntakeGearButton() {
    return joystick1.getRawButton(6);
  }

  public boolean getIntakeArmUpButton() {
      return joystick1.getRawAxis(3)>0.1;
  }

    public boolean getIntakeOverrideButton(){
	return joystick1.getRawButton(4);
    }
    
  public boolean getLowGearButton() { 
      return joystick1.getRawAxis(2)>0.1;
  }

    public void rumbleController(double value){
	joystick1.setRumble(GenericHID.RumbleType.kLeftRumble, value);	
	joystick1.setRumble(GenericHID.RumbleType.kRightRumble, value);
    }
}
