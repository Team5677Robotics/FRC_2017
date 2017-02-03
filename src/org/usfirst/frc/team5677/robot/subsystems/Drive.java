package org.usfirst.frc.team5677.robot.subsystems;

import com.ctre.CANTalon;
import org.usfirst.frc.team5677.robot.Constants;

/** */
public class Drive {
  // Put methods for controlling this subsystems
  // here. Call these from Commands.

  private static Drive _instance = new Drive();

  public static Drive getInstance() {
    return _instance;
  }

  private final CANTalon rightMaster, rightSlave1, rightSlave2, leftMaster, leftSlave1, leftSlave2;
  //private final Solenoid shifter;

  private Drive() {
    rightMaster = new CANTalon(Constants.kRightDriveMaster);
    rightSlave1 = new CANTalon(Constants.kRightDriveSlave1);
    rightSlave2 = new CANTalon(Constants.kRightDriveSlave2);
    leftMaster = new CANTalon(Constants.kLeftDriveMaster);
    leftSlave1 = new CANTalon(Constants.kLeftDriveSlave1);
    leftSlave2 = new CANTalon(Constants.kLeftDriveSlave2);

    rightMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    rightSlave1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    rightSlave2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    leftMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    leftSlave1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    leftSlave2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

    rightMaster.set(0.0);
    rightSlave1.set(0.0);
    rightSlave2.set(0.0);
    leftMaster.set(0.0);
    leftSlave1.set(0.0);
    leftSlave2.set(0.0);
    //shifter = new Solenoid()
  }

  public void setMotionProfileMode() {
    rightMaster.changeControlMode(CANTalon.TalonControlMode.MotionProfile);
    rightSlave1.changeControlMode(CANTalon.TalonControlMode.MotionProfile);
    rightSlave2.changeControlMode(CANTalon.TalonControlMode.MotionProfile);
    leftMaster.changeControlMode(CANTalon.TalonControlMode.MotionProfile);
    leftSlave1.changeControlMode(CANTalon.TalonControlMode.MotionProfile);
    leftSlave2.changeControlMode(CANTalon.TalonControlMode.MotionProfile);
  }

  public void setRightTrajectoryPoint(CANTalon.TrajectoryPoint rightPoint) {
    rightMaster.pushMotionProfileTrajectory(rightPoint);
    rightSlave1.pushMotionProfileTrajectory(rightPoint);
    rightSlave2.pushMotionProfileTrajectory(rightPoint);
  }

  public void setLeftTrajectoryPoint(CANTalon.TrajectoryPoint leftPoint) {
    leftMaster.pushMotionProfileTrajectory(leftPoint);
    leftSlave1.pushMotionProfileTrajectory(leftPoint);
    leftSlave2.pushMotionProfileTrajectory(leftPoint);
  }

  public void processMPBuffer() {

    rightMaster.processMotionProfileBuffer();
    rightSlave1.processMotionProfileBuffer();
    rightSlave2.processMotionProfileBuffer();
    leftMaster.processMotionProfileBuffer();
    leftSlave1.processMotionProfileBuffer();
    leftSlave2.processMotionProfileBuffer();
  }

  public void setMPControlPeriod(int period) {
    rightMaster.changeMotionControlFramePeriod(period);
    rightSlave1.changeMotionControlFramePeriod(period);
    rightSlave2.changeMotionControlFramePeriod(period);
    leftMaster.changeMotionControlFramePeriod(period);
    leftSlave1.changeMotionControlFramePeriod(period);
    leftSlave2.changeMotionControlFramePeriod(period);
  }

  //public CANTalon.MotionProfileStatus getMPStatus(){
  //return rightMaster.getMotionProfileStatus();
  //}

  public void setMPOutput(CANTalon.SetValueMotionProfile out) {
    rightMaster.set(out.value);
    rightSlave1.set(out.value);
    rightSlave2.set(out.value);

    leftMaster.set(out.value);
    leftSlave1.set(out.value);
    leftSlave2.set(out.value);
  }

  public void setRightSpeed(double speed) {
    rightMaster.set(speed);
    rightSlave1.set(speed);
    rightSlave2.set(speed);
  }

  public void setLeftSpeed(double speed) {
    leftMaster.set(speed);
    leftSlave1.set(speed);
    leftSlave2.set(speed);
  }
}
