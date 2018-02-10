
package org.usfirst.frc.team4068.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;

import org.usfirst.frc.team4068.robot.subsystems.ClimberExtension;
import org.usfirst.frc.team4068.robot.subsystems.Clamp;
import org.usfirst.frc.team4068.robot.subsystems.Sonar;
import org.usfirst.frc.team4068.robot.commands.ExampleCommand;
import org.usfirst.frc.team4068.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4068.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team4068.robot.subsystems.Winch;
import org.usfirst.frc.team4068.robot.subsystems.AutoClass;


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
	DriveTrain mainDrive = new DriveTrain();
	ClimberExtension climb = new ClimberExtension();
	Clamp screwDrive = new Clamp();
	Winch winch = new Winch();
	Compressor compressor = new Compressor();
	Solenoid climPneu = new Solenoid(1);
	DoubleSolenoid grabPneu = new DoubleSolenoid(2, 3);
	AutoClass aut = new AutoClass(mainDrive);
	Sonar sonar = new Sonar();
	
	
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
		SmartDashboard.putString("AutoVersion (for , basic)", "Basic");
		SmartDashboard.putNumber("SonarMM", sonar.getDistancemm());
		aut.auto(0.0, false);
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
//		autonomousCommand = chooser.getSelected();
//
//		
//		  String autoSelected = SmartDashboard.getString("Auto Selector", "Default"); 
//		  switch(autoSelected) { 
//		  case "My Auto": 
//			  autonomousCommand = new ForwardAuto(); 
//			  break; 
//		  case "Default Auto": 
//			  default:
//		  autonomousCommand = new ExampleCommand();
//		  break; 
//		  }
//		 
//
//		// schedule the autonomous command (example)
//		if (autonomousCommand != null)
//			autonomousCommand.start();
	}

	/**          
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
//		String gameData;
//		gameData = DriverStation.getInstance().getGameSpecificMessage();
//		if(gameData.charAt(0) == 'L')
//		{
//			//Put left auto code here
//		} else {
//			//Put right auto code here
//		}
//		Scheduler.getInstance().run();
//		
		aut.auto(SmartDashboard.getNumber("AutoSpeed", 0.7), true);
	}
	
	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		aut.auto(0.0, false);
		compressor.setClosedLoopControl(true);
	} 

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		Scheduler.getInstance().run();
		
		SmartDashboard.putNumber("SonarMM", sonar.getDistancemm());

		double r = -driveStick.getTwist();
		double y = -driveStick.getY();
		double x =  driveStick.getX();
    	
    	SmartDashboard.putNumber("Y Input", y);
    	SmartDashboard.putNumber("R Input", r);
    	SmartDashboard.putNumber("x Input", x);
    	
    	mainDrive.drive(x, y, r);
    	
    	//double s = xBox.getRawAxis(SmartDashboard.getNumber("screwAxis", 1));
    	double s = -xBox.getRawAxis(1);
    	
    	double w = xBox.getRawAxis(5);
    	
    	winch.coil((Math.abs(w)>.2)?w:0);
    	
    	screwDrive.screw((Math.abs(s)>.1)?s:0);
    	
    	if (xBox.getRawButton(1)) {
    		climPneu.set(true);
    	} else {
    		climPneu.set( false);
    	}
    	
    	SmartDashboard.putNumber("Double Solenoid KForward", 0);
    	SmartDashboard.putNumber("Double Solenoid KReverse", 0);
    	SmartDashboard.putNumber("Double Solenoid KOff", 0);
    	
    	if (xBox.getRawAxis(2) > .5) {
    		SmartDashboard.putNumber("Double Solenoid KForward", 1);
    		grabPneu.set(DoubleSolenoid.Value.kForward);
    		//System.out.println("Double SOlenoid KForward");
    	} else if (xBox.getRawAxis(3) > .5) {
    		SmartDashboard.putNumber("Double Solenoid KReverse", 1);
    		grabPneu.set(DoubleSolenoid.Value.kReverse);
    		//System.out.println("Double SOlenoid KReverse");
    	} else {
    		SmartDashboard.putNumber("Double Solenoid KOff", 1);
    		grabPneu.set(DoubleSolenoid.Value.kOff);
    		//System.out.println("Double SOlenoid KOff");
    	}
    	
    	
    	
    	
    	
	}

	
	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	} 
}