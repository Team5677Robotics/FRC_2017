package org.usfirst.frc.team5677.lib.logging;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team5677.lib.trajectory.Segment;

public class Logger{

    SmartDashboard sd;
   
    public Logger(){
	sd = new SmartDashboard();
    }

    public static void logTrajectory(Segment trajectory, String trajName){
	double velocity,position;	    
	velocity = trajectory.getVelocity();
	position = trajectory.getPosition();

	SmartDashboard.putNumber(trajName+"Vel", velocity);
	SmartDashboard.putNumber(trajName+"Pos", position);
    }

    public static void setDriveConstants(){
	SmartDashboard.getNumber("kV", 0.0);	
	SmartDashboard.getNumber("kA", 0.0);
    }
    
    public static double[] getDriveConstants(){
	double[] constants = new double[2];
	double kVD = SmartDashboard.getNumber("kVD", 0.0);	
	double kAD = SmartDashboard.getNumber("kAD", 0.0);
	if(kVD == 0.0){
	    constants[0] = 0.0;
	}else{
	    constants[0] = 1.0/kVD;
	}

	if(kAD == 0.0){
	    constants[1] = 0.0;
	}else{
	    constants[1] = 1.0/kAD;
	}
	
	
	return constants;
    }
    
    public void liveLogDriving(double velocity, double position){
	sd.putNumber("CurrentDriveVel",velocity);
	sd.putNumber("CurrentDrivePos",position);
    }

    
}

