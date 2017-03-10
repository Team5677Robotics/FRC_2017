package org.usfirst.frc.team5677.robot.subsystems;

import com.ctre.CANTalon;
import org.usfirst.frc.team5677.robot.Constants;
import org.usfirst.frc.team5677.robot.states.ConveyorState;

public class Conveyor {

    private static Conveyor _instance = new Conveyor();

    public static Conveyor getInstance() {
	return _instance;
    }

    private final CANTalon conveyorRoller;

    private ConveyorState state = ConveyorState.OFF;

    private Conveyor() {
	conveyorRoller = new CANTalon(Constants.kConveyorRoller);
	conveyorRoller.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	conveyorRoller.set(0.0);
    }

    public void toggleConveyor(ConveyorState intakeState) {
	switch (intakeState) {
	case OFF:
	    conveyorRoller.set(0.0);
	    state = ConveyorState.OFF;
	    break;
	case UP:
	    conveyorRoller.set(1.0);
	    state = ConveyorState.UP;
	    break;
	case DOWN:
	    conveyorRoller.set(-1.0);
	    state = ConveyorState.DOWN;
	    break;
	default:
	    conveyorRoller.set(0.0);
	    state = ConveyorState.OFF;
	    break;
	}
    }
}
