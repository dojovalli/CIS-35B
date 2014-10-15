
public class Speed {

	// MEMBERS
	int mSpeed;
	
	// CONSTRUCTORS
	public Speed(int robotSpeedConstant) {
		mSpeed = robotSpeedConstant;
	}
	
	// CONVENIENCE METHODS
	public int getSpeed() {
		return mSpeed;
	}
	
	public void setSpeed(int robotOpCode) {
		mSpeed = robotOpCode;
	}
	
	public void setSpeedSlow() {
		mSpeed = Robot.slow;
	}
	
	public void setSpeedFast() {
		mSpeed = Robot.fast;
	}

}
