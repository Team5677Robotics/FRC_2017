package org.usfirst.frc.team5677.robot.loops;

public class Loop implements java.lang.Runnable{
    private boolean isStarted, isDone;

    public Loop(){
	isStarted = false;
	isDone = false;
    }

    public void run(){

    }

    public void setStart(){
	this.isStarted = true;
    }

    public void setDone(){
	this.isDone = true;
    }
    
    public boolean isDone(){
	return isDone;
    }

    public boolean isStarted(){
	return isStarted;
    }
} 
