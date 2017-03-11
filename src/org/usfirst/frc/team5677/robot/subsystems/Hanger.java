package org.usfirst.frc.team5677.robot.subsystems;

import com.ctre.CANTalon;
import org.usfirst.frc.team5677.robot.Constants;

public class Hanger{


    private static Hanger _instance = new Hanger();

    public static Hanger getInstance(){
	return _instance;
    }

    private CANTalon hangerMaster;
    private CANTalon hangerSlave;

    private Hanger(){
	hangerMaster = new CANTalon(Constants.kHangerMaster);
	hangerSlave = new CANTalon(Constants.kHangerSlave);

	hangerMaster.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	hangerSlave.changeControlMode(CANTalon.TalonControlMode.PercentVbus);

	hangerMaster.set(0.0);
	hangerSlave.set(0.0);
    }

    public void runHanger(){
	hangerMaster.set(-1.0);
	hangerSlave.set(-1.0);
    }

    public void stopHanger(){
	hangerMaster.set(0.0);
	hangerSlave.set(0.0);
    }
}
