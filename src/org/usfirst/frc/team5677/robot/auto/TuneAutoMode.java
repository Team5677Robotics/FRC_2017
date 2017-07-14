package org.usfirst.frc.team5677.robot.auto;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5677.lib.logging.Logger;
import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryGenerator;
import org.usfirst.frc.team5677.robot.controllers.DriveController;
import org.usfirst.frc.team5677.robot.states.GearState;
import org.usfirst.frc.team5677.robot.states.RobotState;
import org.usfirst.frc.team5677.robot.subsystems.Drive;

public class TuneAutoMode implements java.lang.Runnable {
  private TrajectoryGenerator smartGenerator;
  private Drive drive;
  private Segment[] trajectory1;
  private DriveController driveStraight1;
  private Timer t = new Timer();
  private Logger l;
  private double prevTime = -1.0;
  private boolean isStopped = false;

  public TuneAutoMode(
      Drive drive,
      TrajectoryGenerator smartGenerator,
      Logger l) {
    this.drive = drive;
    this.l = l;
 
    this.smartGenerator = smartGenerator;
    trajectory1 = smartGenerator.calcTrajectory(0.0, 0.0, 6.0);
    //trajectory1 = smartGenerator.calcTrajectory(0.0, 0.0, drive.angleToDistance(90)); 
    driveStraight1 = new DriveController(trajectory1, drive, false, false, false);
    //driveStraight1 = new DriveController(trajectory1, drive, false, true, false); 
    //driveStraight1 = new DriveController(trajectory1, drive, false, false, true);
  }

  public void run() {
    //drive.shiftLowGear(false);
    double velocity = (drive.getRightSpeed() * 3.25 * Math.PI) / 12.0 / 60.0;
    double position = (drive.getRightEncoder() * 3.25 * Math.PI) / 12.0;
    l.liveLogDriving(velocity, position);
    double dt;

    if (prevTime == -1.0) {
      prevTime = t.getFPGATimestamp();
      dt = 0.0;
    } else {
      double currTime = t.getFPGATimestamp();
      dt = currTime - prevTime;
      prevTime = currTime;
    }

    if((RobotState.isDisabled || RobotState.isTeleop) && !RobotState.isAuto){
	System.out.println("TeleopMode");
    } else {
	System.out.println("AutoMode");
	if (!driveStraight1.isDone()) {
	    driveStraight1.control(dt);
	} else {
	    if (!isStopped) {
		drive.setLeftSpeed(0.0);
		drive.setRightSpeed(0.0);
		isStopped = true;
	    }
	}
    }
  }

  public void resetDone() {
    isStopped = false;
    driveStraight1.resetDone();
  }
}
