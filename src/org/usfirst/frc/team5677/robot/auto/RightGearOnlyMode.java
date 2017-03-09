package org.usfirst.frc.team5677.robot.auto;

import org.usfirst.frc.team5677.robot.controllers.DriveController;
import org.usfirst.frc.team5677.robot.loops.DriveLoop;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryGenerator;
import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.lib.logging.Logger;
import org.usfirst.frc.team5677.robot.loops.MultiLooper;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

public class RightGearOnlyMode implements java.lang.Runnable{

    private TrajectoryGenerator smartGenerator;
    private Drive drive;
    private Segment[] trajectory1, trajectory2, trajectory3;
    private DriveController driveStraight, driveBackwards, turnLeft;
    private DriveLoop driveStraightLoop, driveBackwardsLoop, turnLeftLoop;
    private Timer t = new Timer();
    private Notifier n1, n2, n3;
    private Logger l;
    private double prevTime = -1.0;
    
    public RightGearOnlyMode(Drive drive, TrajectoryGenerator smartGenerator, Logger l){
	this.drive = drive;
	this.l = l;
	//8 ft for going forward auton mode
	this.smartGenerator = smartGenerator;
	trajectory1 = smartGenerator.calcTrajectory(0.0,0.0,6.0);
	//	l.logTrajectory(trajectory1, "DriveForward");
	trajectory2 = smartGenerator.calcTrajectory(0.0,0.0,5.0);
	trajectory3 = smartGenerator.calcTrajectory(0.0,0.0,drive.angleToDistance(90.0));
	//l.logTrajectory(trajectory3, "TurnLeft");
	driveStraight = new DriveController(trajectory1, drive, false, false, false);
	driveBackwards = new DriveController(trajectory2, drive, false, false, false);
	turnLeft = new DriveController(trajectory3, drive, false, false, true);
	driveStraightLoop = new DriveLoop(driveStraight);
	driveBackwardsLoop = new DriveLoop(driveBackwards);
	turnLeftLoop = new DriveLoop(turnLeft);
    }

    public void run(){
	//drive.shiftLowGear(false);
	double velocity = (drive.getRightSpeed()*3.25*Math.PI)/12.0/60.0;
	double position = (drive.getRightEncoder()*3.25*Math.PI)/12.0;
	l.liveLogDriving(velocity, position);
	double dt;
	
	if(prevTime == -1.0){
	    prevTime = t.getFPGATimestamp();
	    dt = 0.0;
	}else{
	    double currTime = t.getFPGATimestamp();
	    dt = currTime-prevTime;
	    prevTime = currTime;
				     
	}
	//System.out.println("dt=" + dt);
	if(!driveStraight.isDone() && !turnLeft.isDone() && !driveBackwards.isDone()){
	    driveStraight.control(dt);
	    //t.delay(0.75);
	}else if(driveStraight.isDone() && !turnLeft.isDone() && !driveBackwards.isDone()){ 
	    if(turnLeft.i == 0){
		driveStraight.stop();
		drive.resetEncoders();
		t.delay(0.75);
	    }
	    turnLeft.control(dt);
	}
	

    }
}
