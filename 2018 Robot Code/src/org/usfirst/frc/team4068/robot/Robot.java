 
package org.usfirst.frc.team4068.robot;

import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.PrintCommand;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.hal.PowerJNI;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
//import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.Compressor;
import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.DriverStation;
//import org.opencv.imgproc.Imgproc;
import edu.wpi.cscore.UsbCamera;
//import edu.wpi.first.wpilibj.vision.VisionRunner;
//import edu.wpi.first.wpilibj.vision.VisionThread;


import org.usfirst.frc.team4068.robot.subsystems.ClimberExtension;
import org.usfirst.frc.team4068.robot.subsystems.Clamp;
import org.usfirst.frc.team4068.robot.subsystems.Sonar;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.usfirst.frc.team4068.robot.commands.ExampleCommand;
import org.usfirst.frc.team4068.robot.subsystems.DriveTrain;
import org.usfirst.frc.team4068.robot.subsystems.ExampleSubsystem;
import org.usfirst.frc.team4068.robot.subsystems.InputState;
import org.usfirst.frc.team4068.robot.subsystems.Recorder;
import org.usfirst.frc.team4068.robot.subsystems.Winch;
import org.usfirst.frc.team4068.robot.subsystems.AutoClass;
import edu.wpi.first.wpilibj.CameraServer;


/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the manifest file in the resource
 * directory.
 */
public class Robot extends IterativeRobot {
	
	
	private static final int IMG_WIDTH = 640;
	private static final int IMG_HEIGHT = 480;
	
	private static double currentVoltage;

	Joystick driveStick = new Joystick(1);
	Joystick xBox = new Joystick(2);
	DriveTrain mainDrive = new DriveTrain();
	ClimberExtension climb = new ClimberExtension();
	Clamp screwDrive = new Clamp();
	Winch winch = new Winch();
	Compressor compressor = new Compressor();
	Solenoid climPneu = new Solenoid(1);
	DoubleSolenoid grabPneu = new DoubleSolenoid(2, 3);
	Sonar sonar = new Sonar();
	Recorder recorder = new Recorder();
	AutoClass aut = new AutoClass(mainDrive, sonar, screwDrive, grabPneu, recorder);
	
	private Object voltageLock = new Object();

	public static final ExampleSubsystem exampleSubsystem = new ExampleSubsystem();
	public static OI oi;

	Command autonomousCommand;
	SendableChooser<Command> chooser = new SendableChooser<>();

	/**
	 * This function is run when the robot is first started up and should be used
	 * for any initialization code.
	 */
	@Override
	public void robotInit() {
		SmartDashboard.putBoolean("Load Auto From File", true);
		oi = new OI();
		chooser.addDefault("Default Auto", new ExampleCommand());
		// chooser.addObject("My Auto", new MyAutoCommand());
		UsbCamera camera = CameraServer.getInstance().startAutomaticCapture();
		camera.setResolution(640, 480);
		SmartDashboard.putData("Auto mode", chooser);
		SmartDashboard.putNumber("AutoSpeed", 0.7);
		SmartDashboard.putNumber("AutoTime", 200);
		SmartDashboard.putString("AutoVersion (for , basic)", "Basic");
		SmartDashboard.putNumber("SonarMM", sonar.getDistancemm());
		SmartDashboard.putNumber("StopDist", 1000);
		SmartDashboard.putNumber("Auto Turn Time", 25);
		SmartDashboard.putBoolean("Move forward and Stop (No Switch)", false);
		SmartDashboard.putBoolean("Move forward and deposit cube (Left)", false);
		aut.auto(0.0, false, 3);
		
		new Thread() {
			public void run() {
				while (Robot.this.isAutonomous() && Robot.this.isEnabled()) {
					double voltage = PowerJNI.getVinVoltage();
					synchronized (voltageLock) {
						currentVoltage = voltage;
						SmartDashboard.putNumber("Battery Voltage", currentVoltage);
					}
				}
			}
		}.start();
	}

	/**
	 * This function is called once each time the robot enters Disabled mode. You
	 * can use it to reset any subsystem information you want to clear when the
	 * robot is disabled.
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
	 * between different autonomous modes using the dashboard. The sendable chooser
	 * code works with the Java SmartDashboard. If you prefer the LabVIEW Dashboard,
	 * remove all of the chooser code and uncomment the getString code to get the
	 * auto name from the text box below the Gyro
	 *
	 * You can add additional auto modes by adding additional commands to the
	 * chooser code above (like the commented example) or additional comparisons to
	 * the switch structure below with additional strings & commands.
	 */
	@Override
	public void autonomousInit() {
		// autonomousCommand = chooser.getSelected();
		//
		//
		// String autoSelected = SmartDashboard.getString("Auto Selector", "Default");
		// switch(autoSelected) {
		// case "My Auto":
		// autonomousCommand = new ForwardAuto();
		// break;
		// case "Default Auto":
		// default:
		// autonomousCommand = new ExampleCommand();
		// break;
		// }
		//
		//
		// // schedule the autonomous command (example)
		// if (autonomousCommand != null)
		// autonomousCommand.start();
		sonar.getDistancemm();
		sonar.getDistancemm2();
		grabPneu.set(DoubleSolenoid.Value.kReverse);
		aut.init();
		
		try {
			Path path = Paths.get("/home/lvuser/Auto1.auto");
			byte[] data = Files.readAllBytes(path);
			ByteArrayInputStream stream = new ByteArrayInputStream(data);
			
			while (stream.available() >= 10) {
				InputState input = recorder.getNextInput(stream);
				
				drive(input);
				
	    		Thread.sleep((int)Math.round(20.0 * input.getVoltage() / getVoltage()));
			}
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	/**
	 * This function is called periodically during autonomous
	 */
	@Override
	public void autonomousPeriodic() {
		//grabPneu.set(DoubleSolenoid.Value.kReverse);
		/*sonar.getDistancemm();
		sonar.getDistancemm2();
		 String gameData;
		 gameData = DriverStation.getInstance().getGameSpecificMessage();
		 if(gameData.charAt(0) == 'L')
		 {
			 aut.auto(SmartDashboard.getNumber("AutoSpeed", 0.7), true, 1);
		 } else {
			 aut.auto(SmartDashboard.getNumber("AutoSpeed", 0.7), true, 2); 
		 }
		 Scheduler.getInstance().run();*/
		
		//aut.auto(SmartDashboard.getNumber("AutoSpeed", 0.7), true);
	}

	@Override
	public void teleopInit() {
		// This makes sure that the autonomous stops running when
		// teleop starts running. If you want the autonomous to
		// continue until interrupted by another command, remove
		// this line or comment it out.
		if (autonomousCommand != null)
			autonomousCommand.cancel();
		aut.auto(0.0, false, 3);
		compressor.setClosedLoopControl(true);
	}
	
	public void drive(InputState state) {
		Scheduler.getInstance().run();

		SmartDashboard.putNumber("SonarMM", sonar.getDistancemm());
		SmartDashboard.putNumber("SonarMM2", sonar.getDistancemm2());

		sonar.getDistancemm();
		sonar.getDistancemm2();
		
		
		double r = -state.getTwist();
		double y = -state.getY();
		double x = state.getX();

		SmartDashboard.putNumber("Y Input", y);
		SmartDashboard.putNumber("R Input", r);
		SmartDashboard.putNumber("x Input", x);

		mainDrive.drive(x, y, r);

		double s = -state.getXboxAxis(1);

		double w = state.getXboxAxis(5);

		winch.coil((Math.abs(w) > .2) ? w : 0);

		screwDrive.screw((Math.abs(s) > .1) ? s : 0);

		
		
		if (state.getButton(1)) {
			climPneu.set(true);
		} else {
			climPneu.set(false);
		}

		
		
		SmartDashboard.putNumber("Double Solenoid KForward", 0);
		SmartDashboard.putNumber("Double Solenoid KReverse", 0);
		SmartDashboard.putNumber("Double Solenoid KOff", 0);

		if (state.getXboxAxis(2) > .5) {
			SmartDashboard.putNumber("Double Solenoid KForward", 1);
			grabPneu.set(DoubleSolenoid.Value.kForward);
			// System.out.println("Double SOlenoid KForward");
		} else if (state.getXboxAxis(3) > .5) {
			SmartDashboard.putNumber("Double Solenoid KReverse", 1);
			grabPneu.set(DoubleSolenoid.Value.kReverse);
			// System.out.println("Double SOlenoid KReverse");
		} else {
			SmartDashboard.putNumber("Double Solenoid KOff", 1);
			grabPneu.set(DoubleSolenoid.Value.kOff);
			// System.out.println("Double SOlenoid KOff");
		}
	}

	/**
	 * This function is called periodically during operator control
	 */
	@Override
	public void teleopPeriodic() {
		InputState state = new InputState(driveStick, xBox, getVoltage());
		
		drive(state);
		
		// TODO: Find the Y button
		if (state.getButton(4)) {
			recorder.recordInput(state);
		}
		
		// TODO: Find an unused button to save the data
		if (state.getButton(3)) {
			try {
				File file = new File("/home/lvuser/Auto1.auto");
				if (!file.exists()) {
					file.createNewFile();
				}
				FileOutputStream output = new FileOutputStream(file);
				output.write(recorder.getBytes());
				output.close();
			}
			catch (Exception exception) {
				exception.printStackTrace();
			}
		}
	}
	
	private double getVoltage() {
		synchronized (voltageLock) {
			return currentVoltage;
		}
	}

	/**
	 * This function is called periodically during test mode
	 */
	@Override
	public void testPeriodic() {
	}
}