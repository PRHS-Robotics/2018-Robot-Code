
package org.usfirst.frc.team4068.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.RobotDrive;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.livewindow.LiveWindow;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;

import org.usfirst.frc.team4068.robot.subsystems.ClimberExtension;
import org.usfirst.frc.team4068.robot.subsystems.Clamp;

import org.usfirst.frc.team4068.robot.commands.ExampleCommand;
import org.usfirst.frc.team4068.robot.commands.ForwardAuto;
import org.usfirst.frc.team4068.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4068.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team4068.robot.subsystems.Winch;

import java.io.ByteArrayInputStream;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {

	Joystick driveStick = new Joystick(1);
	Joystick xBox = new Joystick(2);
	private RobotDrive drive;
	DriveTrain mainDrive = new DriveTrain();
	ClimberExtension climb = new ClimberExtension();
	Clamp screwDrive = new Clamp();
	Winch winch = new Winch();
	//Compressor compressor = new Compressor();
	Solenoid climPneu = new Solenoid(1);
	//DoubleSolenoid grabPneu = new DoubleSolenoid(2, 3);
	
	
	
	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;
	int screwAxis;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putNumber("screwAxis", screwAxis);
		SmartDashboard.putNumber("AutoSpeed", 0.7);
		SmartDashboard.putNumber("AutoTime", 200);
	}

	/**
	 * This function is called once each time the robot enters Disabled mode.
	 * You can use it to reset any subsystem information you want to clear when
	 * the robot is disabled.
	 */
	@Override
	public void disabledInit() {

	}

	@Override
	public void disabledPeriodic() {
		Scheduler.getInstance().run();
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString code to get the auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons
	 * to the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		autonomousCommand = chooser.getSelected();

		
		  String autoSelected = SmartDashboard.getString("Auto Selector", "Default"); 
		  switch(autoSelected) { 
		  case "My Auto": 
			  autonomousCommand = new ForwardAuto(); 
			  break; 
		  case "Default Auto": 
			  default:
		  autonomousCommand = new ExampleCommand();
		  break; 
		  }
		 

		// schedule the autonomous command (example)
		if (autonomousCommand != null)
			autonomousCommand.start();
	}

	/**          
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		String gameData;
		gameData = DriverStation.getInstance().getGameSpecificMessage();
		if(gameData.charAt(0) == 'L')
		{
			//Put left auto code here
		} else {
			//Put right auto code here
		}
		Scheduler.getInstance().run();
	}
	
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
	} 

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		
		
		//double exp = 1.0;
		
		double r = -driveStick.getAxis(Joystick.AxisType.kTwist);
    	double y = -driveStick.getAxis(Joystick.AxisType.kY);
    	double x = driveStick.getAxis(Joystick.AxisType.kX);
    	
    	SmartDashboard.putNumber("Y Input", y);
    	SmartDashboard.putNumber("R Input", r);
    	SmartDashboard.putNumber("x Input", x);
    	
    	/*
    	x = Math.signum(x) * Math.pow(Math.abs(x), exp);
    	y = Math.signum(y) * Math.pow(Math.abs(y), exp);
    	r = Math.signum(r) * Math.pow(Math.abs(r), exp);
		*/
    	mainDrive.drive((Math.abs(x)>.2)?x:0, (Math.abs(y)>.2)?y:0, (Math.abs(r)>.1)?r:0);
    	
    	//double s = xBox.getRawAxis(SmartDashboard.getNumber("screwAxis", 1));
    	double s = -xBox.getRawAxis(1);
    	
    	double w = xBox.getRawAxis(5);
    	
    	winch.coil((Math.abs(w)>.2)?w:0);
    	
    	screwDrive.screw((Math.abs(s)>.1)?s:0);
    	
    	
    	
    	
    	if (xBox.getRawButton(0)) {
    		climPneu.set(true);
    	} else {
    		climPneu.set(false);
    	}
    	
    	
    	
//    	if (xBox.getRawAxis(3) > .5) {
//    		grabPneu.set(DoubleSolenoid.Value.kForward);
//    	} else if (xBox.getRawAxis(2) > .5) {
//    		grabPneu.set(DoubleSolenoid.Value.kReverse);
//    	} else {
//    		grabPneu.set(DoubleSolenoid.Value.kOff);
//    	}
    	
    	
    	
	}

	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
		LiveWindow.run();
	} 
}