package org.usfirst.frc.team5677.robot.auto;

import org.usfirst.frc.team5677.robot.DriveController;
import org.usfirst.frc.team5677.robot.DriveLoop;
import org.usfirst.frc.team5677.lib.TrajectoryGenerator;
import org.usfirst.frc.team5677.lib.Segment;
import org.usfirst.frc.team5677.robot.loops.MultiLooper;


public class RightGearOnlyMode{

    private TrajectoryGenerator smartGenerator;
    private Drive drive;
    private Segment[] trajectories = new Segment[4];
    private DriveController driveStraight, driveBackwards;
    private DriveLoop driveStraightLoop, driveBackwardsLoop;
    private Loop[] loops = new Loop[2];
    private MultiLooper ml;

    public RightGearOnlyMode(d, smartGenerator){
	this.drive = drive;
	this.smartGenerator = smartGenerator;
	trajectories[0] = smartGenerator.calcTrajectory(0.0,0.0,7.0);	
	trajectories[1] = smartGenerator.calcTrajectory(0.0,0.0,5.0);
	driveStraight = new DriveController(trajectories[0], drive, false);
	driveBackwards = new DriveController(trajectories[1], drive, true);
	driveStraightLoop = new DriveLoop(driveStraight);
	driveBackwardsLoop = new DriveLoop(driveBackwards);
	loops[0] = driveStraightLoop;
	loops[1] = driveBackwardsLoop;
	ml = new MultiLooper(loops);
    }

    

    
}
