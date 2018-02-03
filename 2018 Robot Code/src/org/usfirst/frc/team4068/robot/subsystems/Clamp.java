package org.usfirst.frc.team4068.robot.subsystems;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Clamp {

	Talon screwD = new Talon(5);
	
	double screwM = 1;
	
	public Clamp() {
		
		screwD.setInverted(false);
		
		SmartDashboard.putNumber("screwM: ", screwM);
	}
	
	
	public void screw(double s){	
		
		//double sPow = Math.pow(s, 3);
		
		
		screwM = SmartDashboard.getNumber("screwM", 1);
		
		double sPow = s * screwM;
		
		SmartDashboard.putNumber("switchPower", sPow);
		
		screwD.set(sPow);
		
	}
	
}
