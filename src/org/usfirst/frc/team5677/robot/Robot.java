package org.usfirst.frc.team5677.robot;

import edu.wpi.first.wpilibj.CameraServer;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.CameraServer;
import org.usfirst.frc.team5677.lib.trajectory.Segment;
import org.usfirst.frc.team5677.lib.trajectory.TrajectoryGenerator;
import org.usfirst.frc.team5677.robot.controllers.ArcadeDrive;
import org.usfirst.frc.team5677.lib.logging.Logger;
import org.usfirst.frc.team5677.robot.states.ConveyorState;
import org.usfirst.frc.team5677.robot.states.IntakeState;
import org.usfirst.frc.team5677.robot.states.GearState;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.robot.subsystems.Gear;
import org.usfirst.frc.team5677.robot.subsystems.GearPuncher;
import org.usfirst.frc.team5677.robot.subsystems.Hanger;
import org.usfirst.frc.team5677.robot.controllers.DriveController;
import org.usfirst.frc.team5677.robot.loops.DriveLoop;
import org.usfirst.frc.team5677.robot.auto.RightGearOnlyMode;
import org.usfirst.frc.team5677.robot.auto.StraightOnlyMode;
import edu.wpi.first.wpilibj.Compressor;

/**
 * The VM is configured to automatically run this class, and to call the functions corresponding to
 * each mode, as described in the IterativeRobot documentation. If you change the name of this class
 * or the package after creating this project, you must also update the manifest file in the
 * resource directory.
 */
public class Robot extends IterativeRobot {
    Logger		logger;
    Command		autonomousCommand;
    Drive		drive;
    ControlBoard	controls;
    ArcadeDrive		smartDrive; 
    TrajectoryGenerator smartGenerator;
    RightGearOnlyMode	rightGearAutoMode;
    StraightOnlyMode    straightMode;
    Compressor		compressor;
    Gear		gear;
    GearPuncher		gearPuncher;
    Hanger		hanger;
    SendableChooser     autoChooser;
    CameraServer        cam;
    Notifier n;
    /**
     * This function is run when the robot is first started up and should be used for any
     * initialization code.
     */
    @Override
    public void robotInit() {
	drive	       = Drive.getInstance();
	smartDrive     = new ArcadeDrive(0.075, drive);
	controls       = new ControlBoard(0, 1);
	smartGenerator = new TrajectoryGenerator(15.0, 16.0, 100); 
	gear	       = Gear.getInstance();
	gearPuncher    = GearPuncher.getInstance();
	hanger	       = Hanger.getInstance();
	System.out.println("Hello Robot");
	compressor     = new Compressor();
	compressor.setClosedLoopControl(true); 
	drive.resetEncoders();
	logger		  = new Logger();
	
	straightMode	  = new StraightOnlyMode(drive, smartGenerator, logger, gear, gearPuncher);
	rightGearAutoMode = new RightGearOnlyMode(drive, smartGenerator, logger);

	autoChooser       = new SendableChooser();
	autoChooser.addDefault("Right Gear Mode", rightGearAutoMode);
	autoChooser.addObject("Straight Mode", straightMode);
	SmartDashboard.putData("Auto mode chooser", autoChooser);

       
	cam = CameraServer.getInstance();
	cam.startAutomaticCapture("cam0",0);
	n = new Notifier(straightMode);
	//n = new Notifier(rightGearAutoMode);
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
	//drive.setRightSpeed(1.0);
   
	//System.out.println(drive.angleToDistance(45.0)+" D");
	drive.resetEncoders();
	//n = new Notifier((Runnable) autoChooser.getSelected());
	n.startPeriodic(0.01);
    }
    
    public void disabledPeriodic() {
	//n.stop();
    }
    /** This function is called periodically during autonomous */
    @Override
    public void autonomousPeriodic() {
	//Scheduler.getInstance().run(); 
      
    }

    @Override
    public void teleopInit() {
	// This makes sure that the autonomous stops running when
	// teleop starts running. If you want the autonomous to
	// continue until interrupted by another command, remove
	// this line or comment it out.
	n.stop();
	
	if (autonomousCommand != null) autonomousCommand.cancel();
	//testTalon.changeControlMode(CANTalon.TalonControlMode.PercentVbus);
	drive.resetEncoders();
	drive.isEncodersPresent();
    }

    /** This function is called periodically during operator control */
    @Override
    public void teleopPeriodic() {
	Scheduler.getInstance().run();
	
	double	throttle = controls.getDriveThrottle();
	double	turn	 = controls.getDriveTurn();
	
	smartDrive.setSpeed(throttle, turn); 
	System.out.println("Left Encoder= "+drive.getLeftEncoder()+" ------ Right Encoder: "+drive.getRightEncoder());

	/** This is the actual code that will run the robot for competition. */

	if (controls.getLowGearButton()) {
	    drive.shiftLowGear(true);
	    //System.out.println("test");
	} else {
	    drive.shiftLowGear(false);
	}

	if (controls.getFeedGearButton()) {
	    gear.toggleGear(GearState.LOAD);
	    gearPuncher.toggleGearPuncher(GearState.LOAD);
	} else if (controls.getScoreGearButton()) {
	    gear.toggleGear(GearState.SHOOT);
	    Timer.delay(0.5);
	    gearPuncher.toggleGearPuncher(GearState.SHOOT);
	} else {
	    gear.toggleGear(GearState.LOAD);
	    gearPuncher.toggleGearPuncher(GearState.LOAD);
	}

	if (controls.getHangButton()) {
	    hanger.runHanger();
	} else {
	    hanger.stopHanger();
	}

	//if (controls.getCorrectionButton()) {
	//  gear.closeTop();
	//} 
    }

    /** This function is called periodically during test mode */
    @Override
    public void testPeriodic() {
	LiveWindow.run();
    }
}
