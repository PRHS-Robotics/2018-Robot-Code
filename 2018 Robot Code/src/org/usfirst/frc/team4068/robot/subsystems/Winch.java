package org.usfirst.frc.team4068.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Winch {
	
	Talon winchMotor = new Talon(6);//Changeable
	
	
	public Winch()
	{
		winchMotor.setInverted(false);
		
		SmartDashboard.putNumber("WinchM", 1);
		
	}
	
	public void coil(double rightJoy)
	{
		rightJoy = rightJoy;
		
		double g = SmartDashboard.getNumber("WinchM", 1);
		winchMotor.set((rightJoy * g));
		SmartDashboard.putNumber("CoilingPower", (rightJoy * g));
			
		
	}
	
	
	
}
