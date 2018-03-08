package org.usfirst.frc.team4068.robot.subsystems;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveTrain {
	DifferentialDrive drive = new DifferentialDrive(
		new SpeedControllerGroup(new Talon(3), new Talon(4)),
		new SpeedControllerGroup(new Talon(2), new Talon(1))
	);
	
	public static double calculateDeadzone(double value, double zone) {
		// Set the value to zero if less than the zone
		if (Math.abs(value) < zone) {
			return 0;
		}
		
		// Scale the rest of the value range
		return Math.signum(value) * (Math.abs(value) - zone) / (1.0 - zone);
	}
	
	double RFM = -1;
	double RBM = 1;
	double LBM = -1;
	double LFM = 1;
	
	double inputCurve = 5;
	
	double deadzone = 0.1;
	
	double maxTurnPower = 0.5;
	
	public DriveTrain(){
		drive.setDeadband(0.0);
		
		SmartDashboard.putNumber("RFM", RFM); // -1 base
		SmartDashboard.putNumber("RBM", RBM); //         (regular 1) 
		SmartDashboard.putNumber("LBM", LBM); // -1 base
		SmartDashboard.putNumber("LFM", LFM); //         (regular 1)
		
		SmartDashboard.putNumber("Input Curve", inputCurve);
		SmartDashboard.putNumber("Deadzone", deadzone);
		SmartDashboard.putNumber("Max Turning Power", maxTurnPower);
		
		drive.setSafetyEnabled(false);
	}
	
	
	
	public void drive(double x, double y, double r){
		LFM = SmartDashboard.getNumber("LFM", 1); // second argument is default power
		LBM = SmartDashboard.getNumber("LBM", 1);
		RFM = SmartDashboard.getNumber("RFM", 1);
		RBM = SmartDashboard.getNumber("RBM", 1);
		
		inputCurve = SmartDashboard.getNumber("Input Curve", inputCurve);
		deadzone = SmartDashboard.getNumber("Deadzone", deadzone);
		maxTurnPower = SmartDashboard.getNumber("Max Turning Power", maxTurnPower);
		
		// Apply deadzone
		x = calculateDeadzone(x, deadzone);
		y = calculateDeadzone(y, deadzone);
		r = calculateDeadzone(r, deadzone);
		
		r *= maxTurnPower;
		
		double lfpower = +y + r;
		double rfpower = +y - r;
		
		// Apply curved
		lfpower = Math.signum(lfpower) * (Math.pow(inputCurve, Math.abs(lfpower)) - 1) / (inputCurve - 1);
		rfpower = Math.signum(rfpower) * (Math.pow(inputCurve, Math.abs(rfpower)) - 1) / (inputCurve - 1);
		
		SmartDashboard.putNumber("Left Front", lfpower * LFM);
		SmartDashboard.putNumber("Right Front", rfpower * RFM);
		
		drive.tankDrive(rfpower * RFM, lfpower * LFM, false);
	}
}
