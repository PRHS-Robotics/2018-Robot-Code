package org.usfirst.frc.team4068.robot.subsystems;
import edu.wpi.first.wpilibj.Talon;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;


public class DriveTrain {

	Talon LeftFront = new Talon(3);
	Talon LeftBack = new Talon(4);
	Talon RightFront = new Talon(1);
	Talon RightBack = new Talon(2);
	
	double RFM = -1;
	double RBM = 1;
	double LBM = -1;
	double LFM = 1;
	
	public DriveTrain(){
		
		LeftFront.setInverted(false);
		LeftBack.setInverted(false);
		RightFront.setInverted(false);
		RightBack.setInverted(false);
	
		SmartDashboard.putNumber("RFM", RFM); // -1 base
		SmartDashboard.putNumber("RBM", RBM); //         (regular 1) 
		SmartDashboard.putNumber("LBM", LBM); // -1 base
		SmartDashboard.putNumber("LFM", LFM); //         (regular 1)
	}
	
	
	
	public void drive(double x, double y, double r){
		double rfpower, rbpower, lbpower, lfpower, rrfpower, rrbpower, rlbpower, rlfpower;
		/*
		if (r >= 0 ) {
			rlfpower = (Math.pow(100, r) - 1) / (99);
			rrfpower = -(Math.pow(100, r) - 1) / (99);
			} else {
			rlfpower = -((Math.pow(100, -r) - 1) / (99));
			rrfpower = ((Math.pow(100, -r) - 1) / (99));
		}
		
		if (y >= 0 ) {
		lfpower = (Math.pow(20, y) - 1) / (19);
		rfpower = (Math.pow(20, y) - 1) / (19);
		} else {
		lfpower = -((Math.pow(20, -y) - 1) / (19));
		rfpower = -((Math.pow(20, -y) - 1) / (19));
		}
		
		lfpower = lfpower - rlfpower;
		lbpower = -lfpower;
		rfpower = rfpower - rrfpower;
		rbpower = -rfpower;
		*/
		
		lfpower = y;
		rfpower = y;
		
		lfpower = lfpower - r;
		rfpower = rfpower + r;
		
		rbpower = -rfpower;
		lbpower = -lfpower;
		
		
		
		
		
		
		
		
		LFM = SmartDashboard.getNumber("LFM", 1); // second argument is default power
		LBM = SmartDashboard.getNumber("LBM", 1);
		RFM = SmartDashboard.getNumber("RFM", 1);
		RBM = SmartDashboard.getNumber("RBM", 1);
		
		
		SmartDashboard.putNumber("Left Front", lfpower * LFM);
		SmartDashboard.putNumber("Left Back", lbpower * LBM);
		SmartDashboard.putNumber("Right Front", rfpower * RFM);
		SmartDashboard.putNumber("Right Back", rbpower * RBM);
	
		
		
		
		RightFront.set(rfpower * RFM);
		RightBack.set(rbpower * RBM);
		LeftBack.set(lbpower * LBM);
		LeftFront.set(lfpower * LFM);
		
		/*
		RightFront.set(SmartDashboard.getNumber("Right Front", 0));
		RightBack.set(SmartDashboard.getNumber("Right Back", 0));
		LeftBack.set(SmartDashboard.getNumber("Left Front", 0));
		LeftFront.set(SmartDashboard.getNumber("Left Back", 0));
		*/
	}
	
	
	
}
