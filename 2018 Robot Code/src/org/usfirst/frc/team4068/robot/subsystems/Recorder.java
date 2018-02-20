package org.usfirst.frc.team4068.robot.subsystems;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;

import edu.wpi.first.wpilibj.Joystick;

public class Recorder {
	private ByteArrayOutputStream inputRecorder = new ByteArrayOutputStream();
	
	public int getFrameSize() {
		return 	+8
				+8
				+8
				+8*5
				+1
				+8;
	}
	
	public void recordInput(InputState input) {
		try {
			inputRecorder.write(serialize(input.getX()));
			inputRecorder.write(serialize(input.getY()));
			inputRecorder.write(serialize(input.getTwist()));
			for (int i = 1; i <= 5; ++i) {
				inputRecorder.write(serialize(input.getXboxAxis(i)));
			}
			byte buttons = 0;
			for (int i = 1; i <= 8; ++i) {
				buttons <<= 1;
				buttons |= (input.getButton(i)) ? 1 : 0;
			}
			inputRecorder.write(buttons);
			inputRecorder.write(serialize(input.getVoltage()));
		}
		catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public void recordInput(Joystick driveStick, Joystick auxStick, double voltage) {
		recordInput(new InputState(driveStick, auxStick, voltage));
	}
	
	public InputState getNextInput(ByteArrayInputStream stream) {
		try {
			InputState state = new InputState();
			state.setX(getNextDouble(stream));
			state.setY(getNextDouble(stream));
			state.setTwist(getNextDouble(stream));
			for (int i = 1; i <= 5; ++i) {
				state.setXboxAxis(i,  getNextDouble(stream));
			}
			byte buttons = getNextByte(stream);
			for (int i = 8; i >= 1; --i) {
				state.setButton(i, (buttons & 0x1) != 0);
				buttons >>= 1;
			}
			state.setVoltage(getNextDouble(stream));
			return state;
		}
		catch (Exception exception) {
			exception.printStackTrace();
			return null;
		}
	}
	
	public byte[] getBytes() {
		return inputRecorder.toByteArray();
	}
	
	public int getSize() {
		return inputRecorder.size();
	}
	
	private static byte[] serialize(double x) {
		byte[] array = new byte[8];
		ByteBuffer.wrap(array).putDouble(x);
		return array;
	}
	
	private static double deserialize(byte[] array) {
		return ByteBuffer.wrap(array).getDouble();
	}
	
	private static double getNextDouble(ByteArrayInputStream stream) throws IOException {
		byte[] array = new byte[8];
		stream.read(array);
		return deserialize(array);
	}
	
	private static byte getNextByte(ByteArrayInputStream stream) throws IOException {
		byte[] array = new byte[1];
		stream.read(array);
		return array[0];
	}
}