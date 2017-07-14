package org.usfirst.frc.team5677.robot.subsystems;

import com.ctre.CANTalon;
import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc.team5677.robot.Constants;
import org.usfirst.frc.team5677.robot.states.IntakeState;
import edu.wpi.first.wpilibj.AnalogInput;

public class Intake {

  private static Intake _instance = new Intake();

  public static Intake getInstance() {
    return _instance;
  }

  private final CANTalon intakeRollerMaster, intakeRollerSlave;
  private final Solenoid intakeArm;
  private final AnalogInput haveGearSensor;
    
  private IntakeState state = IntakeState.OFF;
  private boolean intakeArmState = false;
    
  private Intake() {
    intakeRollerMaster = new CANTalon(Constants.kIntakeRollerMaster);
    intakeRollerSlave = new CANTalon(Constants.kIntakeRollerSlave);
    intakeArm = new Solenoid(Constants.kIntakeArm);
    haveGearSensor = new AnalogInput(Constants.kHaveGearSensor);
    
    intakeRollerMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    intakeRollerSlave.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

    intakeRollerMaster.set(0.0);
    intakeRollerSlave.set(0.0);
    intakeArm.set(false);
    
  }

    public boolean hasGear(){
	return (haveGearSensor.getAverageVoltage()>2.0);
    }

    public double getVoltage(){
	return haveGearSensor.getAverageVoltage();
    }

  public void toggleIntake(IntakeState intakeState) {
    switch (intakeState) {
      case OFF:
        intakeRollerMaster.set(0.0);
	intakeRollerSlave.set(0.0);
	intakeArm.set(intakeArmState);
	state = IntakeState.OFF;
        break;
      case IN:
	intakeArm.set(true);
	if(!hasGear()){
	    intakeRollerMaster.set(-1.0);
	    intakeRollerSlave.set(1.0);
	}else{
	    intakeRollerMaster.set(0.0);
	    intakeRollerSlave.set(0.0);
	}
        state = IntakeState.IN;
	intakeArmState = true;
        break;
      case OUT:
        intakeRollerMaster.set(1.0);
	intakeRollerSlave.set(-1.0);
        state = IntakeState.OUT;
        break;
      case UP:
	  intakeArm.set(false);
	  intakeArmState = false;
	  break;
      case GOD:  
	intakeArm.set(true);
	intakeRollerMaster.set(-1.0);
	intakeRollerSlave.set(1.0);
        state = IntakeState.IN;
	intakeArmState = true;
        break;
      default:
        intakeRollerMaster.set(0.0);
	intakeRollerSlave.set(0.0);
	intakeArm.set(intakeArmState);
	state = IntakeState.OFF;
        break;
    }
  }

  public IntakeState getIntakeState() {
    return state;
  }
}
