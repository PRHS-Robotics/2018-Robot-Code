package org.usfirst.frc.team4068.robot.commands;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import org.usfirst.frc.team4068.robot.Robot;
import org.usfirst.frc.team4068.robot.subsystems.DriveTrain;

public class FAutoCommand extends Command {
		DriveTrain mainDrive = new DriveTrain();
		int time;
		
	// Called just before this Command runs the first time
		@Override
		protected void initialize() {
			
		}

		// Called repeatedly when this Command is scheduled to run
		@Override
		protected void execute() {
		time = time + 1;
		if (time<200) {
		SmartDashboard.getNumber("FAutoPower", 0);
		mainDrive.drive(0, 1, 0);
		}
		} 

		@Override
		protected boolean isFinished() {
			// TODO Auto-generated method stub
			return false;
		}

}
