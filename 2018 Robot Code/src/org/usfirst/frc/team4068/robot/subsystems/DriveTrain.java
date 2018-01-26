package org.usfirst.frc.team4068.robot.subsystems;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveTrain {

	Talon LeftFront = new Talon(0);
	Talon LeftBack = new Talon(0);
	Talon RightFront = new Talon(0);
	Talon RightBack = new Talon(0);
	
	double RFM;
	double RBM;
	double LBM;
	double LFM;
	
	public DriveTrain(){
		LeftFront.setInverted(false);
		LeftBack.setInverted(false);
		RightFront.setInverted(false);
		RightBack.setInverted(false);
	
		SmartDashboard.putNumber("RFM", RFM);
		SmartDashboard.putNumber("RBM", RBM);
		SmartDashboard.putNumber("LBM", LBM);
		SmartDashboard.putNumber("LFM", LFM);
	}
	
	
	
	public void drive(double x, double y, double r){
		double rfpower, rbpower, lbpower, lfpower, rrfpower, rrbpower, rlbpower, rlfpower;
		
		if (r >= 0 ) {
			rlfpower = (Math.pow(100, r) - 1) / (99);
			rrfpower = (Math.pow(100, r) - 1) / (99);
			} else {
			rlfpower = -((Math.pow(100, r) - 1) / (99));
			rrfpower = -((Math.pow(100, r) - 1) / (99));
		}
		
		if (y >= 0 ) {
		lfpower = (Math.pow(20, y) - 1) / (19);
		rfpower = (Math.pow(20, y) - 1) / (19);
		} else {
		lfpower = -((Math.pow(20, y) - 1) / (19));
		rfpower = -((Math.pow(20, y) - 1) / (19));
		}
		
		lfpower = lfpower - rlfpower;
		lbpower = -lfpower;
		rfpower = rfpower - rrfpower;
		rbpower = -lfpower;
			
		
		
		
		
		LFM = SmartDashboard.getNumber("LFM", 1);
		LBM = SmartDashboard.getNumber("LBM", 1);
		RFM = SmartDashboard.getNumber("RFM", 1);
		RBM = SmartDashboard.getNumber("RBM", 1);
		
		SmartDashboard.putNumber("Left Front ", lfpower * LFM);
		SmartDashboard.putNumber("Left Back", lbpower * LBM);
		SmartDashboard.putNumber("Right Front", rfpower * RFM);
		SmartDashboard.putNumber("Right Back", rbpower * RBM);
	
		
		
		
		RightFront.set(rfpower * RFM);
		RightBack.set(rbpower * RBM);
		LeftBack.set(lbpower * LBM);
		LeftFront.set(lfpower * LFM);

	}
	
	
	
}
