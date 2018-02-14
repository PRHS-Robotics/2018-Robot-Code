package org.usfirst.frc.team4068.robot.subsystems;
import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Sonar {
	
	private AnalogInput sonar = new AnalogInput(0);
	private AnalogInput sonar2 = new AnalogInput(1);
	
	/** gets distance from sonar thing and puts it on
	 *  SmartDashboard and returns it as a double
	 */
	public double getDistancemm() {
		double voltage = sonar.getVoltage();
		SmartDashboard.putNumber("SVoltage", voltage);
		double sensitivity = 0.0098 / 25.4;
		double mm = voltage / sensitivity;
		SmartDashboard.putNumber("sensitivity", sensitivity);
		//SmartDashboard.putNumber("SonarMM", mm);
		return mm;
	}
	
	
	public double getDistancemm2() {
		double voltage2 = sonar2.getVoltage();
		SmartDashboard.putNumber("SVoltage2", voltage2);
		double sensitivity2 = 0.0098 / 25.4;
		double mm2 = voltage2 / sensitivity2;
		SmartDashboard.putNumber("sensitivity2", sensitivity2);
		//SmartDashboard.putNumber("SonarMM", mm);
		return mm2;
	}
}