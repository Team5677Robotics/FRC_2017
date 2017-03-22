package org.usfirst.frc.team5677.robot.controllers;

import org.usfirst.frc.team5677.robot.subsystems.Drive;

public class ArcadeDrive {

  private double gain = 1.0;
  private Drive d;

  public ArcadeDrive(double gain, Drive d) {
    this.gain = gain;
    this.d = d;
  }

  public void setSpeed(double throttle, double turn) {
    double leftSpeed, rightSpeed, skimmedLeftSpeed, skimmedRightSpeed;
    throttle = -throttle;

    throttle = Math.signum(throttle) * throttle * throttle;
    turn = Math.signum(turn) * turn * turn;

    if (turn < 0.1 && turn > -0.1) {
      turn = 0.0;
    }
    if (throttle < 0.1 && throttle > -0.1) {
      throttle = 0.0;
    }

    leftSpeed = throttle + turn;
    rightSpeed = throttle - turn;

    skimmedLeftSpeed = leftSpeed + skim(rightSpeed);
    skimmedRightSpeed = rightSpeed + skim(leftSpeed);

    d.setLeftSpeed(skimmedLeftSpeed);
    d.setRightSpeed(skimmedRightSpeed);
  }
  /**
   * Does some skimming. Takes off excess speed and dishes it off to the other side.
   *
   * @param speed
   */
  private double skim(double speed) {
    if (speed > 1.0) {
      return -((speed - 1.0) * gain);
    } else if (speed < -1.0) {
      return -((speed + 1.0) * gain);
    } else {
      return 0.0;
    }
  }
}
