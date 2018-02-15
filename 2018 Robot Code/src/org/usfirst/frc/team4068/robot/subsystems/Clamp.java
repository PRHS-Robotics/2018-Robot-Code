package org.usfirst.frc.team4068.robot.subsystems;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class Clamp {

	Talon screwD = new Talon(5);
	DigitalInput limitSwitch = new DigitalInput(1);
	
	double screwM = 1;
	
	public Clamp() {
		
		screwD.setInverted(false);
		
		SmartDashboard.putNumber("Screw Sensitivity", screwM);
	}
	
	
	public void screw(double rawPower){
		screwM = SmartDashboard.getNumber("Screw Sensitivity", 1);
		
		double screwPower = Math.pow(rawPower * screwM, 3);
		
		
		if (screwPower < 0){
			screwPower = screwPower * .25;
		}
		
		SmartDashboard.putNumber("switchPower", screwPower);
		
		
		if (limitSwitch.get() && screwPower < 0) {
		//screwD.set(screwPower);
		} else {
		screwD.set(screwPower);
		}
	
	}
	
}
