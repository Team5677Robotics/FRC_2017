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
    double error = setpointPosition - actualPosition;
    this.sumError += error;
    double errorDt = (error - this.prevError) / this.dt;
    this.prevError = error;
    //Integral term may not be necessary
    return (this.kP * error) + (this.kI * this.sumError) + (this.kD * errorDt);
  }

    public double calcMotorOutput(double currPosition, Segment s,double dt) {
	this.dt = dt;
	double velocity = s.getVelocity();
	double acceleration = s.getAcceleration();
	double position = s.getPosition();
	return getFeedForward(velocity, acceleration) + getFeedBack(position, currPosition);
    };
}
