package org.usfirst.frc.team5677.robot.subsystems;

import edu.wpi.first.wpilibj.Solenoid;
import org.usfirst.frc.team5677.robot.Constants;
import org.usfirst.frc.team5677.robot.states.GearState;

public class GearPuncher {

  private static GearPuncher _instance = new GearPuncher();

  public static GearPuncher getInstance() {
    return _instance;
  }

  private Solenoid puncher;
  private GearState state;

  private GearPuncher() {
    puncher = new Solenoid(Constants.kGearPuncher);
    puncher.set(false);
  }

  public void toggleGearPuncher(GearState gearState) {
    switch (gearState) {
      case LOAD:
        puncher.set(false);
        state = GearState.LOAD;
        break;
      case SHOOT:
        puncher.set(true);
        state = GearState.SHOOT;
        break;
      default:
        puncher.set(false);
        state = GearState.LOAD;
        break;
    }
  }

  public GearState getGearPuncherState() {
    return state;
  }
}
