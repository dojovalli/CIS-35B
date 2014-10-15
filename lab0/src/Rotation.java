
public class Rotation {

	// MEMBERS
	int mRotationDegrees;
	int mRotationDirection;
	
	// CONSTRUCTORS
	public Rotation(int degrees, int direction) {
		mRotationDegrees = degrees;
		if (direction == Robot.clockwise || direction == Robot.counter) {
			mRotationDirection = direction;
		} else {
			System.out.println("Direction input was not valid!"
								+ "The direction has been set to Robot.clockwise by default.");
			mRotationDirection = Robot.clockwise;
		}
	}
	
	// GETTERS & SETTERS
	public int getRotationDegrees() {
		return mRotationDegrees;
	}
	
	public void setRotationDegrees(int robotOpCode) {
		mRotationDegrees = robotOpCode;
	}
	
	public int getRotationDirection() {
		return mRotationDirection;
	}
	
	public void setRotationDirection(int robotOpCode) {
		mRotationDirection = robotOpCode;
	}
	
	
	
	// Convenience Methods
	public void setRotateClockwise(int degrees) {
		mRotationDegrees = degrees;
		mRotationDirection = Robot.clockwise;
	}
	
	public void setRotateCounterClockwise(int degrees) {
		mRotationDegrees = degrees;
		mRotationDirection = Robot.counter;
	}


}
