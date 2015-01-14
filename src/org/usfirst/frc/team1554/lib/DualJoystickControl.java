package org.usfirst.frc.team1554.lib;

import org.usfirst.frc.team1554.lib.collect.IntMap;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.Joystick;

public class DualJoystickControl implements JoystickControl {

	private Joystick leftStick, rightStick;
	private final IntMap<Runnable> leftActions = new IntMap<Runnable>(8);
	private final IntMap<Runnable> rightActions = new IntMap<Runnable>(8);

	public DualJoystickControl(Joystick left, Joystick right) {
		this.leftStick = left;
		this.rightStick = right;
	}

	public DualJoystickControl(int leftPort, int rightPort) {
		this(new Joystick(leftPort), new Joystick(rightPort));
	}

	@Override
	public double getX() {
		return this.rightStick.getX();
	}

	@Override
	public double getY() {
		return this.rightStick.getY();
	}

	@Override
	public double getTwist() {
		return this.leftStick.getTwist();
	}

	@Override
	public double getMagnitude() {
		// final double x = getX();
		// final double y = getY();
		//
		// return Math.sqrt((x * x) + (y * y));
		return this.rightStick.getMagnitude();
	}

	@Override
	public double getDirectionRadians() {
		return Math.atan2(getX(), -getY());
	}

	@Override
	public double getDirectionDegrees() {
		return Math.toDegrees(getDirectionRadians());
	}

	@Override
	public Joystick leftJoystick() {
		return this.leftStick;
	}

	@Override
	public Joystick rightJoystick() {
		return this.rightStick;
	}

	@Override
	public void swapJoysticks() {
		final Joystick temp = this.leftStick;
		this.leftStick = this.rightStick;
		this.rightStick = temp;
	}

	@Override
	public void putButtonAction(int bId, Runnable action, Hand side) {
		final IntMap<Runnable> actions = side == Hand.kLeft ? this.leftActions : this.rightActions;
		final Joystick stick = side == Hand.kLeft ? this.leftStick : this.rightStick;

		if (bId > stick.getButtonCount()) throw new IllegalArgumentException("Button ID can't be greater than the joystick button count!: " + bId + " -> " + stick.getButtonCount() + " max");

		actions.put(bId, action);
	}

	@Override
	public Runnable removeButtonAction(int bId, Hand side) {
		final IntMap<Runnable> actions = side == Hand.kLeft ? this.leftActions : this.rightActions;

		return actions.remove(bId);
	}

}