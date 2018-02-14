package org.usfirst.frc.team4068.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoClass {
	DriveTrain mainDrive;
	Sonar sonar;
	int t;
	double a;
	
	public AutoClass(DriveTrain driveTrain, Sonar sonar) {
		this.mainDrive = driveTrain;
		this.sonar = sonar;
	}
	
	public void auto(double power, boolean run) {
		if (run == true) {
			if (sonar.getDistancemm() < SmartDashboard.getNumber("StopDist", 1000)) {
				mainDrive.drive(0.0, 0.0, 0.0);
			} else {
				mainDrive.drive(0.0, power, 0.0);
			}
			
		
		}/*
		a = SmartDashboard.getNumber("AutoTime", 200);
		if (a > t) {
			mainDrive.drive(0, power, 0.0);
		}
		t++;
		*/
	}
}
