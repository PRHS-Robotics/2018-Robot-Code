package org.usfirst.frc.team4068.robot.commands;

import edu.wpi.first.wpilibj.command.Command;

import org.usfirst.frc.team4068.robot.Robot;
import org.usfirst.frc.team4068.robot.subsystems.DriveTrain;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 *
 */
public class ForwardAuto extends Command {
	DriveTrain mainDrive = new DriveTrain();
	public int t = 0;
	public double d = 0;
	public double a = 0;
	
	
	public ForwardAuto() {
		// Use requires() here to declare subsystem dependencies
		requires(Robot.exampleSubsystem);
		//requires(mainDrive);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		d = SmartDashboard.getNumber("AutoSpeed", 0.7);
		a = SmartDashboard.getNumber("AutoTime", 200);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		
		t++;
		SmartDashboard.putNumber("timer", t);
		if (t < a) {
			mainDrive.drive(0, d, 0);
		} else {
			mainDrive.drive(0, 0, 0);
			end();
		}
		
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
	}

	// Called when another command which requires one or more of the same
	// subsystems is scheduled to run
	@Override
	protected void interrupted() {
	}
}
