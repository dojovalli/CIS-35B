
public class Main {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// Use static method to test the commands
		RobotController.testAllRobotCommands();
		
		// Add newline buffer for output formatting
		System.out.println();
		
		// Create a new RobotController
		RobotController view = new RobotController();
		
		// On Load
		//setupRobot();
		
		// Use class members to test convenience methods
		view.testAllConvenienceMethods();
	}

}
