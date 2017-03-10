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
	double kA = 1.0/150.0;
	double kP = 0.0;
	double kI = 0.0;
	double kD = 0.0;
	if (isBackward) {
	    kP = 0.275;
	} else if (isRight) {
	    kP = 0.30;
	} else if (isLeft) {
	    kP = 0.9;
	    kI = 0.1055;
	} else {
	    kP=0.275;
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
	    System.out.println("Trajectory Completed");
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
	    double	rightMotorOutput = rightFollower.calcMotorOutput(rightFeet, traj[i], dt);
	    double	leftMotorOutput	 = leftFollower.calcMotorOutput(leftFeet, traj[i], dt);
	    Logger.logTrajectory(traj[i], "DriveForward");
	    //System.out.println("right_m= "+rightMotorOutput+ "     left_m="+leftMotorOutput+ " ----   " + i);
	    if (isBackward) {	
		System.out.println("Left D: "+leftFeet+"  -----  Right D:"+rightFeet); 
		d.setRightSpeed(rightMotorOutput);
		d.setLeftSpeed(-leftMotorOutput);
	    } else if(isRight) {
		System.out.println("Right Turn");
		d.setRightSpeed(-rightMotorOutput);
		d.setLeftSpeed(-leftMotorOutput);
	    } else if(isLeft) {	
		System.out.println("Left Turn");
		d.setRightSpeed(rightMotorOutput);
		d.setLeftSpeed(leftMotorOutput);
	    } else {
		System.out.println("Left D: "+leftFeet+"  -----  Right D:"+rightFeet); 
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
}
