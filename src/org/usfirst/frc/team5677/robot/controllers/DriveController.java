package org.usfirst.frc.team5677.robot.controllers;

import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryFollower;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.lib.logging.Logger;
public class DriveController {

    private Segment[]           traj;
    public int			i;
    private boolean		isDone	      = false;
    private TrajectoryFollower	rightFollower = new TrajectoryFollower();
    private TrajectoryFollower	leftFollower  = new TrajectoryFollower();
    private Drive		d;
    private boolean		isBackward    = false;
    private boolean		isRight	      = false;
    private boolean		isLeft	      = false;
    
    public DriveController(Segment[] traj, Drive d, boolean isBackward, boolean isRight, boolean isLeft) {
	this.traj = traj;
	this.d	  = d;
	i	  = 0;
	this.isBackward = isBackward;
	this.isRight	= isRight;
	this.isLeft	= isLeft;
	//double kV = 1.0/11.5;
	//double kA = 1.0/96.0;
	double kV = 1.0/15.0;
	double kA = 1.0/80.0;
	double kP = 0.0;
	double kI = 0.0;
	double kD = 0.0;
	if (isBackward) {
	    kP = 0.25;
	} else if (isRight) {
	    kA = 1.0/60.0;
	    kP = 0.05;
	    //kD = 0.001;
	    //kI = 0.08;
	} else if (isLeft) {
	    kA = 1.0/50.0;
	    kP = 0.05;
	    
	} else {
	    //kP = 0.0;
	    kP=0.25;
	    //kI=0.001;
	}
	//double kA = 1.0/515.0;
	double[] constants = Logger.getDriveConstants();
	//double kV = constants[0];
	//double kA = constants[1];
	//System.out.println("KV= "+kV+ " ---------  ----------  kA= "+kA);
	
	rightFollower.setConstants(kV,kA,kP,kI,kD);
	leftFollower.setConstants(kV,kA,kP,kI,kD);
    }

    public void control(double dt){
	if (i == -1) {
	    System.out.println("No Trajectory");
	} else if (i >= traj.length) {
	    //	    System.out.println("Trajectory Completed");
	    isDone		    = true;
	    double	motorOutput = 0.0;
	    d.setRightSpeed(motorOutput);
	    d.setLeftSpeed(-motorOutput);
	}else{
	    //System.out.println(traj[i].toString()+", "+dt);
	    double	rightInches	 = d.getRightEncoder()*3.25*Math.PI;
	    double	rightFeet	 = Math.abs(rightInches/12.0); 
	    double	leftInches	 = d.getLeftEncoder()*3.25*Math.PI;
	    double	leftFeet	 = Math.abs(rightInches/12.0);
	    double rightMotorOutput = 0.0;
	    double leftMotorOutput = 0.0;
	    double turnHeading = Math.abs(d.getGyroAngle());
	    
	    if(isLeft || isRight){	
		rightMotorOutput = rightFollower.calcMotorOutput(turnHeading, traj[i], dt, true);
		leftMotorOutput	 = leftFollower.calcMotorOutput(turnHeading, traj[i], dt, true);
	    }else{
		rightMotorOutput = rightFollower.calcMotorOutput(rightFeet, traj[i], dt, false);
		leftMotorOutput	 = leftFollower.calcMotorOutput(leftFeet, traj[i], dt, false);
	    }
	    Logger.logTrajectory(traj[i], "DriveForward");
	    System.out.println("right_m= "+rightMotorOutput+ "     left_m="+leftMotorOutput+ " ----   " + i);
	    if (isBackward) {	
		//System.out.println("Left D: "+leftFeet+"  -----  Right D:"+rightFeet); 
		//System.out.println("right_m= "+rightMotorOutput+ "     left_m="+leftMotorOutput+ " ----   " + i);
		double heading = d.getGyroAngle();
		double headingKP = 0.01;
		if(heading>0.0){
		    double adjust = headingKP*heading;
		    leftMotorOutput+=adjust;
		}
		if(heading<0.0){
		    double adjust = headingKP*Math.abs(heading);
		    rightMotorOutput+=adjust;
		}
		d.setRightSpeed(rightMotorOutput);
		d.setLeftSpeed(-leftMotorOutput);
	    } else if(isRight) {
		//System.out.println("Right Turn");
		d.setRightSpeed(-rightMotorOutput);
		d.setLeftSpeed(-leftMotorOutput);
	    } else if(isLeft) {	
		//System.out.println("Left Turn");
		d.setRightSpeed(rightMotorOutput);
		d.setLeftSpeed(leftMotorOutput);
	    } else {
		double heading = d.getGyroAngle();
		if(heading>0.0){
		    double headingKP = 0.05;
		    double adjust = headingKP*heading;
		    System.out.println(adjust);
		    leftMotorOutput+=adjust;
		}
		if(heading<0.0){
		    double headingKP = 0.0;
		    double adjust = headingKP*Math.abs(heading);
		    rightMotorOutput+=adjust;
		}
		System.out.println("Left D: "+leftFeet+"  -----  Right D:"+rightFeet); 	
		//ystem.out.println("Left D: "+leftFeet+"  -----  Right D:"+rightFeet); 
		d.setRightSpeed(-rightMotorOutput);
		d.setLeftSpeed(leftMotorOutput);
	    }
	    i++;
	}
    }

    public void stop(){
	d.setRightSpeed(0.0);
	d.setLeftSpeed(0.0);
    }
    
    public boolean isDone(){
	return isDone;
    }

    public void resetDone(){
	isDone = false;
	i=0;
    }
}
