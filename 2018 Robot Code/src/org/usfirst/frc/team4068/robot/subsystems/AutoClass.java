package org.usfirst.frc.team4068.robot.subsystems;
public class AutoClass {
	DriveTrain mainDrive;
	Sonar sonar;
	
	public AutoClass(DriveTrain driveTrain, Sonar sonar) {
		this.mainDrive = driveTrain;
		this.sonar = sonar;
	}
	
	public void auto(double power, boolean run) {
		if (run == true) {
			if (sonar.getDistancemm() < 532) {
				mainDrive.drive(0.0, 0.0, 0.0);
			} else {
				mainDrive.drive(0.0, power, 0.0);
			}
		}
	}
}
