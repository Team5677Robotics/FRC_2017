package org.usfirst.frc.team5677.robot.loops;

import org.usfirst.frc.team5677.robot.controllers.DriveController;

public class DriveLoop extends Loop{

    private DriveController d;
    public DriveLoop(DriveController d){
	this.d = d;
    }
    
    @Override
    public void run(){
	//System.out.println("Hello");
	d.control();
    }
}
