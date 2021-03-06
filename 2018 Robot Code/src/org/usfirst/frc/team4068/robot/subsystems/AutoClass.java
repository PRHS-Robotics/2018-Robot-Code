package org.usfirst.frc.team4068.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

import java.io.ByteArrayInputStream;

import edu.wpi.first.wpilibj.DoubleSolenoid;
import edu.wpi.first.wpilibj.Solenoid;

public class AutoClass {
	DriveTrain mainDrive;
	Clamp clamp;
	Sonar sonar;
	int t = 0;
	int clampFall = 0;
	int stopt1 = 0;
	int turntime = 0;
	int z = 0;
	DoubleSolenoid grabPneu;// = new DoubleSolenoid(2, 3);
	//int autoTurnTime;
	double a;
	
	Recorder recorder;
	
	public AutoClass(DriveTrain driveTrain, Sonar sonar, Clamp clamp, DoubleSolenoid grabPneu, Recorder recorder) {
		this.mainDrive = driveTrain;
		this.sonar = sonar;
		this.clamp = clamp;
		this.grabPneu = grabPneu;
		this.recorder = recorder;
	}
	
	public void init() {
		t = 0;
		clampFall = 0;
		stopt1 = 0;
		turntime = 0;
		z = 0;
	}
	
	public void auto(double power, boolean run, int LorR) {
		
		sonar.getDistancemm();
		
		// 1 == Left
		// 2 == Right
		
		// LorR /\/\
		
		if (run == true) {
			if (edu.wpi.first.wpilibj.smartdashboard.SmartDashboard.getBoolean("Move forward and Stop (No Switch)", true)) 
			{
				//if (sonar.getDistancemm() > SmartDashboard.getNumber("StopDist", 800)) {
					//a = SmartDashboard.getNumber("AutoTime", 100);
					a = 90;
				if (a > t) {
						mainDrive.drive(0.0, power, 0.0);
					}
					else if ((a + 50) > t) {
						mainDrive.drive(0.0, 0.0, 1);
					} else if ((a + 100) > t){
						mainDrive.drive(0.0, .6, 0.0);
					} else if ((a + 120) > t) {
						clamp.screw(-0.5);
					} else if ((a + 150) > t) {
						mainDrive.drive(0.0, 0.0, 0.0);
						grabPneu.set(DoubleSolenoid.Value.kReverse);
					} else if ((a + 170) > t) {
						grabPneu.set(DoubleSolenoid.Value.kForward);
					} else {
						mainDrive.drive(0.0, 0.0, 0.0);
					}
				t++;
				//}
			} else if (SmartDashboard.getBoolean("Move forward and deposit cube (Left)", false)) {
				if (LorR == 1) {
					if (sonar.getDistancemm() < SmartDashboard.getNumber("StopDist", 550)) {
						mainDrive.drive(0, 0.0, 0.0);
						clampFall++;
						if (clampFall < 30) {
							clamp.screw(-0.5);
						} else {
							grabPneu.set(DoubleSolenoid.Value.kForward);
						}
						
					}else {
						mainDrive.drive(0.0, power, 0.0);
					}
				}//end LorR == 1 
			}

			
			//maybe? turn 45D then forward then s
			
			//UNTESTED move forward, stop, turn, move forward
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
		
		}
	}
}