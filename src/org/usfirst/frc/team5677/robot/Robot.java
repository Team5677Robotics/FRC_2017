package org.usfirst.frc.team5677.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.usfirst.frc.team5677.robot.controllers.ArcadeDrive;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.lib.TrajectoryGenerator;
import org.usfirst.frc.team5677.lib.Segment;
import org.usfirst.frc.team5677.robot.controllers.DriveController;
import org.usfirst.frc.team5677.robot.loops.DriveLoop;
import edu.wpi.first.wpilibj.Notifier;
/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {
    Command autonomousCommand;
    Drive drive;
    ControlBoard controls;
    ArcadeDrive smartDrive; 
    TrajectoryGenerator smartGenerator;
    Segment[] testTrajectory1, testTrajectory2 ;
    DriveController dc;
    DriveLoop dl;
    Notifier n;
    
    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() { 
	drive = Drive.getInstance();
	smartDrive = new ArcadeDrive(0.075, drive);
	controls = new ControlBoard(0, 1);
	smartGenerator = new TrajectoryGenerator(15.0, 20.0, 100);
	System.out.println("Hello Robot");
    }
    
    /**
     * This function is called once each time the robot enters Disabled mode. You can use it to reset
     * any subsystem information you want to clear when the robot is disabled.
     */
    @Override
    public void disabledInit() {}
    
    
    /**
     * This autonomous (along with the chooser code above) shows how to select between different
     * autonomous modes using the dashboard. The sendable chooser code works with the Java
     * SmartDashboard. If you prefer the LabVIEW Dashboard, remove all of the chooser code and
     * uncomment the getString code to get the auto name from the text box below the Gyro
     *
     * <p>You can add additional auto modes by adding additional commands to the chooser code above
     * (like the commented example) or additional comparisons to the switch structure below with
     * additional strings & commands.
     */
    @Override
    public void autonomousInit() {
	//System.out.println("Auto Init");
	testTrajectory1 = smartGenerator.calcTrajectory(0.0,0.0,5.0);
	testTrajectory2 = smartGenerator.calcTrajectory(0.0,0.0,5.0);
	dc = new DriveController(testTrajectory1, testTrajectory2, drive);
	dl = new DriveLoop(dc);
	n = new Notifier(dl);
	n.startPeriodic(0.01);
    }

    @Override
    public void disabledPeriodic() {
	//n.stop(); 
    }
    /** This function is called periodically during autonomous */
    @Override
    public void autonomousPeriodic() {
	Scheduler.getInstance().run();
    }

    @Override
    public void teleopInit() {
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to
	// continue until interrupted by another command, remove
	// this line or comment it out.
	if (autonomousCommand != null) autonomousCommand.cancel();
	//testTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
    }

    /** This function is called periodically during operator control */
    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().run();
	double throttle = controls.getDriveThrottle();
	double turn = controls.getDriveTurn();
	smartDrive.setSpeed(throttle, turn);
    }

    /** This function is called periodically during test mode */
    @Override
    public void testPeriodic() {
	LiveWindow.run();
    }
}
