package org.usfirst.frc.team5677.robot.subsystems;

import com.ctre.CANTalon;
import org.usfirst.frc.team5677.robot.Constants;
import org.usfirst.frc.team5677.robot.states.IntakeState;

public class Intake {

    private static Intake _instance = new Intake();

    public static Intake getInstance(){
	return _instance;
    }

    private final CANTalon intakeRoller;

    private IntakeState state = IntakeState.OFF; 
    private Intake() {
	intakeRoller = new CANTalon(Constants.kIntakeRoller);
	intakeRoller.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	intakeRoller.set(0.0);
    }

    public void toggleIntake(IntakeState intakeState){

	switch(intakeState){

	case OFF:
	    intakeRoller.set(0.0);
	    state = IntakeState.OFF;
	    break;
	case IN:
	    intakeRoller.set(1.0);
	    state = IntakeState.IN;
	    break;
	case OUT:
	    intakeRoller.set(-1.0);
	    state = IntakeState.OUT;
	    break;
	default:
	    intakeRoller.set(0.0);
	    state = IntakeState.OFF;
	    break;
	}
    }
    public IntakeState getIntakeState(){
	return state;
    }
}
