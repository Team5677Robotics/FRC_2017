package org.usfirst.frc.team5677.robot;

import edu.wpi.first.wpilibj.Joystick;

import org.usfirst.frc.team5677.robot.subsystems.Conveyor;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.robot.subsystems.Intake;

import org.usfirst.frc.team5677.robot.commands.ArcadeManualDriveCommand;
import org.usfirst.frc.team5677.robot.commands.ToggleConveyorCommand;
import org.usfirst.frc.team5677.robot.commands.ToggleIntakeCommand;

import org.usfirst.frc.team5677.robot.wrappers.GamepadWrapper;
import org.usfirst.frc.team5677.robot.wrappers.JoystickButtonWrapper;
/**
 * This class allows the joysticks to interface with the robot.
 *
 * @author Vedaad Shakib
 * @version 02/02/17
 */
public class OI {
    private static OI oi = new OI();
    private static Joystick joystickLeft;
    private static Joystick joystickRight;

    /*
     * Creates joystick instances and maps buttons
     */
    public OI() {
	joystickLeft = new Joystick(0);
	joystickRight = new Joystick(1);

	//intake
	JoystickButtonWrapper button4 = new JoystickButtonWrapper(joystickLeft, 4);
	button5.whenPressed(new ToggleIntakeCommand());

	JoystickButtonWrapper button6 = new JoystickButtonWrapper(joystickLeft, 6);
	button6.whenPressed(new ToggleConveyorCommand());
    }

    public static OI getInstance() {
	initialize();
	return oi;
    }

    public static Joystick getRightJoystick() {
	return joystickRight;
    }

    public static Joystick getLeftJoystick() {
	return joystickLeft;
    }
}
