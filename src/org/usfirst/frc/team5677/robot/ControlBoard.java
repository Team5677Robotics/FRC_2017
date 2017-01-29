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
}
