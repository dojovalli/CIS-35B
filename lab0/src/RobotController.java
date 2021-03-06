
	

public class RobotController {

	// MEMBERS
	private Robot mKarel;
	private Power mPower;
	private Speed mSpeed;
	private Rotation mRotation;
	private RobotCommand mCommand;
	
	// Buttons
	/*
	private JToggleButton mPowerSwitch;
	private JToggleButton mSpeedSwitch;
	private Button mR90Button;
	private Button mR180Button;
	private Button mR270Button;
	private Button mR360Button;
	*/

	// CONSTRUCTORS
	public RobotController() {
		System.out.println("Creating RobotController object.");
		
		// Create Robot
		mKarel = new Robot();
		
		// Instantiate Default Object
		mPower = new Power(Robot.on);
		mSpeed = new Speed(Robot.slow);
		mRotation = new Rotation(Robot.rotate90, Robot.clockwise);
		
	}
	
	public void setupControlls() {
		// Get a reference to the Button
		//mPowerSwitch = // TODO - Connect the Swing JToggleButton
		//mSpeedSwitch = // TODO - Connect the Swing JToggleButton
		//mDirectionSwitch = // TODO - Connect the Swing JToggleButton
		//mR90Button = //TODO - Connect the Swing Button
		//mR180Button = //TODO - Connect the Swing Button
		//mR270Button = //TODO - Connect the Swing Button
		//mR360Button = //TODO - Connect the Swing Button
	}
	
	public void executeCommand(RobotCommand command) {
		// Send the Command to the actual Robot
		command.set(mKarel);
				
		// Execute
		mKarel.Execute();
	}
	
	public RobotCommand buildCommand(int degreeOpCode) {
		// Setup Command
		//mPower = getPowerState();
		//mSpeed = getSpeedSetting();
		mRotation.setRotationDegrees(degreeOpCode);
		//mRotation.setRotationDirection(mDirection.getOpCode());
		RobotCommand command = new RobotCommand(mPower, mSpeed, mRotation);
		
		return command;
	}
	
	public void rotate90() {
		// Build command for 90 degrees
		// Send & Execute the RobotCommand
		executeCommand(buildCommand(Robot.rotate90));
	}
	
	public void rotate180() {
		// Build command for 90 degrees
		// Send & Execute the RobotCommand
		executeCommand(buildCommand(Robot.rotate180));
	}
	
	public void rotate270() {
		// Build command for 90 degrees
		// Send & Execute the RobotCommand
		executeCommand(buildCommand(Robot.rotate270));
	}
	
	public void rotate360() {
		// Build command for 90 degrees
		// Send & Execute the RobotCommand
		executeCommand(buildCommand(Robot.rotate360));
	}

	
	// TESTING METHODS
	public void testRobotConvenienceMethods() {
		// Log the process
		System.out.println("Robot Convenience Methods:");
		
		// Execute Clockwise
		System.out.println("Clockwise Rotation");
		rotate90();
		rotate180();
		rotate270();
		rotate360();
		
		// Change the direction to Counter
		mRotation.setRotationDirection(Robot.counter);
		
		// Execute Counter-Clockwise
		System.out.println("Counter Rotation");
		rotate90();
		rotate180();
		rotate270();
		rotate360();
	}
	
	
	public static void testAllRobotCommands() {
		// Create a Robot to execute command
		Robot karel = new Robot();
		
		// Setup Power, Speed, Rotation
		Power io = new Power(Robot.on);
		Speed spd = new Speed(Robot.slow);
		Rotation rotate = new Rotation(Robot.rotate90, Robot.clockwise);
		
		// Create Look Arrays
		int[] speeds = { Robot.slow, Robot.fast };
		int[] directions = { Robot.clockwise, Robot.counter };
		int[] degrees = { Robot.rotate90, Robot.rotate180, Robot.rotate270, Robot.rotate360 };
		
		
		// Automate the production of a command by running loop counter through switch statments
		for (int i = 0; i < 8; i++) {
			
			// Log The "Conceptual" pass
			if (i == 0) System.out.println("Testing All Clockwise Commands: ");
			if (i == 4) System.out.println("Testing All Counter-Clockwise Rotation: ");
			
			// Set the Speed based on the pass
			spd.setSpeed(speeds[ (i / 4) ]);
			rotate.setRotationDirection(directions[ (i / 4) ]);
			rotate.setRotationDegrees(degrees[ (i % 4) ]);
			
			// Build a RobotCommand for this pass
			RobotCommand command = new RobotCommand(io, spd, rotate);
			
			// Set the Robot with the command
			command.set(karel);
				
			// Rotate the command for this pass
			karel.Execute();
			
			// Create space between "Conceptual" passes
			if (i == 3) System.out.println();
		}
	}
	
	public void testAllConvenienceMethods() {
		
		for (int i = 0; i < 2; i++) {
		
			// Log which direction to test rotation
			if (i == 0) System.out.println("Testing All Clockwise Commands: ");
			if (i == 1) {
				System.out.println("Testing All Counter-Clockwise Rotation: ");
				// Change the direction of the rotation to Robot.counter
				mRotation.setRotationDirection(Robot.counter);
			}
			rotate90();
			rotate180();
			rotate270();
			rotate360();
		
			// Add a blank new line for formating output
			System.out.println();
		}
	}
	
	

}
