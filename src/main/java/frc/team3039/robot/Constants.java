/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3039.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 * <p>
 * It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
    public static final double kLooperDt = 0.01;
    //Shooter PID Values
    public static final double kShooterkP = 0.05;
    public static final double kShooterkI = 0.000;
    public static final double kShooterkD = 0.0;
    public static final double kShooterkF = 0.055;
    public static final int kShooterkIZone = 200;
    public static final double kShooterMaxPrecentOutput = .95;

    //Variables to determine when to switch from Calculate to Hold
    public static double kShooterMinTrackStability = 0.25;
    public static double kShooterStartOnTargetRpm = 50.0;
    public static double kShooterStopOnTargetRpm = 150.0;
    public static int kShooterKfBufferSize = 20;
    public static int kShooterMinOnTargetSamples = 20;
    //Turret PID Values
    public static final double kTurretkP = 0.035;
    public static final double kTurretkI = 0;
    public static final double kTurretkD = 0;

}