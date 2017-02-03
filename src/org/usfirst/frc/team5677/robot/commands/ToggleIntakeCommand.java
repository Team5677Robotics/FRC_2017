package org.usfirst.frc.team5677.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import org.usfirst.frc.team5677.robot.subsystems.Intake;

/**
 * Toggles the intake motor
 *
 * @author Vedaad Shakib
 */
public class ToggleIntakeCommand extends Command {
    Intake intake;

    public ToggleIntakeCommand() {
	intake = Intake.getInstance();
    }

    // Called just before this Command runs the first time
    protected void initialize() {
	intake.toggleIntake();
    }

    // Called repeatedly when this Command is scheduled to run
    protected void execute() {}

    // Make this return true when this Command no longer needs to run execute()
    protected boolean isFinished() {
	return true;
    }

    // Called once after isFinished returns true
    protected void end() {}

    // Called when another command which requires one or more of the same
    // subsystems is scheduled to run
    protected void interrupted() {}
}
