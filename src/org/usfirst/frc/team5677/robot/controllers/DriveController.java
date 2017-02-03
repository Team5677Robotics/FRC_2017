package org.usfirst.frc.team5677.robot.controllers;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Notifier;
import org.usfirst.frc.team5677.lib.Segment;
import org.usfirst.frc.team5677.robot.subsystems.Drive;

public class DriveController {

  private Segment[] leftTrajectory, rightTrajectory;
  private Drive d;
  private int i = 0;
  private int r = 0;
  private boolean isLeftDone = false;
  private boolean isRightDone = false;
  public boolean isTrajectoryDone = false;
  private Notifier n;

  class PeriodicRunnable implements java.lang.Runnable {
    public void run() {
      d.processMPBuffer();
    }
  }

  public DriveController(Segment[] t1, Segment[] t2, Drive d) {
    this.leftTrajectory = t1;
    this.rightTrajectory = t2;
    this.d = d;
    this.d.setMotionProfileMode();
    n = new Notifier(new PeriodicRunnable());
    this.d.setMPControlPeriod(5);
    n.startPeriodic(0.005);
  }

  private void setTrajectory() {
    if (!isLeftDone) {
      Segment s1 = leftTrajectory[i];
      CANTalon.TrajectoryPoint leftP = new CANTalon.TrajectoryPoint();
      leftP.isLastPoint = false;
      if ((i + 1) == leftTrajectory.length) {
        leftP.isLastPoint = true;
        isLeftDone = true;
      }
      leftP.zeroPos = false;
      if (i == 0) {
        leftP.zeroPos = true;
      }
      leftP.velocityOnly = false;
      leftP.timeDurMs = 10;
      leftP.position = s1.getPosition();
      leftP.velocity = s1.getVelocity();
      //System.out.println("Segment 1: "+ s1.toString());

      d.setLeftTrajectoryPoint(leftP);
    }

    if (!isRightDone) {
      Segment s2 = leftTrajectory[r];
      CANTalon.TrajectoryPoint rightP = new CANTalon.TrajectoryPoint();
      rightP.isLastPoint = false;
      if ((r + 1) == rightTrajectory.length) {
        rightP.isLastPoint = true;
        isRightDone = true;
      }
      rightP.zeroPos = false;
      if (r == 0) {
        rightP.zeroPos = true;
      }
      rightP.velocityOnly = false;
      rightP.timeDurMs = 10;
      rightP.position = s2.getPosition();
      rightP.velocity = s2.getVelocity();

      //if(leftP.isLastPoint && rightP.isLastPoint){
      //  isTrajectoryDone = true;
      //}

      //System.out.println("Segment 2: "+ s2.toString());

      d.setRightTrajectoryPoint(rightP);
    }
  }

  public void control() {
    if (isLeftDone && isRightDone) {
      isTrajectoryDone = true;
      CANTalon.SetValueMotionProfile s = CANTalon.SetValueMotionProfile.Disable;
      d.setMPOutput(s);
    } else {

      setTrajectory();
      i++;
      r++;

      CANTalon.SetValueMotionProfile s = CANTalon.SetValueMotionProfile.Enable;
      System.out.println("value=" + s.value);
      d.setMPOutput(s);
    }
  }
}
