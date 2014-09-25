
public class Power {

	// Members
	int mPowerSetting;
	
	// Constructor
	public Power(int robotPowerConstant) {
		mPowerSetting = robotPowerConstant;
	}
	
	// GETTERS & SETTERS
	public int getPower() {
		return mPowerSetting;
	}
	
	public void setPowerOn() {
		mPowerSetting = Robot.on;
	}
	
	public void setPowerOff() {
		mPowerSetting = 0;
	}
}
