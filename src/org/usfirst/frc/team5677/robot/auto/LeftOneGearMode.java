package org.usfirst.frc.team5677.robot.auto;

import edu.wpi.first.wpilibj.Timer;
import org.usfirst.frc.team5677.lib.logging.Logger;
import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryGenerator;
import org.usfirst.frc.team5677.robot.controllers.DriveController;
import org.usfirst.frc.team5677.robot.states.GearState;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.robot.subsystems.Gear;
import org.usfirst.frc.team5677.robot.subsystems.GearPuncher;
import org.usfirst.frc.team5677.robot.states.RobotState;


public class LeftOneGearMode implements java.lang.Runnable {
  private TrajectoryGenerator smartGenerator;
  private Drive drive;
  private Gear gear;
  private GearPuncher gearPuncher;
  private Segment[] trajectory1, trajectory2, trajectory3, trajectory4;
  private DriveController driveStraight1, turnRight1, driveStraight2, driveStraight3;
  private Timer t = new Timer();
  private Logger l;
  private double prevTime = -1.0;
  private int punchCount = 0;
  private boolean punchIsDone = false;
  private boolean isStopped = false;

  public LeftOneGearMode(
      Drive drive,
      TrajectoryGenerator smartGenerator,
      Logger l,
      Gear gear,
      GearPuncher gearPuncher) {
    this.drive = drive;
    this.gear = gear;
    this.gearPuncher = gearPuncher;

    this.l = l;

    this.smartGenerator = smartGenerator;
    trajectory1 = smartGenerator.calcTrajectory(0.0, 0.0, 100.0 / 12.0);
    trajectory2 = smartGenerator.calcTrajectory(0.0, 0.0, drive.angleToDistance(76.0));
    trajectory3 = smartGenerator.calcTrajectory(0.0, 0.0, 2.5);
    trajectory4 = smartGenerator.calcTrajectory(0.0, 0.0, 2.0);
    //l.logTrajectory(trajectory3, "TurnLeft");
    driveStraight1 = new DriveController(trajectory1, drive, false, false, false);
    turnRight1 = new DriveController(trajectory2, drive, false, true, false);
    driveStraight2 = new DriveController(trajectory3, drive, false, false, false);
    driveStraight3 = new DriveController(trajectory4, drive, true, false, false);
  }

  public void run() {
    //drive.shiftLowGear(false);
      if((RobotState.isDisabled || RobotState.isTeleop) && !RobotState.isAuto){
	  return;
      }
      
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
    //System.out.println("dt=" + dt);
    //System.out.println(driveStraight1.isDone());
    if (!driveStraight1.isDone()
        && !punchIsDone
        && !turnRight1.isDone()
        && !driveStraight2.isDone()
        && !driveStraight3.isDone()) {
      driveStraight1.control(dt);
      //turnRight1.control(dt);
      //t.delay(0.75);
      //System.out.println("Drive");
    } else if (driveStraight1.isDone()
        && !turnRight1.isDone()
        && !punchIsDone
        && !driveStraight2.isDone()
        && !driveStraight3.isDone()) {
      if (turnRight1.i == 0) {
        driveStraight1.stop();
        drive.resetEncoders();
        drive.resetGyro();
        t.delay(0.75);
      }
      turnRight1.control(dt);
    } else if (driveStraight1.isDone()
        && !punchIsDone
        && turnRight1.isDone()
        && !driveStraight2.isDone()
        && !driveStraight3.isDone()) {
      if (driveStraight2.i == 0) {
        turnRight1.stop();
        drive.resetEncoders();
        drive.resetGyro();
        t.delay(0.75);
      }
      driveStraight2.control(dt);
    } else if (driveStraight1.isDone()
        && !punchIsDone
        && turnRight1.isDone()
        && driveStraight2.isDone()
        && !driveStraight3.isDone()) {
      if (punchCount == 0) {
        driveStraight2.stop();
        drive.resetEncoders();
        drive.resetGyro();
        punchCount++;
        t.delay(0.75);
      }
      gear.toggleGear(GearState.SHOOT);
      t.delay(0.15);
      gearPuncher.toggleGearPuncher(GearState.SHOOT);
      //System.out.println("Do Punch");
      punchIsDone = true;
      t.delay(0.75);
      drive.resetEncoders();
    } else if (driveStraight1.isDone()
        && punchIsDone
        && turnRight1.isDone()
        && driveStraight2.isDone()
        && !driveStraight3.isDone()) {
      driveStraight3.control(dt);
    } else {
      //driveStraight3.stop();
      //gear.toggleGear(GearState.LOAD);
      //gearPuncher.toggleGearPuncher(GearState.LOAD);
      if (!isStopped) {
        drive.setLeftSpeed(0.0);
        drive.setRightSpeed(0.0);
        isStopped = true;
      }

      //System.out.println("Done");
    }
    //System.out.println("Test");
  }

  public void resetDone() {
    gear.toggleGear(GearState.LOAD);
    gearPuncher.toggleGearPuncher(GearState.LOAD);
    isStopped = false;
    driveStraight1.resetDone();
    turnRight1.resetDone();
    driveStraight2.resetDone();
    driveStraight3.resetDone();
    punchIsDone = false;
  }

  public boolean isPunchDone() {
    return punchIsDone;
  }
}
