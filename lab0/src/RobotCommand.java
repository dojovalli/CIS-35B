
public class RobotCommand {

	// MEMBERS
	Power mPower;
	Speed mSpeed;
	Rotation mRotation;
	
	// CONSTRUCTOR
	public RobotCommand(Power p, Speed s, Rotation r) {
		mPower = p;
		mSpeed = s;
		mRotation = r;
	}
	
	// CONVENIENCE METHODS
	public void set(Robot r) {
		r.Set(mPower.getPower());
		r.Set(mSpeed.getSpeed());
		r.Set(mRotation.getRotationDegrees());
		r.Set(mRotation.getRotationDirection());
	}

	// GETTERS & SETTERS
	public Power getPower() {
		return mPower;
	}

	public void setPower(Power power) {
		mPower = power;
	}

	public Speed getSpeed() {
		return mSpeed;
	}

	public void setSpeed(Speed speed) {
		mSpeed = speed;
	}

	public Rotation getRotation() {
		return mRotation;
	}

	public void setRotation(Rotation rotation) {
		mRotation = rotation;
	}
	
	

}
