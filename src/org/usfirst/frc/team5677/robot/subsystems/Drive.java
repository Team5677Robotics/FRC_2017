package org.usfirst.frc.team5677.robot.subsystems;

import com.ctre.CANTalon;
import org.usfirst.frc.team5677.robot.Constants;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.ADXRS450_Gyro;

/** */
public class Drive {
    // Put methods for controlling this subsystems
    // here. Call these from Commands.

    private static Drive _instance = new Drive();

    public static Drive getInstance() {
	return _instance;
    }

    private CANTalon rightMaster;
    private CANTalon rightSlave1;
    private CANTalon rightSlave2;
    private CANTalon leftMaster;
    private CANTalon leftSlave1;
    private CANTalon leftSlave2;
    private Solenoid shifter;
    private ADXRS450_Gyro gyro;
    
    //private final Solenoid shifter;

    private enum ShifterState{
	HIGH, LOW;
    }

    private ShifterState shifterState = ShifterState.HIGH;
    
    private Drive() {
	rightMaster = new CANTalon(Constants.kRightDriveMaster);
	rightSlave1 = new CANTalon(Constants.kRightDriveSlave1);
	rightSlave2 = new CANTalon(Constants.kRightDriveSlave2);
	leftMaster = new CANTalon(Constants.kLeftDriveMaster);
	leftSlave1 = new CANTalon(Constants.kLeftDriveSlave1);
	leftSlave2 = new CANTalon(Constants.kLeftDriveSlave2);
	shifter = new Solenoid(Constants.kShifter);
    
	rightMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	rightSlave1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	rightSlave2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	leftMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	leftSlave1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	leftSlave2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

	leftSlave2.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative); 
	rightMaster.setFeedbackDevice(CANTalon.FeedbackDevice.CtreMagEncoder_Relative);
	leftSlave2.configEncoderCodesPerRev(1024);
	rightMaster.configEncoderCodesPerRev(1024);
	rightMaster.reverseSensor(true);

	gyro = new ADXRS450_Gyro();
	
	rightMaster.set(0.0);
	rightSlave1.set(0.0);
	rightSlave2.set(0.0);
	leftMaster.set(0.0);
	leftSlave1.set(0.0);
	leftSlave2.set(0.0);
	//shifter = new Solenoid()
    }

    public void setToSpeedMode(){
	rightMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
	rightSlave1.changeControlMode(CANTalon.TalonControlMode.Speed);
	rightSlave2.changeControlMode(CANTalon.TalonControlMode.Speed);
	leftMaster.changeControlMode(CANTalon.TalonControlMode.Speed);
	leftSlave1.changeControlMode(CANTalon.TalonControlMode.Speed);
	leftSlave2.changeControlMode(CANTalon.TalonControlMode.Speed);
    }

    public void setToPercentVBusMode(){	
	rightMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	rightSlave1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	rightSlave2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	leftMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	leftSlave1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	leftSlave2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }
    public void setRightSpeed(double speed) {
	//speed = -speed;
	//speed = 0.0;
	rightMaster.set(speed);
	rightSlave1.set(speed);
	rightSlave2.set(speed);
    
    }

    public void shiftLowGear(boolean state){
	if(state){
	    shifter.set(true);
	}else{
	    shifter.set(false);
	}
    }

    public void setLeftSpeed(double speed) {
	//speed = -speed;
	//speed=0.0;
	leftMaster.set(speed);
	leftSlave1.set(speed);
	leftSlave2.set(speed);
    }

    public double angleToDistance(double angle){
	double circumOfRobot = Constants.kWheelBase*Math.PI;
	System.out.println(circumOfRobot);
	double percentCircum = angle/360.0;
	System.out.println(percentCircum);
	double distanceOfTurn = percentCircum*circumOfRobot;
	return distanceOfTurn/12.0;
    }

    public void isEncodersPresent(){
	System.out.println(leftSlave2.isSensorPresent(CANTalon.FeedbackDevice.CtreMagEncoder_Relative));
    }
    public double getRightEncoder(){
	return rightMaster.getPosition();
    }

    public double getLeftEncoder(){
	//leftSlave2.reverseSensor(true);
	return leftSlave2.getPosition();
    }

    public void resetEncoders(){
	leftSlave2.setPosition(0.0);
        rightMaster.setPosition(0.0);

        leftSlave2.setEncPosition(0);
        rightMaster.setEncPosition(0);
    }

    public double getLeftSpeed(){
	return leftSlave2.getSpeed();
    }

    public double getRightSpeed(){
	return rightMaster.getSpeed();
    }

    public void calibrateGyro(){
	gyro.calibrate();
    }

    public void resetGyro(){
	gyro.reset();
    }

    public double getGyroAngle(){
	return gyro.getAngle();
    }
    
    public CANTalon getRightTalon(){
	return rightMaster;
    }
    
}
