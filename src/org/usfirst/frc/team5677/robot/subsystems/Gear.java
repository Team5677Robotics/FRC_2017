package org.usfirst.frc.team5677.robot.subsystems;

import org.usfirst.frc.team5677.robot.Constants;
import org.usfirst.frc.team5677.robot.states.GearState;
import edu.wpi.first.wpilibj.Solenoid;

public class Gear {

    private static Gear _instance = new Gear();
    
    public static Gear getInstance(){
	return _instance;
    }

    private Solenoid loader, locker;
    private GearState state;
    
    private Gear() {
	loader = new Solenoid(Constants.kGearLoader);
	locker = new Solenoid(Constants.kGearLocker);
	loader.set(false);
	locker.set(false);
    }

    public void toggleGear(GearState gearState){
	switch(gearState){
	case LOAD:
	    loader.set(false);
	    locker.set(false);
	    state = GearState.LOAD;
	    break;
	case SHOOT:
	    loader.set(true);
	    locker.set(true);
	    state = GearState.SHOOT;
	    break;
	default:
	    loader.set(false);
	    locker.set(false);
	    state = GearState.LOAD;
	    break;
	}
    }

    public GearState getGearState(){
	return state;
    }

    public void closeTop() {
	loader.set(false);
    }
}

