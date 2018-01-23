package team3647subsystems;

import com.ctre.CANTalon;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import team3647ConstantsAndFunctions.Constants;

public class Drivetrain 
{
	public static WPI_TalonSRX leftSRX = new WPI_TalonSRX(Constants.leftMaster);
	public static WPI_TalonSRX rightSRX = new WPI_TalonSRX(Constants.rightMaster);
	
	public static VictorSPX leftSPX1 = new VictorSPX(Constants.leftSlave1);
	public static VictorSPX rightSPX1 = new VictorSPX(Constants.rightSlave1);
	public static VictorSPX leftSPX2 = new VictorSPX(Constants.leftSlave2);
	public static VictorSPX rightSPX2 = new VictorSPX(Constants.rightSlave2);
	
	public static DifferentialDrive drive = new DifferentialDrive(leftSRX, rightSRX);
	
	public static void drivetrainInitialization()
	{
		setLeftMotorSpeed(0);
		setRightMotorSpeed(0);
		leftSPX1.follow(leftSRX);
		leftSPX2.follow(leftSRX);    
		rightSPX1.follow(rightSRX);
		rightSPX2.follow(rightSRX);
	}
	
	static double avg;

	public static void setLeftMotorSpeed(double speed, ControlMode cm)
	{
		leftSRX.set(cm, speed);
	}
	
	public static void setLeftMotorSpeed(double speed)
	{
		leftSRX.set(speed);
	}
	
	public static void setRightMotorSpeed(double speed, ControlMode cm)
	{
		rightSRX.set(cm, speed);
	}
	
	public static void setRightMotorSpeed(double speed)
	{
		rightSRX.set(speed);
	}
	
	
	public static void testDrive(double fYValue, double sYValue)
	{
		setLeftMotorSpeed(fYValue);
		setRightMotorSpeed(-sYValue);
	}
	
	public static void testPID(double yValue, double xValue)
	{
		drive.arcadeDrive(yValue, xValue);
	}
	
	public static void tankDrive(double lYValue, double rYValue)
	{
		drive.tankDrive(lYValue, rYValue, false);
		System.out.println("lvalue: " + lYValue + "; rvalue: " + rYValue); 
		System.out.println("Left speed: " + leftSRX.get() + "; Right speed: " + rightSRX.get()); 
	}
	
	public static boolean reachedDistance(double leftEnc, double rightEnc, double distance)
	{
		avg = Math.abs(leftEnc) + Math.abs(rightEnc);
		avg/=2;
		if(avg<distance)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static void driveForward(double leftEnc, double rightEnc, double speed)
	{
		if(Math.abs(leftEnc - rightEnc) < 20)
		{
			drive.tankDrive(speed, speed, false);
		}
		else if(Math.abs(leftEnc - rightEnc) < 30)
		{
			if(leftEnc > rightEnc)
		 	{
				drive.tankDrive(speed - .075, speed, false);
		 	}
			else
		 	{
		 		drive.tankDrive(speed, speed - .075, false);
		 	}
		 }
		 else if(Math.abs(leftEnc - rightEnc) < 40)
		 {
			 if(leftEnc > rightEnc)
			 {
				 drive.tankDrive(speed - .15, speed, false);
			 }
			 else
			 {
				 drive.tankDrive(speed, speed - .15, false);
			 }
		 }
		 else if(Math.abs(leftEnc - rightEnc) < 60)
		 {
			 if(leftEnc > rightEnc)
			 {
				 drive.tankDrive(speed - .225, speed, false);
			 }
			 else
			 {
				 drive.tankDrive(speed, speed - .225, false);
			 }
		 }
		 else
		 {
			 if(leftEnc > rightEnc)
			 {
				 drive.tankDrive(speed - .3, speed, false);
			 }
			 else
			 {
			 	drive.tankDrive(speed, speed - .3, false);
			 }
		 }
	}
	
	public static void driveBackward(double leftEnc, double rightEnc, double speed)
	{
		if(Math.abs(leftEnc - rightEnc) < 20)
		{
			drive.tankDrive(speed, speed, false);
		}
		else if(Math.abs(leftEnc - rightEnc) < 30)
		{
			if(leftEnc > rightEnc)
		 	{
				drive.tankDrive(speed + .075, speed, false);
		 	}
			else
		 	{
		 		drive.tankDrive(speed, speed + .075, false);
		 	}
		 }
		 else if(Math.abs(leftEnc - rightEnc) < 40)
		 {
			 if(leftEnc > rightEnc)
			 {
				 drive.tankDrive(speed + .15, speed, false);
			 }
			 else
			 {
				 drive.tankDrive(speed, speed + .15, false);
			 }
		 }
		 else if(Math.abs(leftEnc - rightEnc) < 60)
		 {
			 if(leftEnc > rightEnc)
			 {
				 drive.tankDrive(speed +.225, speed, false);
			 }
			 else
			 {
				 drive.tankDrive(speed, speed + .225, false);
			 }
		 }
		 else
		 {
			 if(leftEnc > rightEnc)
			 {
				 drive.tankDrive(speed + .3, speed, false);
			 }
			 else
			 {
			 	drive.tankDrive(speed, speed + .3, false);
			 }
		 }
	}
	
	public static boolean reachedTurnDistance(double sum, double requiredLeftDist, double requiredRightDist)
	{
		if(sum < requiredLeftDist + requiredRightDist)
		{
			return false;
		}
		else
		{
			return true;
		}
	}
	
	public static void goStraightLeft(double currentRatio, boolean withinRange, double sum, double requiredLeftDist, double requiredRightDist, double leftSpeed, double rightSpeed, double adjustment)
	{
		if(withinRange || sum < 50)
		{
			drive.tankDrive(leftSpeed,rightSpeed, false);
		}
		else
		{
			if(currentRatio > 1.1 && currentRatio < 1.18)
			{
//				setLeftMotorSpeed(leftSpeed + adjustment);
//				setRightMotorSpeed(-(rightSpeed - adjustment));
				
				drive.tankDrive(leftSpeed + adjustment, (rightSpeed - adjustment), false);
			}
			else if(currentRatio > 1.18 && currentRatio < 1.25)
			{
//				setLeftMotorSpeed(leftSpeed + (2*adjustment));
//				setRightMotorSpeed(-(rightSpeed - (2*adjustment)));
				
				drive.tankDrive(leftSpeed + (2*adjustment),(rightSpeed - (2*adjustment)), false);
			}
			else if(currentRatio > 1.25)
			{
//				setLeftMotorSpeed(leftSpeed + (3*adjustment));
//				setRightMotorSpeed(-(rightSpeed - (3*adjustment)));
				
				drive.tankDrive(leftSpeed + (3*adjustment), (rightSpeed - (3*adjustment)), false);
			}
			else if(currentRatio < .9 && currentRatio > .82)
			{
//				setLeftMotorSpeed(leftSpeed - adjustment);
//				setRightMotorSpeed(-(rightSpeed + adjustment));
				
				drive.tankDrive(leftSpeed - adjustment, (rightSpeed + adjustment), false);
			}
			else if(currentRatio < .82 && currentRatio > .75)
			{
//				setLeftMotorSpeed(leftSpeed - (2*adjustment));
//				setRightMotorSpeed(-(rightSpeed + (2*adjustment)));
				
				drive.tankDrive(leftSpeed - (2*adjustment), (rightSpeed + (2*adjustment)), false);
			}
			else
			{
//				setLeftMotorSpeed(leftSpeed - (3*adjustment));
//				setRightMotorSpeed(-(rightSpeed + (3*adjustment)));
				
				drive.tankDrive(leftSpeed - (3*adjustment), (rightSpeed + (3*adjustment)), false);
			}
		}
	}
	
	public static void goStraightRight(double currentRatio, boolean withinRange, double sum, double requiredLeftDist, double requiredRightDist, double leftSpeed, double rightSpeed, double adjustment)
	{
		if(withinRange || sum < 50)
		{
//			setLeftMotorSpeed(leftSpeed);
//			setRightMotorSpeed(-rightSpeed);
			
			drive.tankDrive(leftSpeed,rightSpeed, false);
		}
		else
		{
			if(currentRatio > 1.1 && currentRatio < 1.18)
			{
//				setLeftMotorSpeed(leftSpeed + adjustment);
//				setRightMotorSpeed(-(rightSpeed - adjustment));
				
				drive.tankDrive(leftSpeed - adjustment,(rightSpeed + adjustment), false);
			}
			else if(currentRatio > 1.18 && currentRatio < 1.25)
			{
//				setLeftMotorSpeed(leftSpeed + (2*adjustment));
//				setRightMotorSpeed(-(rightSpeed - (2*adjustment)));
				
				drive.tankDrive(leftSpeed - (2*adjustment),(rightSpeed + (2*adjustment)), false);
			}
			else if(currentRatio > 1.25)
			{
//				setLeftMotorSpeed(leftSpeed + (3*adjustment));
//				setRightMotorSpeed(-(rightSpeed - (3*adjustment)));
				
				drive.tankDrive(leftSpeed - (3*adjustment),(rightSpeed + (3*adjustment)), false);
			}
			else if(currentRatio < .9 && currentRatio > .82)
			{
//				setLeftMotorSpeed(leftSpeed - adjustment);
//				setRightMotorSpeed(-(rightSpeed + adjustment));
				
				drive.tankDrive(leftSpeed + adjustment,(rightSpeed - adjustment));
			}
			else if(currentRatio < .82 && currentRatio > .75)
			{
//				setLeftMotorSpeed(leftSpeed - (2*adjustment));
//				setRightMotorSpeed(-(rightSpeed + (2*adjustment)));
				
				drive.tankDrive(leftSpeed + (2*adjustment),(rightSpeed - (2*adjustment)));
			}
			else
			{
//				setLeftMotorSpeed(leftSpeed - (3*adjustment));
//				setRightMotorSpeed(-(rightSpeed + (3*adjustment)));
				
				drive.tankDrive(leftSpeed + (3*adjustment),(rightSpeed - (3*adjustment)));
			}
		}
	}
	
	public static void goBackLeft(double currentRatio, boolean withinRange, double sum, double requiredLeftDist, double requiredRightDist, double leftSpeed, double rightSpeed, double adjustment)
	{
		if(withinRange || sum < 50)
		{
//			setLeftMotorSpeed(leftSpeed);
//			setRightMotorSpeed(-rightSpeed);
			
			drive.tankDrive(leftSpeed,rightSpeed);
		}
		else
		{
			if(currentRatio > 1.1 && currentRatio < 1.18)
			{
//				setLeftMotorSpeed(leftSpeed + adjustment);
//				setRightMotorSpeed(-(rightSpeed - adjustment));
				
				drive.tankDrive(leftSpeed - adjustment,-(rightSpeed + adjustment));
			}
			else if(currentRatio > 1.18 && currentRatio < 1.25)
			{
//				setLeftMotorSpeed(leftSpeed + (2*adjustment));
//				setRightMotorSpeed(-(rightSpeed - (2*adjustment)));
				
				drive.tankDrive(leftSpeed - (2*adjustment),-(rightSpeed + (2*adjustment)));
			}
			else if(currentRatio > 1.25)
			{
//				setLeftMotorSpeed(leftSpeed + (3*adjustment));
//				setRightMotorSpeed(-(rightSpeed - (3*adjustment)));
				
				drive.tankDrive(leftSpeed - (3*adjustment),-(rightSpeed + (3*adjustment)));
			}
			else if(currentRatio < .9 && currentRatio > .82)
			{
//				setLeftMotorSpeed(leftSpeed - adjustment);
//				setRightMotorSpeed(-(rightSpeed + adjustment));
				
				drive.tankDrive(leftSpeed + adjustment,-(rightSpeed - adjustment));
			}
			else if(currentRatio < .82 && currentRatio > .75)
			{
//				setLeftMotorSpeed(leftSpeed - (2*adjustment));
//				setRightMotorSpeed(-(rightSpeed + (2*adjustment)));
				
				drive.tankDrive(leftSpeed + (2*adjustment),-(rightSpeed - (2*adjustment)));
			}
			else
			{
//				setLeftMotorSpeed(leftSpeed - (3*adjustment));
//				setRightMotorSpeed(-(rightSpeed + (3*adjustment)));
				
				drive.tankDrive(leftSpeed + (3*adjustment),-(rightSpeed - (3*adjustment)));
			}
		}
	}
	
	public static void goBackRight(double currentRatio, boolean withinRange, double sum, double requiredLeftDist, double requiredRightDist, double leftSpeed, double rightSpeed, double adjustment)
	{
		if(withinRange || sum < 50)
		{
//			setLeftMotorSpeed(leftSpeed);
//			setRightMotorSpeed(-rightSpeed);
			
			drive.tankDrive(leftSpeed,rightSpeed);
		}
		else
		{
			if(currentRatio > 1.1 && currentRatio < 1.18)
			{
//				setLeftMotorSpeed(leftSpeed + adjustment);
//				setRightMotorSpeed(-(rightSpeed - adjustment));
				
				drive.tankDrive(leftSpeed - adjustment,-(rightSpeed + adjustment));
			}
			else if(currentRatio > 1.18 && currentRatio < 1.25)
			{
//				setLeftMotorSpeed(leftSpeed + (2*adjustment));
//				setRightMotorSpeed(-(rightSpeed - (2*adjustment)));
				
				drive.tankDrive(leftSpeed - (2*adjustment),-(rightSpeed + (2*adjustment)));
			}
			else if(currentRatio > 1.25)
			{
//				setLeftMotorSpeed(leftSpeed + (3*adjustment));
//				setRightMotorSpeed(-(rightSpeed - (3*adjustment)));
				
				drive.tankDrive(leftSpeed - (3*adjustment),-(rightSpeed + (3*adjustment)));
			}
			else if(currentRatio < .9 && currentRatio > .82)
			{
//				setLeftMotorSpeed(leftSpeed - adjustment);
//				setRightMotorSpeed(-(rightSpeed + adjustment));
				
				drive.tankDrive(leftSpeed + adjustment,-(rightSpeed - adjustment));
			}
			else if(currentRatio < .82 && currentRatio > .75)
			{
//				setLeftMotorSpeed(leftSpeed - (2*adjustment));
//				setRightMotorSpeed(-(rightSpeed + (2*adjustment)));
				
				drive.tankDrive(leftSpeed + (2*adjustment),-(rightSpeed - (2*adjustment)));
			}
			else
			{
//				setLeftMotorSpeed(leftSpeed - (3*adjustment));
//				setRightMotorSpeed(-(rightSpeed + (3*adjustment)));
				
				drive.tankDrive(leftSpeed + (3*adjustment),-(rightSpeed - (3*adjustment)));
			}
		}
	}
}
