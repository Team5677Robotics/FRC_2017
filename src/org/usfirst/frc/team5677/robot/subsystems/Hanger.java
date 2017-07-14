package org.usfirst.frc.team5677.robot.subsystems;

import com.ctre.CANTalon;
import org.usfirst.frc.team5677.robot.Constants;

public class Hanger {

  private static Hanger _instance = new Hanger();

  public static Hanger getInstance() {
    return _instance;
  }

  private CANTalon hangerMaster;
  private CANTalon hangerSlave1;
  private CANTalon hangerSlave2;
    
  private Hanger() {
    hangerMaster = new CANTalon(Constants.kHangerMaster);
    hangerSlave1= new CANTalon(Constants.kHangerSlave1);
    hangerSlave2 = new CANTalon(Constants.kHangerSlave2);
    
    hangerMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    hangerSlave1.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    hangerSlave2.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    
    hangerMaster.set(0.0);
    hangerSlave1.set(0.0);
    hangerSlave2.set(0.0);
  }

  public void runHanger() {
    hangerMaster.set(-1.0);
    hangerSlave1.set(-1.0);
    hangerSlave2.set(-1.0);
  }

    public void grabRope(){
	hangerMaster.set(-0.5);
	hangerSlave1.set(-0.5);
	hangerSlave2.set(-0.5);
    }
    
  public void stopHanger() {
    hangerMaster.set(0.0);
    hangerSlave1.set(0.0);
    hangerSlave2.set(0.0);
  }
}
