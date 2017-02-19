package org.usfirst.frc.team5677.robot.auto;

import org.usfirst.frc.team5677.robot.controllers.DriveController;
import org.usfirst.frc.team5677.robot.loops.DriveLoop;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryGenerator;
import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.robot.loops.MultiLooper;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

public class RightGearOnlyMode implements java.lang.Runnable{

    private TrajectoryGenerator smartGenerator;
    private Drive drive;
    private Segment[] trajectory1, trajectory2, trajectory3;
    private DriveController driveStraight, driveBackwards, turnRight;
    private DriveLoop driveStraightLoop, driveBackwardsLoop, turnRightLoop;
    private Timer t = new Timer();
    private Notifier n1, n2, n3;
    
    public RightGearOnlyMode(Drive drive, TrajectoryGenerator smartGenerator){
	this.drive = drive;
	this.smartGenerator = smartGenerator;
	trajectory1 = smartGenerator.calcTrajectory(0.0,0.0,60.00/12.0);	
	trajectory2 = smartGenerator.calcTrajectory(0.0,0.0,5.0);
	trajectory3 = smartGenerator.calcTrajectory(0.0,0.0,drive.angleToDistance(45.0));
	driveStraight = new DriveController(trajectory1, drive, false, false, false);
	driveBackwards = new DriveController(trajectory2, drive, false, false, false);
	turnRight = new DriveController(trajectory3, drive, false, true, false);
	driveStraightLoop = new DriveLoop(driveStraight);
	driveBackwardsLoop = new DriveLoop(driveBackwards);
	turnRightLoop = new DriveLoop(turnRight);
	n1 = new Notifier(driveStraightLoop);
	n2 = new Notifier(driveBackwardsLoop);
	n3 = new Notifier(turnRightLoop);
	n1.startPeriodic(0.01);
    }

    public void run(){
	if(driveStraightLoop.isDone() && !turnRightLoop.isStarted()){
	    n1.stop();
	    t.delay(0.75);
	    n3.startPeriodic(0.01);
	}/*else if(turnRightLoop.isStarted() && !driveBackwardsLoop.isStarted()){
	    n3.stop();
	    t.delay(0.75);
	    n2.startPeriodic(0.01);
	    }*/
    }
}
