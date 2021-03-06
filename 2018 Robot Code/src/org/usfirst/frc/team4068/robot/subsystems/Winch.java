package org.usfirst.frc.team4068.robot.subsystems;

import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Winch {
	
	Talon winchMotor = new Talon(6);//Changeable
	//SmartDashboard.putNumber("winchInput")
	
	public Winch()
	{
		winchMotor.setInverted(false);
		
		SmartDashboard.putNumber("WinchM", 1);
		
	}
	
	public void coil(double rightJoy, double voltage)
	{
		rightJoy = DriveTrain.calculateDeadzone(rightJoy, 0.5);
		
		double g = SmartDashboard.getNumber("WinchM", 1);
		winchMotor.set(rightJoy * g);
		//SmartDashboard.putNumber("winchOutput", rightJoy * g);
		SmartDashboard.putNumber("CoilingPower", (rightJoy * g));
	}
	
	
	
}
