package org.usfirst.frc.team5677.lib.trajectory;;

import org.usfirst.frc.team5677.lib.trajectory.Segment;

public class TrajectoryFollower {

  private double kV, kA, kP, kI, kD, dt;
  private double prevError = 0.0;
  private double sumError = 0.0;

  public TrajectoryFollower() {}


  public void setConstants(double kV, double kA, double kP, double kI, double kD) {
    this.kV = kV;
    this.kA = kA;
    this.kP = kP;
    this.kI = kI;
    this.kD = kD; 
  }

  public double getFeedForward(double velocity, double acceleration) {
    return (kV * velocity) + (kA * acceleration);
  }

  public double getFeedBack(double setpointPosition, double actualPosition) {
      //System.out.println("setpointPosition= "+setpointPosition+" ----------  actualPosition= "+actualPosition);
    double error = setpointPosition - actualPosition;
    System.out.println("Error: "+error);
    this.sumError += error;
    double errorDt = (error - this.prevError) / this.dt;
    this.prevError = error;
    //Integral term may not be necessary
    return (this.kP * error) + (this.kI * this.sumError) + (this.kD * errorDt);
  }

    public double calcMotorOutput(double currPosition, Segment s,double dt, boolean isTurn) {
	this.dt = dt;
	double velocity = s.getVelocity();
	double acceleration = s.getAcceleration();
	double position = 0.0;
	if(isTurn){
	    position = getAngle(s.getPosition());
	}else{ 
	    position = s.getPosition();
	}
	return getFeedForward(velocity, acceleration) + getFeedBack(position, currPosition);
	//return getFeedForward(velocity,acceleration);
    };

    public double getAngle(double feet){
	double circumOfRobot = (27.0*Math.PI)/12.0;
	double percent = feet/circumOfRobot;
	return 360.0*percent;
    }
}
