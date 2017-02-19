package org.usfirst.frc.team5677.robot.controllers;


import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryFollower;
import org.usfirst.frc.team5677.robot.subsystems.Drive;

public class DriveController{

    private Segment[] traj;
    private int i;
    private boolean isDone = false;
    private TrajectoryFollower follow = new TrajectoryFollower();
    private Drive d;
    private boolean isBackward = false;
    private boolean isRight = false;
    private boolean isLeft = false;
    
    public DriveController(Segment[] traj, Drive d, boolean isBackward, boolean isRight, boolean isLeft){
	this.traj = traj;
	this.d = d;
	i=0;
	this.isBackward = isBackward;
	this.isRight = isRight;
	this.isLeft = isLeft;
	double kV = 1.0/7.0;
	double kA = 1.0/85.0;
	//double kA = 0.0;
	follow.setConstants(kV,kA,0.0,0.0,0.0);
    }

    public void control(double dt){
	if(i==-1){
	    System.out.println("No Trajectory");
	}else if(i>=traj.length){
	    System.out.println("Trajectory Completed");
	    isDone = true;
	    double motorOutput = 0.0;
	    d.setRightSpeed(motorOutput);
	    d.setLeftSpeed(-motorOutput);
	}else{
	    System.out.println(traj[i].toString()+", "+dt);
	    double motorOutput = follow.calcMotorOutput(0.0, traj[i], dt);
	    System.out.println(motorOutput+ " ----   " + i);
	    if(isBackward){
		System.out.println("Backwards");
		d.setRightSpeed(motorOutput);
		d.setLeftSpeed(-motorOutput);
	    }else if(isRight){
		System.out.println("Right Turn");
		d.setRightSpeed(-motorOutput);
		d.setLeftSpeed(-motorOutput);
	    }else if(isLeft){	
		System.out.println("Left Turn");
		d.setRightSpeed(motorOutput);
		d.setLeftSpeed(motorOutput);
	    }else{
		System.out.println("Left V: "+d.getLeftEncoder()+"  -----  Right V:" + d.getRightEncoder());
		d.setRightSpeed(-motorOutput);
		d.setLeftSpeed(motorOutput);
	    }
	    i++;
	}
    }

    public boolean isDone(){
	return isDone;
    }

}
