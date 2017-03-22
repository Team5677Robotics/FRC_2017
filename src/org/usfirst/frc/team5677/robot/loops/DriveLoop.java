package org.usfirst.frc.team5677.robot.loops;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5677.robot.controllers.DriveController;

public class DriveLoop extends Loop {

  private DriveController d;
  private Timer t;
  private double prevTime = -1.0;

  public DriveLoop(DriveController d) {
    super();
    this.d = d;
    t = new Timer();
  }

  @Override
  public void run() {
    super.setStart();
    double dt;
    if (prevTime == -1.0) {
      prevTime = t.getFPGATimestamp();
      dt = 0.0;
    } else {
      double currTime = t.getFPGATimestamp();
      dt = currTime - prevTime;
      prevTime = currTime;
    }

    if (d.isDone() == false) {
      d.control(dt);
    } else {
      super.setDone();
    }
  }
}
