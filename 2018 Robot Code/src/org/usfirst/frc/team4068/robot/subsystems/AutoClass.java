package org.usfirst.frc.team4068.robot.subsystems;
public class AutoClass {
	DriveTrain mainDrive = new DriveTrain();
	Sonar sonar = new Sonar();
	
	public void auto(double pow, boolean run) {
		
		if (run == true) {
		if (sonar.getDistancemm() < 532) {
			mainDrive.drive(0.0, 0.0, 0.0);
		} else {
			mainDrive.drive(0.0, pow, 0.0);
		}
		}
		
	}
}
