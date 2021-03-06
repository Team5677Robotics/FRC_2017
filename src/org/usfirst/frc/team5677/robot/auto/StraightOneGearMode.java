package org.usfirst.frc.team5677.robot.auto;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5677.lib.logging.Logger;
import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryGenerator;
import org.usfirst.frc.team5677.robot.controllers.DriveController;
import org.usfirst.frc.team5677.robot.states.GearState;
import org.usfirst.frc.team5677.robot.states.RobotState;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.robot.subsystems.Gear;
import org.usfirst.frc.team5677.robot.subsystems.GearPuncher;

public class StraightOneGearMode implements java.lang.Runnable {
  private TrajectoryGenerator smartGenerator;
  private Drive drive;
  private Gear gear;
  private GearPuncher gearPuncher;
  private Segment[] trajectory1, trajectory2;
  private DriveController driveStraight1, driveStraight2;
  private Timer t = new Timer();
  private Logger l;
  private double prevTime = -1.0;
  private int punchCount = 0; 
  private boolean punchIsDone = false;
  private boolean isStopped = false;

  public StraightOneGearMode(
      Drive drive,
      TrajectoryGenerator smartGenerator,
      Logger l,
      Gear gear,
      GearPuncher gearPuncher) {
    this.drive = drive;
    this.gear = gear;
    this.gearPuncher = gearPuncher;

    this.l = l;

    //8 ft for going forward auton mode
    this.smartGenerator = smartGenerator;
    trajectory1 = smartGenerator.calcTrajectory(0.0, 0.0, 76.0 / 12.0);
    trajectory2 = smartGenerator.calcTrajectory(0.0, 0.0, 2.0);
    //l.logTrajectory(trajectory3, "TurnLeft");
    driveStraight1 = new DriveController(trajectory1, drive, false, false, false);
    driveStraight2 = new DriveController(trajectory2, drive, true, false, false);
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
    }else{
	System.out.println("AutoMode");
	if (!driveStraight1.isDone() && !punchIsDone && !driveStraight2.isDone()) {
	    driveStraight1.control(dt);
	} else if (driveStraight1.isDone() && !punchIsDone && !driveStraight2.isDone()) {
	    if (punchCount == 0) {
		drive.resetEncoders();
		punchCount++;
		t.delay(0.75);
	    }
	    gear.toggleGear(GearState.SHOOT);
	    t.delay(0.15);
	    gearPuncher.toggleGearPuncher(GearState.SHOOT);
	    punchIsDone = true;
	    t.delay(0.75);
	    drive.resetEncoders();
	} else if (driveStraight1.isDone() && punchIsDone && !driveStraight2.isDone()) {
	    driveStraight2.control(dt); 
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
    gear.toggleGear(GearState.LOAD);
    gearPuncher.toggleGearPuncher(GearState.LOAD);
    isStopped = false;
    driveStraight1.resetDone();
    driveStraight2.resetDone();
    punchIsDone = false;
  }

  public boolean isPunchDone() {
    return punchIsDone;
  }
}
