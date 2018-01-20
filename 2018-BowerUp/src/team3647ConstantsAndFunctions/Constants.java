package team3647ConstantsAndFunctions;

public class Constants 
{
	//Motor Pins
	public final static int leftMotorPin = 0;
	public final static int rightMotorPin = 3;
	
	//Encoder Pins
	public final static int leftEncoderPinSourceA = 0;
	public final static int leftEncoderPinSourceB = 1;
	public final static int rightEncoderPinSourceA = 4;
	public final static int rightEncoderPinSourceB = 5;
	
	//JoySticks
	public final static int mainControllerNumber = 0;
	
	//Turn Constant for the Drivetrain
	public static double turnConstant(double yValue)
	{
		double turnConstant;
		turnConstant = .75 -(.25*(Math.abs(yValue)));
		return turnConstant;
	}
	
	//Pins for Auto
	public final static int leftAutoPin = 6;
	public final static int middleAutoPin = 7;
	public final static int rightAutoPin = 8;
	
	//Auto numbers
	public final static double initialStraightLLSWSC = 772;
	public final static double smallTurnForSwitchSWSC = 386 * Math.PI;
	public final static double bigTurnForSwitchSWSC = 772 * Math.PI;
	public final static double distanceSwitchLLSWSC = 386;
	public final static double smallTurnFromSwitchSWSC = 964 * Math.PI;
	public final static double bigTurnFromSwitchSWSC = 1735.2 * Math.PI;
	public final static double backUpToPickUpCubeSWSC = 3214;
	
	public final static double moveOnce = 600;
	
	public final static double testStright = 7 * 100;
	public final static double testBigRight = 2.7889 * moveOnce;
	public final static double testSmallLeft = 1.4835 * moveOnce;

}
