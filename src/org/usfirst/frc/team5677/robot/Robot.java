package org.usfirst.frc.team5677.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Notifier;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import org.usfirst.frc.team5677.lib.Segment;
import org.usfirst.frc.team5677.robot.controllers.ArcadeDrive;
import org.usfirst.frc.team5677.robot.states.ConveyorState;
import org.usfirst.frc.team5677.robot.states.IntakeState;
import org.usfirst.frc.team5677.robot.subsystems.Conveyor;
import org.usfirst.frc.team5677.robot.subsystems.Drive;
import org.usfirst.frc.team5677.robot.subsystems.Intake;

//import org.usfirst.frc.team5677.robot.controllers.DriveController;
//import org.usfirst.frc.team5677.robot.loops.DriveLoop;
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
  //TrajectoryGenerator smartGenerator;
  Segment[] testTrajectory1, testTrajectory2;
  //DriveController dc;
  //DriveLoop dl;
  Notifier n;
  Intake intake;
  Conveyor conveyor;

  /**
   * This function is run when the robot is first started up and should be used for any
   * initialization code.
   */
  @Override
  public void robotInit() {
    drive = Drive.getInstance();
    smartDrive = new ArcadeDrive(0.075, drive);
    controls = new ControlBoard(0, 1);
    //smartGenerator = new TrajectoryGenerator(15.0, 20.0, 100);
    intake = Intake.getInstance();
    conveyor = Conveyor.getInstance();
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
    /*testTrajectory1 = smartGenerator.calcTrajectory(0.0,0.0,5.0);
    testTrajectory2 = smartGenerator.calcTrajectory(0.0,0.0,5.0);
    dc = new DriveController(testTrajectory1, testTrajectory2, drive);
    dl = new DriveLoop(dc);
    n = new Notifier(dl);
    n.startPeriodic(0.01);*/
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

    /** This is just a test condition to make sure that all of the subsystems work properly. */
    if (controls.getIntakeInButton()) {
      intake.toggleIntake(IntakeState.IN);
    } else if (controls.getIntakeOutButton()) {
      intake.toggleIntake(IntakeState.OUT);
    } else {
      intake.toggleIntake(IntakeState.OFF);
    }

    if (controls.getConveyorUpButton()) {
      conveyor.toggleConveyor(ConveyorState.UP);
    } else if (controls.getConveyorDownButton()) {
      conveyor.toggleConveyor(ConveyorState.DOWN);
    } else {
      conveyor.toggleConveyor(ConveyorState.OFF);
    }

    /** This is the actual code that will run the robot for competition. */
    /*
    if(controls.getIntakeBallButton()){
        intake.toggleIntake(IntakeState.IN);
        conveyor.toggleConveyor(ConveyorState.DOWN);
    }else if(controls.getScoreLowGoalButton()){
        intake.toggleIntake(IntakeState.OUT);
        conveyor.toggleConveyor(ConveyorState.UP);
    }else{
        intake.toggleIntake(IntakeState.OFF);
        conveyor.toggleConveyor(ConveyorState.OFF);
    }
    */
  }

  /** This function is called periodically during test mode */
  @Override
  public void testPeriodic() {
    LiveWindow.run();
  }
}