package org.usfirst.frc.team4068.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class AutoClass {
	DriveTrain mainDrive;
	Sonar sonar;
	int t;
	int stopt1 = 0;
	int turntime = 0;
	//int autoTurnTime;
	double a;
	
	public AutoClass(DriveTrain driveTrain, Sonar sonar) {
		this.mainDrive = driveTrain;
		this.sonar = sonar;
	}
	
	public void auto(double power, boolean run) {
		if (run == true) {
			
			//UNTESTED move forward, stop, turn, monve forward
//			if (sonar.getDistancemm() < SmartDashboard.getNumber("StopDist", 1000)) {
//				mainDrive.drive(0.0, 0.0, 0.0);
//				stopt1++;
//			} else  if (stopt1 < 5){
//				mainDrive.drive(0.0, power, 0.0);
//			} else if (stopt1 < (SmartDashboard.getNumber("Auto Turn Time", 25) - 5) && turntime < SmartDashboard.getNumber("Auto Turn Time", 25)) {
//				turntime++;
//				mainDrive.drive(0.0, 0.0, power);
//			} else if (stopt1 < (SmartDashboard.getNumber("Auto Turn Time", 25) - 5) && sonar.getDistancemm() < SmartDashboard.getNumber("StopDist", 1000)) {
//				mainDrive.drive(0.0, power, 0.0);
//			}
			
			SmartDashboard.putNumber("stopt", stopt1);
		
		}/*
		a = SmartDashboard.getNumber("AutoTime", 200);
		if (a > t) {
			mainDrive.drive(0, power, 0.0);
		}
		t++;
		*/
	}
}
