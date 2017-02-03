package org.usfirst.frc.team5677.robot;

import edu.wpi.first.wpilibj.Joystick;

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

  public boolean getIntakeInButton() {
    return joystick2.getRawButton(2);
  }

  public boolean getIntakeOutButton() {
    return joystick2.getRawButton(3);
  }

  public boolean getConveyorUpButton() {
    return joystick2.getRawButton(1);
  }

  public boolean getConveyorDownButton() {
    return joystick2.getRawButton(4);
  }

  public boolean getIntakeBallButton() {
    return joystick2.getRawButton(2);
  }

  public boolean getScoreLowGoalButton() {
    return joystick2.getRawButton(4);
  }
}
