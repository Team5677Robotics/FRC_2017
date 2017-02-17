package org.usfirst.frc.team5677.lib.trajectory;

import org.usfirst.frc.team5677.lib.trajectory.Segment;

public class TrajectoryGenerator {

  double maxV;
  double maxA;
  double controlLoop;

  public TrajectoryGenerator(double maxV, double maxA, double controlLoop) {
    this.maxV = maxV;
    this.maxA = maxA;
    this.controlLoop = controlLoop;
  }

  /**
   *
   * Using the third kinematic equation we calculate the max velocity the robt can travel based
   * off of the desired distance we want to travel. Then we compare this calculated velocity
   * to the max velocity that the robot can actually do and returns the minimum value.
   *
   * @param   initialV    initial velocity of the robot
   * @param   totalDistance desired distance of travel for the robot
   * @return      the minimum between adjustedMaxV and maxV
   *
   */
  private double calcAdjustedMaxV(double initialV, double endingV, double totalDistance) {
      //double startDistanceOffset = (0.5 * initialV * initialV) / this.maxA;
      //double endDistanceOffset = (0.5 * endingV * endingV) / this.maxA;
      double adjustedMaxV =
        Math.sqrt(this.maxA * totalDistance);

    return Math.min(adjustedMaxV, this.maxV);
  }

  public Segment[] calcTrajectory(double initialV, double endingV, double totalDistance) {
    double adjustedMaxV = calcAdjustedMaxV(initialV, endingV, totalDistance);
    double rampUpTime = (adjustedMaxV - initialV) / this.maxA;
    double rampUpDistance = 0.5*rampUpTime*adjustedMaxV;
    double rampDownTime = (adjustedMaxV - endingV) / this.maxA;
    double rampDownDistance = rampUpDistance; 
    double coastDistance = totalDistance - (rampUpDistance + rampUpDistance);
    double coastTime = coastDistance / adjustedMaxV;
    double totalTime = rampUpTime + coastTime + rampDownTime;
    int totalIterations = (int) (totalTime / (1 / this.controlLoop));
    //double[] trajectoryInfo = {adjustedMaxV, rampUpTime, rampUpDistance, rampDownTime, rampDownDistance, coastDistance, coastTime, totalTime};
    System.out.println(adjustedMaxV);
    System.out.println(rampUpDistance);
    System.out.println(coastDistance + rampUpDistance);
    Segment[] trajectory = new Segment[totalIterations];
    double time = 0.0;
    for (int i = 0; i < totalIterations; i++) {
      double velocity, acceleration, position;
      if (time <= rampUpTime) {
        velocity = initialV + (this.maxA * time);
        acceleration = this.maxA;
        position = velocity * 0.5 * time;
      } else if (time > rampUpTime && time <= (coastTime + rampUpTime)) {
        velocity = adjustedMaxV;
        acceleration = 0;
        position = velocity * time;
      } else {
        double adjustedTime = (totalTime - time);
        velocity = (this.maxA * (adjustedTime));
        //System.out.println(this.maxA*time);
        acceleration = -this.maxA;
        position =
            (totalDistance) - (0.5 * velocity * adjustedTime);
      }
      time += (1 / controlLoop);
      Segment s = new Segment(velocity, acceleration, position);
      trajectory[i] = s;
    }
    return trajectory;
  }
}
