package org.usfirst.frc.team4068.robot.subsystems;

import edu.wpi.first.wpilibj.Joystick;

public class InputState {
	double x = 0.0;
	double y = 0.0;
	double r = 0.0;
	
	double auxAxes[] = new double[5];
	boolean buttons[] = new boolean[8];
	
	double voltage = 0.0;
	
	public InputState() {
		
	}
	
	public InputState(Joystick driveStick, Joystick auxStick, double voltage) {
		x = driveStick.getX();
		y = driveStick.getY();
		r = driveStick.getTwist();
		for (int i = 1; i <= 5; ++i) {
			auxAxes[i - 1] = auxStick.getRawAxis(i);
		}
		for (int i = 1; i <= 8; ++i) {
			buttons[i - 1] = auxStick.getRawButton(i);
		}
	}
	
	public double getX() {
		return x;
	}
	
	public void setX(double value) {
		x = value;
	}
	
	public double getY() {
		return y;
	}
	
	public void setY(double value) {
		y = value;
	}
	
	public double getTwist() {
		return r;
	}
	
	public void setTwist(double value) {
		r = value;
	}
	
	public double getXboxAxis(int axis) {
		return auxAxes[axis - 1];
	}
	
	public void setXboxAxis(int axis, double value) {
		auxAxes[axis - 1] = value;
	}
	
	public boolean getButton(int button) {
		return buttons[button - 1];
	}
	
	public void setButton(int button, boolean value) {
		buttons[button - 1] = value;
	}
	
	public double getVoltage() {
		return voltage;
	}
	
	public void setVoltage(double value) {
		voltage = value;
	}
}
