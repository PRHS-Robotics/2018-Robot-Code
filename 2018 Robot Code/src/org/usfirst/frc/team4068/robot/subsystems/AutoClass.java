package org.usfirst.frc.team4068.robot.subsystems;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
	
	public AutoClass(DriveTrain driveTrain, Sonar sonar, Clamp clamp, DoubleSolenoid grabPneu) {
		this.mainDrive = driveTrain;
		this.sonar = sonar;
		this.clamp = clamp;
		this.grabPneu = grabPneu;
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
			
			if (SmartDashboard.getBoolean("Move forward and Stop (No Switch)", false) == true) 
			{
				//if (sonar.getDistancemm() > SmartDashboard.getNumber("StopDist", 600)) {
					a = SmartDashboard.getNumber("AutoTime", 200);
					SmartDashboard.putNumber("AutoCounter", t);
					if (t < 150) {
						mainDrive.drive(0.0, power, 0.0);
					}
					else {
						
						mainDrive.drive(0.0, 0.0, 0.0);
					}
				t++;
				//}
			} else if (SmartDashboard.getBoolean("Move forward and deposit cube (Left)", false) == true) {
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
			
			else if (SmartDashboard.getBoolean("Move forward and deposit cube (right)", false)) {
				if (LorR == 2) {
					//left
					//turn left 90D
					mainDrive.drive(0, power, -1.0);//turn left
					//timer here
					mainDrive.drive(0, power, 0.0);
					//timer here
					mainDrive.drive(0, power, 1.0);
					//timer here
					if (sonar.getDistancemm() < SmartDashboard.getNumber("StopDist", 800)) {
						mainDrive.drive(0.0, 0.0, 0.0);
						++clampFall;
						if (clampFall < 30) {
							clamp.screw(-0.5);
						} else {
							grabPneu.set(DoubleSolenoid.Value.kReverse);
						}
					} else {
							mainDrive.drive(0,power,0.0);
					}
				}
			} else {
				mainDrive.drive(0.0, 0.0, 0.0);
//				if (z < SmartDashboard.getNumber("AutoTime", 200)) {
//					mainDrive.drive(0.0, power, 0.0);
//				}
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