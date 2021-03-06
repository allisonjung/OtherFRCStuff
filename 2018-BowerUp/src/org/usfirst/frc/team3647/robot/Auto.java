//package org.usfirst.frc.team3647.robot;
//
//import edu.wpi.first.wpilibj.Timer;
//import team3647ConstantsAndFunctions.Constants;
//import team3647ConstantsAndFunctions.Functions;
//import team3647elevator.Elevator;
//import team3647elevator.ElevatorLevel;
//import team3647subsystems.Drivetrain;
//import team3647subsystems.Encoders;
//
//public class Auto 
//{
//	/*
//	 * Measurements:
//	 * Sides:
//	 	* Switch: For X: Left of Robot Start to Front of the Robot at the End: 55.56 inches
//	 	* Switch: For Y: 168 inches
//	 	* Scale: For Y: 324 inches
//	 	* Scale: For X: 18 inches
//	 * Middle:
//	 	* Right Side Switch: For X: around 42 inches
//	 	* Right Side Switch: For Y: around 140 inches
//	 	* Left Side Switch: For X: around 66 inches
//	 	* Left Side Switch: For Y: around 140 inches
//	 */
//	
//	/*
//	 * 1. MSLSW
//	 * 2. MSRSW
//	 * 3. RSRSW2
//	 * 4. LSLSW2
//	 * 5. RSRSC2
//	 * 6. LSLSC2
//	 * 7. RSLSCF
//	 */
//	
//	static double aimedRatio, currentRatio;
//	static double sum, speed, lSpeed, rSpeed;
//	static int test = 0;
//	static boolean withinRange;
//	static double requiredLeftDist, requiredRightDist, requiredStraightDist = 0;
//	static int currentState;
//	
//	static double prevLeftEncoder, prevRightEncoder, avg;
//	static String gameData, auto;
//	
//	static double lSSpeed, rSSpeed, lAdjustment, rAdjustment, error, adjustment;
//	static boolean lError, rError;
//	static double []adjustmentValues = new double[2];
//	
//	public static void RSLSCF(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					Elevator.moveEleVader(-.2);
//				}
//				break;
//			case 1:
//				
//				break;
//		}
//	}
//	
//	public static void RSRSWF(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					Elevator.moveEleVader(-.2);
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.switchStraight;
//				avg = (lValue + rValue)/2;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = Functions.straightForSwitch(avg);
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 2;
//				}
//				break;
//			case 2:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.switchFirstBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = Functions.BigTurnSwitch(rValue);
//					lSpeed = rSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 3;
//				}
//				break;
//			case 3:
//				Drivetrain.stop();
//				Timer.delay(.3);
//				Encoders.resetEncoders();
//				currentState = 5;
//				break;
//			case 5:
//				if(ElevatorLevel.reachedSwitch())
//				{
//					Elevator.stopEleVader();
//					currentState = 6;
//				}
//				else
//				{
//					Elevator.moveEleVader(.4);
//				}
//				break;
//			case 6:
//				oof.shootCube();
//				Timer.delay(.5);
//				currentState = 7;
//				break;
//			case 7:
//				oof.stopIntake();
//				Elevator.stopEleVader();
//				Drivetrain.stop();
//				break;
////			case 4:
////				if(ElevatorLevel.reachedSwitch())
////				{
////					Elevator.stopEleVader();
////					currentState = 5;
////				}
////				else
////				{
////					Elevator.setElevatorButtons(false, false, true, false);
////					Elevator.runElevator();
////				}
////				break;
////			case 5:
////				if(!intakeWheels.hasCube())
////				{
////					Timer.delay(.35);
////					currentState = 6;
////				}
////				else
////				{
////					intakeWheels.shootCube();
////				}
////				break;
////			case 6:
////				intakeWheels.stopIntake();
////				Elevator.stopEleVader();
////				Drivetrain.stop();
////				break;
//		}
//	}
//	
//	public static void MSLSWF(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					Elevator.moveEleVader(-.2);
//				}
//				break;
//			case 1:
//				requiredRightDist = Constants.middleLSwitchBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.middleLeftCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = Functions.middleLBigTurn(rValue);
//					lSpeed = rSpeed/Constants.middleLeftCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.middleLSwitchBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.middleLeftCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed =  Functions.middleLBigTurn(lValue);
//					rSpeed = lSpeed/Constants.middleLeftCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					lValue += prevLeftEncoder;
//					rValue += prevRightEncoder;
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				avg = (lValue + rValue);
//				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.middleLSwitchStraightToSwitch))
//				{
//					speed = Functions.middleLStraighttoSwitch(avg);
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 4;
//				}
//				break;
//			case 4:
//				Drivetrain.stop();
//				Timer.delay(.3);
//				Encoders.resetEncoders();
//				currentState = 5;
//				break;
//			case 5:
//				if(ElevatorLevel.reachedSwitch())
//				{
//					Elevator.stopEleVader();
//					currentState = 6;
//				}
//				else
//				{
//					Elevator.moveEleVader(.4);
//				}
//				break;
//			case 6:
//				oof.shootCube();
//				Timer.delay(.5);
//				currentState = 7;
//				break;
//			case 7:
//				oof.stopIntake();
//				Elevator.stopEleVader();
//				Drivetrain.stop();
//				break;	
////			case 5:
////				if(ElevatorLevel.reachedSwitch())
////				{
////					Elevator.stopEleVader();
////					currentState = 6;
////				}
////				else
////				{
////					Elevator.setElevatorButtons(false, false, true, false);
////					Elevator.runElevator();
////				}
////				break;
////			case 6:
////				if(!intakeWheels.hasCube())
////				{
////					Timer.delay(.35);
////					currentState = 7;
////				}
////				else
////				{
////					intakeWheels.shootCube();
////				}
////				break;
////			case 7:
////				intakeWheels.stopIntake();
////				Elevator.stopEleVader();
////				Drivetrain.stop();
////				break;
//			
//		}
//	}
//	
//	public static void MSRSWF(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					Elevator.moveEleVader(-.2);
//				}
//				break;
//			case 1:
//				requiredLeftDist = Constants.middleRSwitchBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.middleRightCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue+lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = Functions.middleRBigTurn(lValue);
//					rSpeed = lSpeed/Constants.middleRightCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 2;
//				}
//				break;
//			case 2:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.middleRSwitchBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.middleRightCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue+lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = Functions.middleRBigTurn(rValue);
//					lSpeed = rSpeed/Constants.middleRightCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					lValue += prevLeftEncoder;
//					rValue += prevRightEncoder;
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				avg = (lValue + rValue)/2;
//				requiredStraightDist = Constants.middleRSwitchStraightToSwitch;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = Functions.middleRStraighttoSwitch(avg);
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 4;
//				}
//				break;
//			case 4:
//				Drivetrain.stop();
//				Timer.delay(.3);
//				Encoders.resetEncoders();
//				currentState = 5;
//				break;
//			case 5:
//				if(ElevatorLevel.reachedSwitch())
//				{
//					Elevator.stopEleVader();
//					currentState = 6;
//				}
//				else
//				{
//					Elevator.moveEleVader(.4);
//				}
//				break;
//			case 6:
//				oof.shootCube();
//				Timer.delay(.5);
//				currentState = 7;
//				break;
//			case 7:
//				oof.stopIntake();
//				Elevator.stopEleVader();
//				Drivetrain.stop();
//				break;
////			case 5:
////				if(ElevatorLevel.reachedSwitch())
////				{
////					Elevator.stopEleVader();
////					currentState = 6;
////				}
////				else
////				{
////					Elevator.setElevatorButtons(false, false, true, false);
////					Elevator.runElevator();
////				}
////				break;
////			case 6:
////				if(!intakeWheels.hasCube())
////				{
////					Timer.delay(.35);
////					currentState = 7;
////				}
////				else
////				{
////					intakeWheels.shootCube();
////				}
////				break;
////			case 7:
////				intakeWheels.stopIntake();
////				Elevator.stopEleVader();
////				Drivetrain.stop();
////				break;
//		}
//	}
//	
//	public static void testB(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0)
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					Elevator.moveEleVader(-.2);
//				}
//				break;
//			case 1:
//				if(!Drivetrain.reachedDistance(lValue, rValue, 9000))
//				{
//					Drivetrain.forw(lValue, rValue, .7,0);
//				}
//				else
//				{
//					currentState = 99;
//				}
//				break;
//			case 99:
//				if((lValue + rValue) < 100)
//				{
//					currentState = 2;
//					Encoders.testEncoders();
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void LSLSWF(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					Elevator.moveEleVader(-.2);
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.switchStraight;
//				avg = (lValue + rValue)/2;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = Functions.straightForSwitch(avg);
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.forw(lValue, rValue, speed, 0);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 99;
//				}
//				break;
//			case 99:
//				if((lValue + rValue) < 100)
//				{
//					currentState = 2;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 2:
////				lValue -= prevLeftEncoder;
////				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.switchFirstBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = Functions.BigTurnSwitch(lValue);
//					rSpeed = lSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 3;
//				}
//				break;
//			case 3:
//				Drivetrain.stop();
//				Timer.delay(.3);
//				//Encoders.resetEncoders();
//				currentState = 6;
//				break;
//			case 5:
//				if(ElevatorLevel.reachedSwitch())
//				{
//					Elevator.stopEleVader();
//					currentState = 6;
//				}
//				else
//				{
//					Elevator.moveEleVader(.4);
//				}
//				break;
//			case 6:
//				oof.shootCube();
//				Timer.delay(.5);
//				currentState = 7;
//				break;
//			case 7:
//				Encoders.testEncoders();
//				oof.stopIntake();
//				Elevator.stopEleVader();
//				Drivetrain.stop();
//				break;
////			case 4:
////				if(ElevatorLevel.reachedSwitch())
////				{
////					Elevator.stopEleVader();
////					currentState = 5;
////				}
////				else
////				{
////					Elevator.setElevatorButtons(false, false, true, false);
////					Elevator.runElevator();
////				}
////				break;
////			case 5:
////				if(!intakeWheels.hasCube())
////				{
////					Timer.delay(.35);
////					currentState = 6;
////				}
////				else
////				{
////					intakeWheels.shootCube();
////				}
////				break;
////			case 6:
////				intakeWheels.stopIntake();
////				Elevator.stopEleVader();
////				Drivetrain.stop();
////				break;
//		}
//	}
//	
//	public static void RSRSCF(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					Elevator.moveEleVader(-.2);
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.scaleStraight;
//				avg = (lValue + rValue)/2;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = Functions.straightForScale(avg);
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 2;
//				}
//				break;
//			case 2:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.scaleFirstBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.scaleFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = Functions.BigTurnScale(rValue);
//					lSpeed = rSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 3;
//				}
//				break;
//			case 3:
//				Drivetrain.stop();
//				Timer.delay(.3);
//				Encoders.resetEncoders();
//				currentState = 5;
//				break;
//			case 5:
//				if(ElevatorLevel.reachedSwitch())
//				{
//					Elevator.stopEleVader();
//					currentState = 6;
//				}
//				else
//				{
//					Elevator.moveEleVader(.4);
//				}
//				break;
//			case 6:
//				oof.shootCube();
//				Timer.delay(.5);
//				currentState = 7;
//				break;
//			case 7:
//				oof.stopIntake();
//				Elevator.stopEleVader();
//				Drivetrain.stop();
//				break;
////			case 4:
////				if(ElevatorLevel.reachedScale())
////				{
////					Elevator.stopEleVader();
////					currentState = 5;
////				}
////				else
////				{
////					Elevator.setElevatorButtons(false, false, false, true);
////					Elevator.runElevator();
////				}
////				break;
////			case 5:
////				if(!intakeWheels.hasCube())
////				{
////					Timer.delay(.35);
////					currentState = 6;
////				}
////				else
////				{
////					intakeWheels.shootCube();
////				}
////				break;
////			case 6:
////				intakeWheels.stopIntake();
////				Elevator.stopEleVader();
////				Drivetrain.stop();
////				break;
//		}
//	}
//	
//	public static void LSLSCF(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && ElevatorLevel.reachedStop())
//				{
//					Elevator.stopEleVader();
//					ElevatorLevel.resetElevatorEncoders();
//					currentState = 2;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//					Elevator.moveEleVader(-.2);
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.scaleStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					avg = (lValue + rValue)/2;
//					speed = Functions.straightForScale(avg);
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.arcadeDrive(lValue, rValue, speed, 0);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 2;
//				}
//				break;
//			case 2:
////				lValue -= prevLeftEncoder;
////				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.scaleFirstBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.scaleFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					avg = (lValue + rValue)/2;
//					lSpeed = Functions.BigTurnScale(avg);
//					rSpeed = lSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 3;
//				}
//				break;
//			case 3:
//				Drivetrain.stop();
//				Timer.delay(.3);
//				Encoders.resetEncoders();
//				currentState = 5;
//				break;
//			case 5:
//				if(ElevatorLevel.reachedSwitch())
//				{
//					Elevator.stopEleVader();
//					currentState = 6;
//				}
//				else
//				{
//					Elevator.moveEleVader(.4);
//				}
//				break;
//			case 6:
//				oof.shootCube();
//				Timer.delay(.5);
//				currentState = 7;
//				break;
//			case 7:
//				oof.stopIntake();
//				Elevator.stopEleVader();
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void MSLSW(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && Elevator.elevatorState == 1)
//				{
//					Elevator.setElevatorButtons(true, false, false, false);
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredRightDist = Constants.middleLSwitchBigTurn -2000;
//				requiredLeftDist = requiredRightDist/Constants.middleLeftCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = .63;
//					lSpeed = rSpeed/Constants.middleLeftCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredRightDist = Constants.middleLSwitchBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.middleLeftCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = .3;
//					lSpeed = rSpeed/Constants.middleLeftCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = 2000;
//				requiredRightDist = requiredLeftDist/Constants.middleLeftCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = .3;
//					rSpeed = lSpeed/Constants.middleLeftCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 4;
//				}
//				break;
//			case 4:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.middleLSwitchBigTurn - 2000;
//				requiredRightDist = requiredLeftDist/Constants.middleLeftCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = .6;
//					rSpeed = lSpeed/Constants.middleLeftCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 5;
//				}
//				break;
//			case 5:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.middleLSwitchBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.middleLeftCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = .4;
//					rSpeed = lSpeed/Constants.middleLeftCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					lValue += prevLeftEncoder;
//					rValue += prevRightEncoder;
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 6;
//				}
//				break;
//			case 6:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.middleLSwitchStraightToSwitch))
//				{
//					speed = .4;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 7;
//				}
//				break;
//			case 7:
//				Drivetrain.stop();
//				Elevator.setElevatorButtons(false, false, true, false);
//				intakeWheels.shootCube();
//				Timer.delay(.7);
//				currentState = 8;
//				break;
//			case 8:
//				Drivetrain.stop();
//				Elevator.setElevatorButtons(false, false, true, false);
//				intakeWheels.stopIntake();
//				break;
//			
//		}
//	}
//	
//	
//	
//	
//	
//	public static void crossBaseline(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.crossBaseline - 4000;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					Drivetrain.driveForward(lValue, rValue, .8, Constants.adjustmentConstant(.8));
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredStraightDist = Constants.crossBaseline;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					Drivetrain.driveForward(lValue, rValue, .3, Constants.adjustmentConstant(.3));
//				}
//				else
//				{
//					currentState = 3;
//				}
//				break;
//			case 3:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	
//	
////	public static void LSLSC1(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 0:
////				if(lValue == 0 && rValue ==0)
////				{
////					currentState = 1;
////				}
////				else
////				{
////					Encoders.resetEncoders();
////				}
////				break;
////			case 1:
////				requiredStraightDist = Constants.scaleStraight - 2800;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveForward(lValue, rValue, .6, Constants.adjustmentConstant(.6));
////				}
////				else
////				{
////					currentState = 2;
////				}
////				break;
////			case 2:
////				requiredStraightDist = Constants.scaleStraight;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveForward(lValue, rValue, .25, Constants.adjustmentConstant(.25));
////				}
////				else
////				{
////					prevLeftEncoder = lValue;
////					prevRightEncoder =  rValue;
////					currentState = 3;
////				}
////				break;
////			case 3:
////				lValue -= prevLeftEncoder;
////				rValue -= prevRightEncoder;
////				requiredLeftDist = Constants.scaleFirstBigTurn;
////				requiredRightDist = Constants.scaleFirstSmallTurn;
////				if(!Drivetrain.reachedTurnDistance(rValue + lValue, (requiredLeftDist -1400), (requiredRightDist - (1400/Constants.scaleFirstCurveSmallSpeedConstant))))
////				{
//////					lSpeed = Functions.scaleFirstCurveBigSpeed(lValue);
////					lSpeed = .6;
////					rSpeed = lSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
////					adjustment = Constants.adjustmentConstant(lSpeed);
////					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
////				}
////				else
////				{
////					currentState = 4;
////				}
////				break;
////			case 4:
////				lValue -= prevLeftEncoder;
////				rValue -= prevRightEncoder;
////				requiredLeftDist = Constants.scaleFirstBigTurn;
////				requiredRightDist = Constants.scaleFirstSmallTurn;
////				if(!Drivetrain.reachedTurnDistance(rValue + lValue, (requiredLeftDist), (requiredRightDist)))
////				{
//////					lSpeed = Functions.scaleFirstCurveBigSpeed(lValue);
////					lSpeed = .3;
////					rSpeed = lSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
////					adjustment = Constants.adjustmentConstant(lSpeed);
////					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
////				}
////				else
////				{
////					currentState = 5;
////				}
////				break;
////			case 5:
////				Drivetrain.stop();
////				break;	
////		}
////	}
//	
//	public static void MSRSW(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredLeftDist = Constants.middleRSwitchBigTurn - 2000;
//				requiredRightDist = requiredLeftDist/Constants.middleRightCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue+lValue, requiredLeftDist, requiredRightDist))
//				{
////					lSpeed = Functions.middleRightBigCurveSpeed(lValue);
//					lSpeed = .65;
//					rSpeed = lSpeed/Constants.middleRightCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredLeftDist = Constants.middleRSwitchBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.middleRightCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue+lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = .4;
//					rSpeed = lSpeed/Constants.middleRightCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				requiredRightDist = 2000;
//				requiredLeftDist = requiredRightDist/Constants.middleRightCurveSmallSpeedConstant;
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				if(!Drivetrain.reachedTurnDistance(rValue+lValue, requiredLeftDist, requiredRightDist))
//				{
////					rSpeed = Functions.middleRightBigCurveSpeed(lValue);
//					rSpeed = .4;
//					lSpeed = rSpeed/Constants.middleRightCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 4;
//				}
//				break;
//			case 4:
//				requiredRightDist = Constants.middleRSwitchBigTurn - 2000;
//				requiredLeftDist = requiredRightDist/Constants.middleRightCurveSmallSpeedConstant;
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				if(!Drivetrain.reachedTurnDistance(rValue+lValue, requiredLeftDist, requiredRightDist))
//				{
////					rSpeed = Functions.middleRightBigCurveSpeed(lValue);
//					rSpeed = .65;
//					lSpeed = rSpeed/Constants.middleRightCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 5;
//				}
//				break;
//			case 5:
//				requiredRightDist = Constants.middleRSwitchBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.middleRightCurveSmallSpeedConstant;
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				if(!Drivetrain.reachedTurnDistance(rValue+lValue, requiredLeftDist, requiredRightDist))
//				{
////					rSpeed = Functions.middleRightBigCurveSpeed(lValue);
//					rSpeed = .4;
//					lSpeed = rSpeed/Constants.middleRightCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					lValue += prevLeftEncoder;
//					rValue += prevRightEncoder;
//					prevLeftEncoder = lValue;
//					prevRightEncoder = rValue;
//					currentState = 6;
//				}
//				break;
//			case 6:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredStraightDist = 1000;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .4;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 7;
//				}
//				break;
//			case 7:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredStraightDist = Constants.middleRSwitchStraightToSwitch - 1400;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .6;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 8;
//				}
//				break;
//			case 8:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredStraightDist = Constants.middleRSwitchStraightToSwitch;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .25;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 9;
//				}
//				break;
//			case 9:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	
//	
////	public static void LSLSC(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 0:
////				if(lValue == 0 && rValue ==0)
////				{
////					currentState = 1;
////				}
////				else
////				{
////					Encoders.resetEncoders();
////				}
////				break;
////			case 1:
////				requiredStraightDist = Constants.scaleStraight;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					speed = Functions.scaleInitialStraight(lValue, rValue);
////					adjustment = Constants.adjustmentConstant(speed);
////					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
////				}
////				else
////				{
////					prevLeftEncoder = lValue;
////					prevRightEncoder =  rValue;
////					currentState = 2;
////				}
////				break;
////			case 2:
////				lValue -= prevLeftEncoder;
////				rValue -= prevRightEncoder;
////				requiredLeftDist = Constants.scaleFirstBigTurn;
////				requiredRightDist = Constants.scaleFirstSmallTurn;
////				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
////				{
////					lSpeed = Functions.scaleFirstCurveBigSpeed(lValue);
////					rSpeed = lSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
////					adjustment = Constants.adjustmentConstant(lSpeed);
////					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
////				}
////				else
////				{
////					currentState = 3;
////				}
////				break;
////			case 3:
////				Drivetrain.stop();
////				break;
////		}
////	}
//	
//	public static void LSLSC2(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.scaleStraight - 2500;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .75;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredStraightDist = Constants.scaleStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .4;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.scaleFirstBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.scaleFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = .55;
//					rSpeed = lSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 6;
//				}
//				break;
//			case 6:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	
//	
//	public static void RSRSC2(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.scaleStraight - 2500;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .75;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredStraightDist = Constants.scaleStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .4;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.scaleFirstBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.scaleFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = .55;
//					lSpeed = rSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 6;
//				}
//				break;
//			case 6:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	
//	
//	public static void RSRSCSC(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					Elevator.setElevatorButtons(true, false, false, false);
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.scaleStraight - 2500;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .75;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredStraightDist = Constants.scaleStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .4;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.scaleFirstBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.scaleFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = .55;
//					lSpeed = rSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 4;
//				}
//				break;
//			case 4:
//				Drivetrain.stop();
//				Timer.delay(.3);
//				Encoders.resetEncoders();
//				currentState = 5;
//				
//				Timer.delay(.8);
//				intakeWheels.shootCube();
//				Timer.delay(.4);
//				currentState = 5;
//				break;
//			case 5:
//				if(ElevatorLevel.reachedScale())
//				{
//					Elevator.stopEleVader();
//					currentState = 6;
//				}
//				else
//				{
//					Elevator.setElevatorButtons(false, false, false, true);
//					Elevator.runElevator();
//				}
//				break;
//			case 6:
//				if(!intakeWheels.hasCube())
//				{
//					Timer.delay(.35);
//					currentState = 7;
//				}
//				else
//				{
//					intakeWheels.shootCube();
//				}
//				break;
//			case 7:
//				lValue = Math.abs(lValue);
//				rValue = Math.abs(rValue);
//				requiredLeftDist = Constants.doubleLongTurnScaleBackUpToWall - 2000;
//				requiredRightDist = requiredLeftDist/Constants.doubleScaleWallandScaleCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = -.6;
//					rSpeed = lSpeed/Constants.doubleScaleWallandScaleCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goBackRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 8;
//				}
//				break;
//			case 8:
//				lValue = Math.abs(lValue);
//				rValue = Math.abs(rValue);
//				requiredLeftDist = Constants.doubleLongTurnScaleBackUpToWall;
//				requiredRightDist = requiredLeftDist/Constants.doubleScaleWallandScaleCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = -.35;
//					rSpeed = lSpeed/Constants.doubleScaleWallandScaleCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goBackRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 9;
//				}
//				break;
//		}
//	}
//	
//	
//	
////	public static void RSRSC(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 0:
////				if(lValue == 0 && rValue ==0)
////				{
////					currentState = 1;
////				}
////				else
////				{
////					Encoders.resetEncoders();
////				}
////				break;
////			case 1:
////				requiredStraightDist = Constants.scaleStraight;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					speed = Functions.scaleInitialStraight(lValue, rValue);
////					adjustment = Constants.adjustmentConstant(speed);
////					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
////				}
////				else
////				{
////					prevLeftEncoder = lValue;
////					prevRightEncoder =  rValue;
////					currentState = 2;
////				}
////				break;
////			case 2:
////				lValue -= prevLeftEncoder;
////				rValue -= prevRightEncoder;
////				requiredLeftDist = Constants.scaleFirstSmallTurn;
////				requiredRightDist = Constants.scaleFirstBigTurn;
////				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
////				{
////					rSpeed = Functions.scaleFirstCurveBigSpeed(rValue);
////					lSpeed = rSpeed/Constants.scaleFirstCurveSmallSpeedConstant;
////					adjustment = Constants.adjustmentConstant(rSpeed);
////					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
////				}
////				else
////				{
////					currentState = 3;
////				}
////				break;
////			case 3:
////				Drivetrain.stop();
////				break;
////		}
////	}
//	
////	public static void RSRSW(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 0:
////				if(lValue == 0 && rValue ==0)
////				{
////					currentState = 1;
////				}
////				else
////				{
////					Encoders.resetEncoders();
////				}
////				break;
////			case 1:
////				requiredStraightDist = Constants.switchStraight;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					speed = Functions.switchInitialStraight(lValue, rValue);
////					adjustment = Constants.adjustmentConstant(speed);
////					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
////				}
////				else
////				{
////					prevLeftEncoder = lValue;
////					prevRightEncoder =  rValue;
////					currentState = 2;
////				}
////				break;
////			case 2:
////				lValue -= prevLeftEncoder;
////				rValue -= prevRightEncoder;
////				requiredLeftDist = Constants.switchFirstSmallTurn;
////				requiredRightDist = Constants.switchFirstBigTurn;
////				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
////				{
////					rSpeed = Functions.switchFirstCurveBigSpeed(rValue);
////					lSpeed = rSpeed/Constants.switchFirstCurveSmallSpeedConstant;
////					adjustment = Constants.adjustmentConstant(rSpeed);
////					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
////				}
////				else
////				{
////					currentState = 3;
////				}
////				break;
////			case 3:
////				Drivetrain.stop();
////				break;
////		}
////	}
////	
//	public static void RSRSW2(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.switchStraight - 2000;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
////					speed = Functions.switchInitialStraight(lValue, rValue);
//					speed = .7;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredStraightDist = Constants.switchStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
////					speed = Functions.switchInitialStraight(lValue, rValue);
//					speed = .25;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = 1400;
//				requiredLeftDist = requiredRightDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					rSpeed = Functions.switchFirstCurveBigSpeed(rValue);
//					rSpeed = .48;
//					lSpeed = rSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 3;
//				}
//				break;
//			case 4:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.middleRSwitchBigTurn - 2000;
//				requiredLeftDist = requiredRightDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					rSpeed = Functions.switchFirstCurveBigSpeed(rValue);
//					rSpeed = .6;
//					lSpeed = rSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 5;
//				}
//				break;
//			case 5:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.middleRSwitchBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					rSpeed = .4;
//					lSpeed = rSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 6;
//				}
//				break;
//			case 6:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void RSRSW1(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.switchStraight -2800;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
////					speed = Functions.switchInitialStraight(lValue, rValue);
//					speed = .6;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredStraightDist = Constants.switchStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
////					speed = Functions.switchInitialStraight(lValue, rValue);
//					speed = .27;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.switchFirstBigTurn - 1400;
//				requiredLeftDist = requiredRightDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					rSpeed = Functions.switchFirstCurveBigSpeed(rValue);
//					rSpeed = .6;
//					lSpeed = rSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 4;
//				}
//				break;
//			case 4:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredRightDist = Constants.switchFirstBigTurn;
//				requiredLeftDist = requiredRightDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					rSpeed = Functions.switchFirstCurveBigSpeed(rValue);
//					rSpeed = .3;
//					lSpeed = rSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(rSpeed);
//					Drivetrain.goStraightLeft(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 5;
//				}
//				break;
//			case 5:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void LSLSW2(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.switchStraight - 2000;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
////					speed = Functions.switchInitialStraight(lValue, rValue);
//					speed = .7;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredStraightDist = Constants.switchStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = .3;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = 1000;
//				requiredRightDist = requiredLeftDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					lSpeed = Functions.switchFirstCurveBigSpeed(lValue);
//					lSpeed = .43;
//					rSpeed = lSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 4;
//				}
//				break;
//			case 4:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.switchFirstBigTurn - 2000;
//				requiredRightDist = requiredLeftDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					lSpeed = Functions.switchFirstCurveBigSpeed(lValue);
//					lSpeed = .7;
//					rSpeed = lSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 5;
//				}
//				break;
//			case 5:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.switchFirstBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					lSpeed = Functions.switchFirstCurveBigSpeed(lValue);
//					lSpeed = .3;
//					rSpeed = lSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 6;
//				}
//				break;
//			case 6:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	
//	public static void LSLSW(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue == 0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.switchStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
//					speed = Functions.switchInitialStraight(lValue, rValue);
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 2;
//				}
//				break;
//			case 2:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.switchFirstBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
//					lSpeed = Functions.switchFirstCurveBigSpeed(lValue);
//					rSpeed = lSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 3;
//				}
//				break;
//			case 3:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
//	public static void LSLSW1(double lValue, double rValue)
//	{
//		switch(currentState)
//		{
//			case 0:
//				if(lValue == 0 && rValue ==0 && Elevator.elevatorState == 1)
//				{
//					currentState = 1;
//				}
//				else
//				{
//					Encoders.resetEncoders();
//				}
//				break;
//			case 1:
//				requiredStraightDist = Constants.switchStraight - 2800;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
////					speed = Functions.switchInitialStraight(lValue, rValue);
//					speed = .6;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					currentState = 2;
//				}
//				break;
//			case 2:
//				requiredStraightDist = Constants.switchStraight;
//				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
//				{
////					speed = Functions.switchInitialStraight(lValue, rValue);
//					speed = .27;
//					adjustment = Constants.adjustmentConstant(speed);
//					Drivetrain.driveForward(lValue, rValue, speed, adjustment);
//				}
//				else
//				{
//					prevLeftEncoder = lValue;
//					prevRightEncoder =  rValue;
//					currentState = 3;
//				}
//				break;
//			case 3:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.switchFirstBigTurn - 1400;
//				requiredRightDist = requiredLeftDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					lSpeed = Functions.switchFirstCurveBigSpeed(lValue);
//					lSpeed = .6;
//					rSpeed = lSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 4;
//				}
//				break;
//			case 4:
//				lValue -= prevLeftEncoder;
//				rValue -= prevRightEncoder;
//				requiredLeftDist = Constants.switchFirstBigTurn;
//				requiredRightDist = requiredLeftDist/Constants.switchFirstCurveSmallSpeedConstant;
//				if(!Drivetrain.reachedTurnDistance(rValue + lValue, requiredLeftDist, requiredRightDist))
//				{
////					lSpeed = Functions.switchFirstCurveBigSpeed(lValue);
//					lSpeed = .3;
//					rSpeed = lSpeed/Constants.switchFirstCurveSmallSpeedConstant;
//					adjustment = Constants.adjustmentConstant(lSpeed);
//					Drivetrain.goStraightRight(lValue, rValue, requiredLeftDist, requiredRightDist, lSpeed, rSpeed, adjustment);
//				}
//				else
//				{
//					currentState = 5;
//				}
//				break;
//			case 5:
//				Drivetrain.stop();
//				break;
//		}
//	}
//	
////	public static void semicircle(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 1:
////				requiredRightDist = (Constants.testSmallUTurn);
////				requiredLeftDist = (Constants.testBigUTurn);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				lValue = Math.abs(lValue);
////				rValue = Math.abs(rValue);
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist+845, requiredRightDist-200))
////				{
////					Drivetrain.goBackRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, -.92, -.3, .04);
////				}
////				else
////				{
////					prevLeftEncoder = lValue;
////					prevRightEncoder = rValue;
////					currentState = 2;
////				}
////				break;
////			case 2:
////				lValue = -lValue - prevLeftEncoder;
////				rValue = -rValue - prevRightEncoder;
////				if(!Drivetrain.reachedDistance(lValue, rValue, 9000))
////				{
////					Drivetrain.driveBackward(lValue, rValue, -.4, .04);
////				}
////				else
////				{
////					Drivetrain.stop();
////				}
////					
////				
////				break;
////		}
////	}
////	
////	
////	public static void MSRRSW2(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 1:
////				requiredRightDist = (Constants.MSRRSWfirstsmallTurn - 500);
////				requiredLeftDist = (Constants.MSRRSWfirstbigTurn - 1766.61);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goStraightRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .707, .2, .035);
////				}
////				else
////				{
////					currentState = 2;
////				}
////				break;
////			case 2:
////				requiredRightDist = (Constants.MSRRSWfirstsmallTurn);
////				requiredLeftDist = (Constants.MSRRSWfirstbigTurn);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goStraightRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .53, .15, .028);
////				}
////				else
////				{
////					prevLeftEncoder = lValue;
////					prevRightEncoder = rValue;
////					currentState = 3;
////				}
////				break;
////			case 3:
////				requiredRightDist = Constants.MSRRSWsecondbigTurn;
////				requiredLeftDist = Constants.MSRRSWsecondsmallTurn;
////				aimedRatio = ((requiredRightDist)/(requiredLeftDist));
////				lValue = lValue - prevLeftEncoder;
////				rValue = rValue - prevRightEncoder;
////				currentRatio = (((rValue)/(lValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist-405))
////				{
////					if(rValue < 1766.61 && lValue < 500)
////					{
////						Drivetrain.goStraightLeft(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .15, .53, .028);
////					}
////					else
////					{
////						Drivetrain.goStraightLeft(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .2, .65, .035);
////					}
////					
////				}
////				else
////				{
////					currentState = 5;
////				}
////				break;
////			case 4:
////				requiredRightDist = Constants.MSRRSWsecondbigTurn;
////				requiredLeftDist = Constants.MSRRSWsecondsmallTurn;
////				aimedRatio = ((requiredRightDist)/(requiredLeftDist));
////				lValue = lValue - prevLeftEncoder;
////				rValue = rValue - prevRightEncoder;
////				currentRatio = (((rValue)/(lValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(sum < requiredLeftDist + requiredRightDist)
////				{
////					Drivetrain.goStraightLeft(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .2, .707, .035);
////				}
////				else
////				{
////					currentState = 5;
////				}
////			case 5:
////				prevLeftEncoder = lValue;
////				prevRightEncoder = rValue;
////				currentState = 6;
////				break;
////			case 6:
////				lValue = lValue - prevLeftEncoder;
////				rValue = rValue - prevRightEncoder;
////				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.MSRRSWStraight - 1440))
////				{
////					Drivetrain.driveForward(lValue, rValue, .6, .06);
////				}
////				else
////				{
////					currentState =7;
////				}
//////				error = Math.abs(prevLeftEncoder - prevRightEncoder);
//////				if(prevRightEncoder > prevLeftEncoder)
//////				{
//////					rError = true;
//////					lError = false;
//////					currentState = 9;
//////				}
//////				else if(prevRightEncoder < prevLeftEncoder)
//////				{
//////					rError = false;
//////					lError = true;
//////					currentState = 9;
//////				}
//////				else
//////				{
//////					rError = false;
//////					lError = false;
//////					currentState = 9;
//////				}
////				break;
////			case 7:
////				lValue = lValue - prevLeftEncoder;
////				rValue = rValue - prevRightEncoder;
////				if(!Drivetrain.reachedDistance(lValue, rValue, Constants.MSRRSWStraight))
////				{
////					Drivetrain.driveForward(lValue, rValue, .2, .03);
////				}
////				else
////				{
////					currentState =9;
////				}
//////				if(lError)
//////				{
//////					lValue = lValue - prevLeftEncoder + error;
//////					rValue = rValue - prevRightEncoder;
//////					if(!Drivetrain.reachedDistance(lValue, rValue, Constants.MSRRSWStraight))
//////					{
//////						Drivetrain.driveForward(lValue, rValue, .6, .06);
//////					}
//////					else
//////					{
//////						currentState = 9;
//////					}
//////				}
//////				else if(rError)
//////				{
//////					lValue = lValue - prevLeftEncoder;
//////					rValue = rValue - prevRightEncoder + error;
//////					if(!Drivetrain.reachedDistance(lValue, rValue, Constants.MSRRSWStraight))
//////					{
//////						Drivetrain.driveForward(lValue, rValue, .6, .06);
//////					}
//////					else
//////					{
//////						currentState = 9;
//////					}
//////				}
//////				else
//////				{
//////					lValue = lValue - prevLeftEncoder;
//////					rValue = rValue - prevRightEncoder;
//////					if(!Drivetrain.reachedDistance(lValue, rValue, Constants.MSRRSWStraight))
//////					{
//////						Drivetrain.driveForward(lValue, rValue, .6, .06);
//////					}
//////					else
//////					{
//////						currentState = 9;
//////					}
//////				}
////				break;
////			case 9:
////				Drivetrain.stop();
////				break;
////		}
////		
////	}
////	
////	
////	
////	public static void testTurn(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 1:
////				requiredRightDist = (Constants.testSmall - 1440);
////				requiredLeftDist = (Constants.testBig - 2592);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goStraightRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .585, .325, .04);
////				}
////				else
////				{
////					currentState = 2;
////				}
////				break;
////			case 2:
////				requiredRightDist = (Constants.testSmall);
////				requiredLeftDist = (Constants.testBig);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goStraightRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .36, .2, .03);
////				}
////				else
////				{
////					requiredRightDist = 0;
////					requiredLeftDist = 0;
////					currentState = 3;
////				}
////				break;
////			case 3:
////				Drivetrain.stop();
////				Timer.delay(.3);
////				Encoders.resetEncoders();
////				Timer.delay(.3);
////				currentState = 4;
////				break;
////			case 4:
////				requiredRightDist = (Constants.testSmall - 1440);
////				requiredLeftDist = (Constants.testBig - 2592);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				lValue = Math.abs(lValue);
////				rValue = Math.abs(rValue);
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goBackRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, -.585, -.325, .04);
////				}
////				else
////				{
////					currentState = 5;
////				}
////				break;
////			case 5:
////				requiredRightDist = (Constants.testSmall);
////				requiredLeftDist = (Constants.testBig);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				lValue = Math.abs(lValue);
////				rValue = Math.abs(rValue);
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goBackRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, -.36, -.2, .04);
////				}
////				else
////				{
////					requiredRightDist = 0;
////					requiredLeftDist = 0;
////					currentState = 6;
////				}
////				break;
////			case 6:
////				Drivetrain.stop();
////				break;
////		}
////		
////	}
////	
////	public static void testRight(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 1:
////				requiredStraightDist = Constants.testStright -1440;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveForward(lValue, rValue, .6, .06);
////				}
////				else
////				{
////					currentState =2;
////				}
////				break;
////			case 2:
////				requiredStraightDist = Constants.testStright;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveForward(lValue, rValue, .1, .02);
////				}
////				else
////				{
////					requiredStraightDist = 0;
////					currentState = 3;
////				}
////				break;
////			case 3:
////				requiredRightDist = (Constants.testSmall - 1440);
////				requiredLeftDist = (Constants.testBig - 2592);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				lValue = lValue - Constants.testStright;
////				rValue = rValue - Constants.testStright;
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goStraightRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .585, .325, .04);
////				}
////				else
////				{
////					currentState = 4;
////				}
////				break;
////			case 4:
////				requiredRightDist = (Constants.testSmall);
////				requiredLeftDist = (Constants.testBig);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				lValue = lValue - Constants.testStright;
////				rValue = rValue - Constants.testStright;
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goStraightRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .36, .2, .03);
////				}
////				else
////				{
////					requiredRightDist = 0;
////					requiredLeftDist = 0;
////					currentState = 5;
////				}
////				break;
////			case 5:
////				Drivetrain.stop();
////				Encoders.resetEncoders();
////				Timer.delay(.3);
////				currentState = 6;
////				break;
////			case 6:
////				requiredRightDist = (Constants.testSmall - 1440);
////				requiredLeftDist = (Constants.testBig - 2592);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				lValue = Math.abs(lValue);
////				rValue = Math.abs(rValue);
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goBackRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, -.585, -.325, .04);
////				}
////				else
////				{
////					currentState = 7;
////				}
////				break;
////			case 7:
////				requiredRightDist = (Constants.testSmall);
////				requiredLeftDist = (Constants.testBig);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				lValue = Math.abs(lValue);
////				rValue = Math.abs(rValue);
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goBackRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, -.36, -.2, .04);
////				}
////				else
////				{
////					requiredRightDist = 0;
////					requiredLeftDist = 0;
////					currentState = 8;
////				}
////				break;
////			case 8:
////				requiredStraightDist = 1440;
////				lValue = lValue + Constants.testBig;
////				rValue = rValue + Constants.testSmall;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveBackward(lValue, rValue, -.4, .03);
////				}
////				else
////				{
////					currentState =9;
////				}
////				break;
////			case 9:
////				requiredStraightDist = Constants.testStright - 1440;
////				lValue = lValue + Constants.testBig;
////				rValue = rValue + Constants.testSmall;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveBackward(lValue, rValue, -.6, .06);
////				}
////				else
////				{
////					currentState =10;
////				}
////				break;
////			case 10:
////				requiredStraightDist = Constants.testStright;
////				lValue = lValue + Constants.testBig;
////				rValue = rValue + Constants.testSmall;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveBackward(lValue, rValue, -.2, 04);
////				}
////				else
////				{
////					currentState =11;
////				}
////				break;
////			case 11:
////				Drivetrain.stop();
////				break;
////		}
////	}
////	
////	public static void test(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 1:
////				requiredStraightDist = Constants.testStright -1440;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveForward(lValue, rValue, .6, .06);
////				}
////				else
////				{
////					currentState =2;
////				}
////				break;
////			case 2:
////				requiredStraightDist = Constants.testStright;
////				if(!Drivetrain.reachedDistance(lValue, rValue, requiredStraightDist))
////				{
////					Drivetrain.driveForward(lValue, rValue, .1, .02);
////				}
////				else
////				{
////					Encoders.resetEncoders();
////					//Timer.delay(.2);
////					requiredStraightDist = 0;
////					currentState = 3;
////				}
////				break;
////			case 3:
////				requiredLeftDist = (Constants.testSmall);
////				requiredRightDist = (Constants.testBig);
////				aimedRatio = ((requiredRightDist)/(requiredLeftDist));
////				currentRatio = (((rValue)/(lValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goStraightLeft(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .325, .585, .05);
////				}
////				else
////				{
////					currentState = 6;
////				}
////				break;
////			case 4:
////				Drivetrain.stop();
////				Encoders.resetEncoders();
////				Timer.delay(.2);
////				currentState = 5;
////				break;
////			case 5:
////				requiredLeftDist = (Constants.testSmall);
////				requiredRightDist = (Constants.testBig);
////				aimedRatio = ((requiredRightDist)/(requiredLeftDist));
////				rValue = Math.abs(rValue);
////				lValue = Math.abs(lValue);
////				currentRatio = (((rValue)/(lValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goBackLeft(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, -.2, -.36, .03);
////				}
////				else
////				{
////					currentState = 6;
////				}
////				break;
////			case 6:
////				Drivetrain.stop();
////				break;
////		}
////	}
////	
////	public static void FR(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 1:
////				requiredRightDist = (Constants.testSmall);
////				requiredLeftDist = (Constants.testBig);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goStraightRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, .585, .325, .04);
////				}
////				else
////				{
////					currentState = 2;
////				}
////				break;
////			case 2:
////				Drivetrain.stop();
////				break;
////		}
////	}
////	
////	public static void BR(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 1:
////				requiredRightDist = (Constants.testSmall);
////				requiredLeftDist = (Constants.testBig);
////				aimedRatio = ((requiredLeftDist)/(requiredRightDist));
////				lValue = Math.abs(lValue);
////				rValue = Math.abs(rValue);
////				currentRatio = (((lValue)/(rValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goBackRight(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, -.585, -.325, .04);
////				}
////				else
////				{
////					currentState = 2;
////				}
////				break;
////			case 2:
////				Drivetrain.stop();
////				break;
////		}
////	}
////	
////	public static void BL(double lValue, double rValue)
////	{
////		switch(currentState)
////		{
////			case 1:
////				requiredRightDist = (Constants.testBig);
////				requiredLeftDist = (Constants.testSmall);
////				aimedRatio = ((requiredRightDist)/(requiredLeftDist));
////				lValue = Math.abs(lValue);
////				rValue = Math.abs(rValue);
////				currentRatio = (((rValue)/(lValue))/aimedRatio);
////				sum = (rValue) + (lValue);
////				if(currentRatio >= .9 && currentRatio <= 1.1)
////				{
////					withinRange = true;
////				}
////				else
////				{
////					withinRange = false;
////				}
////				if(!Drivetrain.reachedTurnDistance(sum, requiredLeftDist, requiredRightDist))
////				{
////					Drivetrain.goBackLeft(currentRatio, withinRange, sum, requiredLeftDist, requiredRightDist, -.325, -.585, .04);
////				}
////				else
////				{
////					currentState = 2;
////				}
////				break;
////			case 2:
////				Drivetrain.stop();
////				break;
////		}
////	}
//	
//	public static void runAuto(double lValue, double rValue)
//	{
//		gameData = "RRR";//
//		//gameData = DriverStation.getInstance().getGameSpecificMessage();
//		auto += gameData.charAt(0);//
//		auto += gameData.charAt(1);//
//		switch(auto)
//		{
//			case "LL":
//				//MSRRSW2(lValue, rValue);//
//				break;
//			case "RR":
//				//MSRRSW2(lValue, rValue);
//				break;
//			case "RL":
//				//MSRRSW2(lValue, rValue);//
//				break;
//			case "LR":
//				//MSRRSW2(lValue, rValue);//
//				break;
//		}
//	}
//	
//	public static void initialize()
//	{
//		Encoders.resetEncoders();
//		Elevator.elevatorState = 0;
//		Elevator.setElevatorButtons(false, false, false, false);
//		currentState = 0;
//	}
//}