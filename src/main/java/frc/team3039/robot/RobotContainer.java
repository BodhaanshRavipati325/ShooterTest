/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.team3039.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import frc.team3039.robot.subsystems.Shooter;

/**
 * This class is where the bulk of the robot should be declared.  Since Command-based is a
 * "declarative" paradigm, very little robot logic should actually be handled in the {@link Robot}
 * periodic methods (other than the scheduler calls).  Instead, the structure of the robot
 * (including subsystems, commands, and button mappings) should be declared here.
 */
public class RobotContainer
{
    // The robot's subsystems and commands are defined here...
    private final Shooter shooterSubsystem = new Shooter();

    /**
     * The container for the robot.  Contains subsystems, OI devices, and commands.
     */
    public RobotContainer()
    {
        // Configure the button bindings
        configureButtonBindings();
    }

    /**
     * Use this method to define your button->command mappings.  Buttons can be created by
     * instantiating a {@link GenericHID} or one of its subclasses ({@link
     * edu.wpi.first.wpilibj.Joystick Joystick} or {@link XboxController}), and then passing it to a
     * {@link edu.wpi.first.wpilibj2.command.button.JoystickButton JoystickButton}.
     */
    private void configureButtonBindings()
    {
        //Instant Command is Instants of blank methof
        SmartDashboard.putData("Set Shooters Speed 1.0",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(1.0)));
        SmartDashboard.putData("Set Shooters Speed 0.95",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(0.95)));
        SmartDashboard.putData("Set Shooters Speed 0.85",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(0.85)));
        SmartDashboard.putData("Set Shooters Speed 0.9",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(0.9)));
        SmartDashboard.putData("Set Shooters Speed 0.8",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(0.8)));
        SmartDashboard.putData("Set Shooters Speed 0.7",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(0.7)));
        SmartDashboard.putData("Set Shooters Speed 0.6",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(0.6)));
        SmartDashboard.putData("Set Shooters Speed 0.5",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(0.5)));
        SmartDashboard.putData("Set Shooters Speed Off",  new InstantCommand(()-> shooterSubsystem.setShooterSpeed(0.0)));
        SmartDashboard.putData("Set Shooters RPM 1500",    new InstantCommand(()-> shooterSubsystem.setShooterRPM(1500)));
        SmartDashboard.putData("Set Shooters RPM 4000",    new InstantCommand(()-> shooterSubsystem.setShooterRPM(4000)));
        SmartDashboard.putData("Set Shooters RPM 5000", new InstantCommand(()-> shooterSubsystem.setShooterRPM(5000)));
        SmartDashboard.putData("Set Shooters RPM 6000 ", new InstantCommand(()-> shooterSubsystem.setShooterRPM(6000)));
        SmartDashboard.putData("Set Shooters RPM 0", new InstantCommand(()-> shooterSubsystem.setShooterRPM(0)));
        SmartDashboard.putData("Reset Shooters Position", new InstantCommand(()-> shooterSubsystem.resetShooterPosition()));

    }


    /**
     * Use this to pass the autonomous command to the main {@link Robot} class.
     *
     * @return the command to run in autonomous
     */
    public Command getAutonomousCommand()
    {
        // An ExampleCommand will run in autonomous
        return null;
    }
}
