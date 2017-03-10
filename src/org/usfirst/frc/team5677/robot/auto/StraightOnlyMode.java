package org.usfirst.frc.team5677.robot.auto;

import org.usfirst.frc.team5677.robot.controllers.DriveController;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.robot.subsystems.Gear;
import org.usfirst.frc.team5677.robot.subsystems.GearPuncher;
import org.usfirst.frc.team5677.robot.states.GearState;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryGenerator;
import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.lib.logging.Logger;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;

public class StraightOnlyMode implements java.lang.Runnable{
    private TrajectoryGenerator smartGenerator;
    private Drive		drive;
    private Gear                gear;
    private GearPuncher         gearPuncher;
    private Segment[]           trajectory1;
    private DriveController	driveStraight1;
    private Timer		t	 = new Timer();
    private Logger		l;
    private double		prevTime = -1.0;
    private int punchCount = 0;

    public StraightOnlyMode(Drive drive, TrajectoryGenerator smartGenerator, Logger l, Gear gear, GearPuncher gearPuncher){
	this.drive = drive;
	this.gear = gear;
	this.gearPuncher = gearPuncher;

	this.l	   = l;
	
	//8 ft for going forward auton mode
	this.smartGenerator = smartGenerator;
	trajectory1	    = smartGenerator.calcTrajectory(0.0,0.0,100.0/12.0);
	//l.logTrajectory(trajectory3, "TurnLeft");
	driveStraight1 = new DriveController(trajectory1, drive, false, false, false);
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
	} else {
	    double	currTime = t.getFPGATimestamp();
	    dt			 = currTime-prevTime;
	    prevTime		 = currTime;
				     
	}
	//System.out.println("dt=" + dt);
	if (!driveStraight1.isDone()) {
	    driveStraight1.control(dt);
	    //t.delay(0.75);
	} else if(driveStraight1.isDone() && !punchIsDone) { 
	    if (punchCount == 0) {
		driveStraight1.stop();
		drive.resetEncoders();
		punchCount++;
		t.delay(0.75);
	    }
	    gear.toggleGear(GearState.SHOOT);
	    t.delay(0.15);
	    gearPuncher.toggleGearPuncher(GearState.SHOOT);
	    punchIsDone=true;
	} else {
	    drive.setLeftSpeed(0.0);
	    drive.setRightSpeed(0.0);
	}
    }
}
